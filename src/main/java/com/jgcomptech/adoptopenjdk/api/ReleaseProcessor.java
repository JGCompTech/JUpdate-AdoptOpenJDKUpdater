package com.jgcomptech.adoptopenjdk.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.jgcomptech.adoptopenjdk.JavaRelease;
import com.jgcomptech.adoptopenjdk.api.beans.SimpleAsset;
import com.jgcomptech.adoptopenjdk.api.beans.SmartRelease;
import com.jgcomptech.adoptopenjdk.enums.AssetJVMType;
import com.jgcomptech.adoptopenjdk.enums.AssetName;
import com.jgcomptech.adoptopenjdk.enums.ReleaseType;
import com.jgcomptech.adoptopenjdk.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;

import static com.jgcomptech.adoptopenjdk.api.APISettings.*;

public enum ReleaseProcessor {
    ;

    public static final Logger logger = LoggerFactory.getLogger(ReleaseProcessor.class);

    public static boolean acquireNextReleasePage(final JavaRelease baseRelease, final ReleaseType type,
                                                 final AssetJVMType jvmType) throws IOException {
        final String fullUrlMask =
                "https://api.github.com/repos/%1s/%2s/releases?page=%d&per_page=%d&client_id=%3s&client_secret=%4s";
        final String shortUrlMask =
                "https://api.github.com/repos/%1s/%2s/releases?page=%d&per_page=%d";
        final String fullUrl;

        //Create the API URL depending on if OAuth information has been provided.
        //noinspection IfMayBeConditional
        if (isUseOAuth()) {
            fullUrl = String.format(fullUrlMask, getCompanyName(), baseRelease.getUrl(),
                    baseRelease.getPageCount(), getNumberOfReleasesPerPage(),
                    getOAuth_client_id(), getOAuth_client_secret());
        }
        else {
            fullUrl = String.format(shortUrlMask, getCompanyName(), baseRelease.getUrl(),
                    baseRelease.getPageCount(), getNumberOfReleasesPerPage());
        }

        logger.debug("~ Processing Release Page " + baseRelease.getPageCount() + ' ' + type
                + ' ' + baseRelease.getMajorBuild()
                + " (Release " + (baseRelease.getReleaseCount() - getNumberOfReleasesPerPage())
                + '-' + baseRelease.getReleaseCount() + ")...");

        //Load the JSON response from the API
        final JsonArray pageReleases = Utils.processJSONAsArray(fullUrl);

        //Go to the next release if 0 results were returned
        if(pageReleases.size() == 0) {
            //Increment the API page count to prep for the next release page
            baseRelease.incrementPageCount().incrementReleaseCount();
            return true;
        }

        BaseAssets currentAssets = null;
        String releaseName = "";

        //Load current assets based on the specified JVM type
        if (type == ReleaseType.JDK && jvmType == AssetJVMType.hotspot) {
            currentAssets = baseRelease.getJdkHotspotAssets();
            releaseName = "JDK Hotspot";
        } else if (type == ReleaseType.JRE && jvmType == AssetJVMType.hotspot) {
            currentAssets = baseRelease.getJreHotspotAssets();
            releaseName = "JRE Hotspot";
        } else if (type == ReleaseType.JDK && jvmType == AssetJVMType.openj9) {
            currentAssets = baseRelease.getJdkOpenJ9Assets();
            releaseName = "JDK OpenJ9";
        } else if (type == ReleaseType.JRE && jvmType == AssetJVMType.openj9) {
            currentAssets = baseRelease.getJreOpenJ9Assets();
            releaseName = "JRE OpenJ9";
        }

        //Go to next release if no results were returned for the specified JVM type
        if (currentAssets == null) {
            //Increments the API page count to prep for the next release page
            baseRelease.incrementPageCount().incrementReleaseCount();
            return false;
        }

        //Process each release one at a time
        for(final JsonElement releaseElement : pageReleases) {
            final JsonObject releaseObject = releaseElement.getAsJsonObject();

            //If release is marked as a pre-release then skip
            if(releaseObject.get("prerelease").getAsBoolean()) continue;

            final SmartRelease newRelease = new SmartRelease(releaseObject);

            processRelease(baseRelease, type, jvmType, newRelease, currentAssets, releaseName);

            if(currentAssets.isAllAcquired()) {
                //noinspection HardcodedFileSeparator
                logger.info("*****All " + baseRelease.getName() + " Assets Acquired! "
                        + currentAssets.getAll().size()
                        + '/' + currentAssets.getEnabledAssets().size() + "*****");
                return true;
            }
        }

        //Increment the API page count to prep for the next release page
        baseRelease.incrementPageCount().incrementReleaseCount();
        return false;
    }

    public static void processRelease(final JavaRelease baseRelease,
                                      final ReleaseType type,
                                      final AssetJVMType jvmType,
                                      final SmartRelease release,
                                      final BaseAssets currentAssets,
                                      final String releaseName) {
        for (final SimpleAsset binaryAsset : release.getBinaryAssets()) {
            //Check if the file already has been set as an asset
            if(currentAssets.contains(binaryAsset.getAssetName())) {
                //logger.debug("--> Skipping " + releaseName + " Asset: " + binaryAsset.getFilename() + " - Already Exists!");
                continue;
            }

            //Check if file matches java version
            if(!binaryAsset.isMajorVersion(baseRelease.getMajorBuild())) {
                //logger.debug("--> Skipping " + releaseName + " Asset: " + binaryAsset.getFilename() + " - Not Java " + baseRelease.getMajorBuild() + '!');
                continue;
            }

            //Check if file matches jvm type, hotspot or openj9
            if(!binaryAsset.isAssetJVMType(jvmType)) {
                //logger.debug("--> Skipping " + releaseName + " Asset: " + binaryAsset.getFilename() + " - Not Java " + jvmType + '!');
                continue;
            }

            //Check if the file matches release type, JDK or JRE
            if(binaryAsset.getReleaseType() != type) {
                //logger.debug("--> Skipping " + releaseName + " Asset: " + binaryAsset.getFilename() + " - Not Java " + type + '!');
                continue;
            }

            //Add current asset to release
            currentAssets.setAsset(binaryAsset);

            //Check if asset is in enabled list and respond with log entry
            if(currentAssets.getEnabledAssets().contains(binaryAsset.getAssetName())) {
                //noinspection HardcodedFileSeparator
                logger.debug("--> Adding " + releaseName + " Asset: " + binaryAsset.getFilename()
                        + ' ' + currentAssets.getAll().size()
                        + '/' + currentAssets.getEnabledAssets().size());
            } else {
                logger.debug("!!Unexpected " + releaseName + " Asset FOUND: " + binaryAsset.getAssetName() + "!!");
            }

            //Generate matching sha-256 filename
            final String binarySHAName = binaryAsset.getFilename() + ".sha256.txt";

            //Check if matching filename exists and if so save it to a variable
            final Optional<SimpleAsset> shaAsset = release.getShaAssets().stream()
                    .filter(a -> a.getFilename().equals(binarySHAName))
                    .findFirst();

            //Check if matching filename was found
            if (shaAsset.isPresent()) {
                //Add sha-256 asset to release
                currentAssets.setAsset(shaAsset.get());
                //noinspection HardcodedFileSeparator
                logger.debug("--> Adding " + releaseName + " Asset: " + binarySHAName
                        + ' ' + currentAssets.getAll().size()
                        + '/' + currentAssets.getEnabledAssets().size());
            } else {
                final AssetName shaAssetName = AssetName.parseFromName(binarySHAName);
                //Check if sha-256 asset is in the enabled list
                if(currentAssets.getEnabledAssets().contains(shaAssetName)) {
                    //Disable sha-256 asset
                    currentAssets.disableAsset(shaAssetName);
                    logger.debug("!!SHA Asset Disabled: " + shaAssetName + " - " + binarySHAName + " NOT FOUND!!");
                }
            }

            //Generate matching json filename
            final String binaryJsonName = binaryAsset.getFilename() + ".json";

            //Check if matching filename exists and if so save it to a variable
            final Optional<SimpleAsset> jsonAsset = release.getJsonAssets().stream()
                    .filter(a -> a.getFilename().equals(binaryJsonName))
                    .findFirst();

            //Check if matching filename was found
            if (jsonAsset.isPresent()) {
                //Add json asset to release
                currentAssets.setAsset(jsonAsset.get());
                //noinspection HardcodedFileSeparator
                logger.debug("--> Adding " + releaseName + " Asset: " + binaryJsonName
                        + ' ' + currentAssets.getAll().size()
                        + '/' + currentAssets.getEnabledAssets().size());
            } else {
                final AssetName jsonAssetName = AssetName.parseFromName(binaryJsonName);
                //Check if json asset is in the enabled list
                if(currentAssets.getEnabledAssets().contains(jsonAssetName)) {
                    //Disable sha-256 asset
                    currentAssets.disableAsset(jsonAssetName);
                    logger.debug("!!Json Asset Disabled: " + jsonAssetName + " - " + binaryJsonName + " NOT FOUND!!");
                }
            }
        }
    }
}
