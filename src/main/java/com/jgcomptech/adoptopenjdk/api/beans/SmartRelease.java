package com.jgcomptech.adoptopenjdk.api.beans;

import com.google.gson.JsonObject;
import com.jgcomptech.adoptopenjdk.enums.AssetFileType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SmartRelease {
    private final SimpleRelease release;
    private final List<SimpleAsset> shaAssets = new ArrayList<>();
    private final List<SimpleAsset> jsonAssets = new ArrayList<>();
    private final List<SimpleAsset> binaryAssets = new ArrayList<>();

    public SmartRelease(final JsonObject rootObj) {
        release = new SimpleRelease(rootObj);

        //Process each asset one at a time
        for (final SimpleAsset asset : getAssets()) {
            //Check if file matches java version
            if (//Block the weird duplicate file OpenJDK11U-jdk_ppc64_aix_hotspot_11.0.4_11_adopt.tar.gz
                    !asset.getFilename().contains("adopt.tar.gz")){

                //Get the file type of the current asset
                final AssetFileType fileType = asset.getFileType();

                //Sort the assets by file type
                if (fileType == AssetFileType.pkg
                        || fileType == AssetFileType.tar_gz
                        || fileType == AssetFileType.msi
                        || fileType == AssetFileType.zip) {
                    addBinaryAsset(asset);
                } else if (fileType == AssetFileType.pkg_json
                        || fileType == AssetFileType.tar_gz_json
                        || fileType == AssetFileType.msi_json
                        || fileType == AssetFileType.zip_json) {
                    addJsonAsset(asset);
                } else if (fileType == AssetFileType.pkg_sha256_txt
                        || fileType == AssetFileType.tar_gz_sha256_txt
                        || fileType == AssetFileType.msi_sha256_txt
                        || fileType == AssetFileType.zip_sha256_txt) {
                    addShaAsset(asset);
                }
            }
        }
    }

    public String getUrl() {
        return release.getUrl();
    }

    public String getHtml_url() {
        return release.getHtml_url();
    }

    public int getId() {
        return release.getId();
    }

    public String getTagName() {
        return release.getTagName();
    }

    public String getTargetCommitish() {
        return release.getTargetCommitish();
    }

    public String getName() {
        return release.getName();
    }

    public boolean isDraft() {
        return release.isDraft();
    }

    public SimpleAuthor getAuthor() {
        return release.getAuthor();
    }

    public boolean isPrerelease() {
        return release.isPrerelease();
    }

    public String getCreatedAt() {
        return release.getCreatedAt();
    }

    public String getPublishedAt() {
        return release.getPublishedAt();
    }

    public List<SimpleAsset> getAssets() {
        return release.getAssets();
    }

    public List<SimpleAsset> getShaAssets() {
        return Collections.unmodifiableList(shaAssets);
    }

    public List<SimpleAsset> getJsonAssets() {
        return Collections.unmodifiableList(jsonAssets);
    }

    public List<SimpleAsset> getBinaryAssets() {
        return Collections.unmodifiableList(binaryAssets);
    }

    public void addShaAsset(final SimpleAsset asset) { shaAssets.add(asset); }

    public void addJsonAsset(final SimpleAsset asset) { jsonAssets.add(asset); }

    public void addBinaryAsset(final SimpleAsset asset) { binaryAssets.add(asset); }

    public String getDescription() {
        return release.getDescription();
    }
}
