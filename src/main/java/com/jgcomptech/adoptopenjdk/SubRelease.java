package com.jgcomptech.adoptopenjdk;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.jgcomptech.adoptopenjdk.api.APISettings;
import com.jgcomptech.adoptopenjdk.api.BaseAssets;
import com.jgcomptech.adoptopenjdk.api.beans.SimpleAsset;
import com.jgcomptech.adoptopenjdk.api.beans.SimpleRelease;
import com.jgcomptech.adoptopenjdk.enums.AssetJVMType;
import com.jgcomptech.adoptopenjdk.enums.AssetName;
import com.jgcomptech.adoptopenjdk.enums.AssetReleaseType;
import com.jgcomptech.adoptopenjdk.enums.AssetType;
import com.jgcomptech.adoptopenjdk.utils.HTTPDownload;
import com.jgcomptech.adoptopenjdk.utils.IntegerValue;
import com.jgcomptech.adoptopenjdk.utils.logging.Loggers;
import com.jgcomptech.adoptopenjdk.utils.progressbar.ProgressBar;
import com.jgcomptech.adoptopenjdk.utils.progressbar.ProgressBarBuilder;
import com.jgcomptech.adoptopenjdk.utils.progressbar.ProgressBarStyle;
import com.jgcomptech.adoptopenjdk.utils.recentJava.CollectorsExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static com.jgcomptech.adoptopenjdk.Settings.COMPANY_NAME;
import static com.jgcomptech.adoptopenjdk.api.APISettings.*;

/**
 * The main object that contains all info about a specific Java version in either JDK or JRE and Hotspot or OpenJ9.
 */
public class SubRelease {
    private final Logger logger = LoggerFactory.getLogger(SubRelease.class);
    private final AssetType assetType;
    private final JavaRelease parent;
    private final BaseAssets assets;
    private final IntegerValue pageCount = new IntegerValue(1);
    private final IntegerValue releaseCount = new IntegerValue(APISettings.getNumberOfReleasesPerPage());

    /**
     * Instantiates a new Sub release.
     * @param assetType the asset type
     * @param parent    the parent
     */
    public SubRelease(final AssetType assetType, final JavaRelease parent) {
        this.assetType = assetType;
        this.parent = parent;
        assets = new BaseAssets(this);
    }

    /**
     * Gets the Java version name.
     * @return the Java version name
     */
    public String getName() {
        return parent.toString() + ' ' + assetType.toString();
    }

    /**
     * Gets the type including JDK or JRE and Hotspot or OpenJ9.
     * @return the type including JDK or JRE and Hotspot or OpenJ9
     */
    public AssetType getType() {
        return assetType;
    }

    /**
     * Gets the release type either JDK or JRE.
     * @return the release type either JDK or JRE
     */
    public AssetReleaseType getReleaseType() {
        return assetType.getReleaseType();
    }

    /**
     * Gets the JVM type either Hotspot or OpenJ9.
     * @return the JVM type either Hotspot or OpenJ9
     */
    public AssetJVMType getJvmType() {
        return assetType.getJvmType();
    }

    /**
     * Gets the parent Java base release.
     * @return the parent Java base release
     */
    public JavaRelease getParentBaseRelease() {
        return parent;
    }

    /**
     * Gets the object containing all the acquired release objects.
     * @return the object containing all the acquired release objects
     */
    public BaseAssets getAssets() {
        return assets;
    }

    /**
     * Gets the current API page count.
     * @return the current API page count
     */
    private int getPageCount() {
        return pageCount.get();
    }

    /**
     * Gets the current API release count.
     * @return the current API release count
     */
    private int getReleaseCount() {
        return releaseCount.get();
    }

    /**
     * Increments the current API page and release count.
     */
    private void incrementPageAndReleaseCount() {
        pageCount.increment();
        releaseCount.add(APISettings.getNumberOfReleasesPerPage());
    }

    /**
     * Contacts the API and download all release info and process the results.
     * @param prerelease  whether or not pre-release assets should be used
     * @param showBoolean whether or not logging should be enabled
     * @return this instance for method chaining
     * @throws IOException if any errors occur
     */
    @SuppressWarnings("UnusedReturnValue")
    public SubRelease processReleases(final boolean prerelease, final boolean showBoolean) throws IOException {
        logger.info("~ Processing Java " + parent.getMajorBuild() + ' ' + assetType.toString() + "...");

        boolean allAssetsAcquired = false;

        if(!Loggers.RootPackage.getLogger().isDebugEnabled() && !showBoolean) {
            try (final ProgressBar pb = new ProgressBarBuilder()
                    .setTaskName("")
                    .setInitialMax(getAssets().getEnabledAssets().size())
                    .setStyle(ProgressBarStyle.ASCII_LINUX)
                    .useDefaultProgressBarRenderer()
                    .build()) {
                while(!allAssetsAcquired) {
                    allAssetsAcquired =
                            acquireNextReleasePage(this, prerelease, pb);
                }
            }
        } else {
            while(!allAssetsAcquired) {
                allAssetsAcquired =
                        acquireNextReleasePage(this, prerelease, null);
            }
        }

        return this;
    }

    private boolean acquireNextReleasePage(final SubRelease subRelease,
                                                 final boolean usePrerelease,
                                                 final ProgressBar pb) throws IOException {
        final String fullUrlMask =
                "https://api.github.com/repos/%1s/%2s/releases?page=%d&per_page=%d&client_id=%3s&client_secret=%4s";
        final String shortUrlMask =
                "https://api.github.com/repos/%1s/%2s/releases?page=%d&per_page=%d";
        final String fullUrl;

        final JavaRelease baseRelease = subRelease.getParentBaseRelease();

        String releaseUrl = baseRelease.getUrl();

        if(subRelease.getType() == AssetType.JDKOpenJ9 || subRelease.getType() == AssetType.JREOpenJ9) {
            if(baseRelease.getMajorBuild() == 9) {
                releaseUrl = "openjdk9-openj9-releases";
            } else if(baseRelease.getMajorBuild() == 10) {
                releaseUrl = "openjdk10-openj9-releases";
            }
        }

        //Create the API URL depending on if OAuth information has been provided.
        //noinspection IfMayBeConditional
        if (isUseOAuth()) {
            fullUrl = String.format(fullUrlMask, COMPANY_NAME, releaseUrl,
                    getPageCount(), getNumberOfReleasesPerPage(),
                    getOAuth_client_id(), getOAuth_client_secret());
        }
        else {
            fullUrl = String.format(shortUrlMask, COMPANY_NAME, releaseUrl,
                    getPageCount(), getNumberOfReleasesPerPage());
        }

        logger.debug("~ Processing Release Page " + getPageCount() + ' ' + subRelease.getName()
                + " (Release " + (getReleaseCount() - getNumberOfReleasesPerPage())
                + '-' + getReleaseCount() + ")...");

        final HTTPDownload download = new HTTPDownload(fullUrl);

        //Load the JSON response from the API
        final Optional<JsonArray> pageReleases = download.processJSONAsArray();

        //Go to the next release if 0 results were returned
        if(!pageReleases.isPresent() || pageReleases.get().size() == 0) {
            //Increment the API page count to prep for the next release page
            incrementPageAndReleaseCount();
            return true;
        }

        final List<SimpleRelease> newReleases = new LinkedList<>();

        //Process each release one at a time
        for(final JsonElement releaseElement : pageReleases.get()) {
            final JsonObject releaseObject = releaseElement.getAsJsonObject();

            //If release is marked as a pre-release then skip
            if(releaseObject.get("prerelease").getAsBoolean() && !usePrerelease) continue;

            newReleases.add(new SimpleRelease(releaseObject));
        }

        if(subRelease.isAllAssetsAcquired()) {
            if(pb != null) pb.stepTo(getAssets().getEnabledAssets().size());
            //noinspection HardcodedFileSeparator
            logger.debug("***** All " + subRelease.getName() + " Assets Acquired! "
                    + subRelease.getAssets().getAll().size()
                    + '/' + subRelease.getAssets().getEnabledAssets().size() + " *****");
            return true;
        }

        for(final SimpleRelease newRelease : newReleases) {
            processRelease(subRelease, newRelease, pb);

            if(subRelease.isAllAssetsAcquired()) {
                if(pb != null) pb.stepTo(getAssets().getEnabledAssets().size());
                //noinspection HardcodedFileSeparator
                logger.debug("***** All " + subRelease.getName() + " Assets Acquired! "
                        + subRelease.getAssets().getAll().size()
                        + '/' + subRelease.getAssets().getEnabledAssets().size() + " *****");
                return true;
            }
        }

        //Increment the API page count to prep for the next release page
        incrementPageAndReleaseCount();
        return false;
    }

    private void processRelease(final SubRelease subRelease,
                                       final SimpleRelease release,
                                       final ProgressBar pb) {
        BaseAssets currentAssets = subRelease.getAssets();
        String releaseName = subRelease.getName();

        for (final SimpleAsset binaryAsset : release.getBinaryAssets()) {
            //Check if the file already has been set as an asset
            if(currentAssets.contains(binaryAsset.getAssetName())) {
                logger.trace("--> Skipping " + releaseName + " Asset: " + binaryAsset.getFilename() + " - Already Exists!");
                continue;
            }

            //Check if file matches java version
            if(!binaryAsset.isMajorVersion(subRelease.getParentBaseRelease().getMajorBuild())) {
                logger.trace("--> Skipping " + releaseName + " Asset: " + binaryAsset.getFilename() +
                        " - Not Java " + subRelease.getParentBaseRelease().getMajorBuild() + '!');
                continue;
            }

            //Check if file matches jvm type, hotspot or openj9
            if(!binaryAsset.isAssetJVMType(subRelease.getJvmType())) {
                logger.trace("--> Skipping " + releaseName + " Asset: " + binaryAsset.getFilename() +
                        " - Not Java " + subRelease.getJvmType() + '!');
                continue;
            }

            //Check if the file matches release type, JDK or JRE
            if(binaryAsset.getReleaseType() != subRelease.getReleaseType()) {
                logger.trace("--> Skipping " + releaseName + " Asset: " + binaryAsset.getFilename() +
                        " - Not Java " + subRelease.getReleaseType() + '!');
                continue;
            }

            //Add current asset to release
            currentAssets.addAsset(binaryAsset);

            //Check if asset is in enabled list and respond with log entry
            if(currentAssets.getEnabledAssets().contains(binaryAsset.getAssetName())) {
                //noinspection HardcodedFileSeparator
                logger.debug("--> Adding " + releaseName + " Asset: " + binaryAsset.getFilename()
                        + ' ' + currentAssets.getAll().size()
                        + '/' + currentAssets.getEnabledAssets().size());
                if(pb != null) pb.stepTo(currentAssets.getAll().size());
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
                currentAssets.addAsset(shaAsset.get());
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
                currentAssets.addAsset(jsonAsset.get());
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

    /**
     * Returns true if all assets have been acquired from the API.
     * @return true if all assets have been acquired from the API
     */
    public boolean isAllAssetsAcquired() {
        return assets.isAllAcquired();
    }

    /**
     * Returns a list of all assets returned by the API that were unexpected.
     * @return a list of all assets returned by the API that were unexpected
     */
    public List<AssetName> getExtraAssets() {
        return assets.getAll().keySet().stream()
                .filter(asset -> !assets.getEnabledAssets().contains(asset))
                .collect(CollectorsExt.toUnmodifiableList());
    }

    /**
     * Returns a list of all assets not returned by the API that were expected.
     * @return a list of all assets not returned by the API that were expected
     */
    public List<AssetName> getMissingAssets() {
        return assets.getEnabledAssets().stream()
                .filter(asset -> !assets.contains(asset))
                .collect(CollectorsExt.toUnmodifiableList());
    }

    /**
     * Prints all assets returned by the API that were unexpected.
     * @return this instance for method chaining
     */
    @SuppressWarnings("UnusedReturnValue")
    public SubRelease printMissingAssets() {
        getMissingAssets()
                .forEach(a -> logger.debug("!!" + getName() + " Asset NOT FOUND: " + a.name() + "!!"));
        return this;
    }

    /**
     * Prints all assets not returned by the API that were expected.
     * @return this instance for method chaining
     */
    @SuppressWarnings("UnusedReturnValue")
    public SubRelease printExtraAssets() {
        getExtraAssets()
                .forEach(a -> logger.debug("!!Unexpected " + getName() + " Asset FOUND: " + a.name() + "!!"));
        return this;
    }
}
