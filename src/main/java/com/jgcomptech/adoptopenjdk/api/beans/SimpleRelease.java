package com.jgcomptech.adoptopenjdk.api.beans;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.jgcomptech.adoptopenjdk.enums.AssetFileType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class SimpleRelease {
    private final String url;
    private final String html_url;
    private final int id;
    private final String tag_name;
    private final String target_commitish;
    private final String name;
    private final boolean draft;
    private final SimpleAuthor author;
    private final boolean prerelease;
    private final String created_at;
    private final String published_at;
    private final List<SimpleAsset> assets = new ArrayList<>();
    private final List<SimpleAsset> shaAssets = new ArrayList<>();
    private final List<SimpleAsset> jsonAssets = new ArrayList<>();
    private final List<SimpleAsset> binaryAssets = new ArrayList<>();
    private final String description;

    public SimpleRelease(final JsonObject rootObj) {
        final JsonObject authorObj = rootObj.getAsJsonObject("author");
        final JsonArray assetsArray = rootObj.getAsJsonArray("assets");

        url = rootObj.get("url").getAsString();
        html_url = rootObj.get("html_url").getAsString();
        id = rootObj.get("id").getAsInt();
        tag_name = rootObj.get("tag_name").getAsString();
        target_commitish = rootObj.get("target_commitish").getAsString();
        name = rootObj.get("name").getAsString();
        draft = rootObj.get("draft").getAsBoolean();
        author = new SimpleAuthor().setLogin(authorObj.get("login").getAsString())
                        .setId(authorObj.get("id").getAsInt())
                        .setUrl(authorObj.get("url").getAsString());
        prerelease = rootObj.get("prerelease").getAsBoolean();
        created_at = rootObj.get("created_at").getAsString();
        published_at = rootObj.get("published_at").getAsString();
        description = rootObj.get("body").getAsString();

        for (final JsonElement obj : assetsArray) {
            final JsonObject currentObj = obj.getAsJsonObject();
            assets.add(SimpleAsset
                    .getBuilder()
                    .setParent(this)
                    .setUrl(currentObj.get("url").getAsString())
                    .setId(currentObj.get("id").getAsInt())
                    .setFileName(currentObj.get("name").getAsString())
                    .setContentType(currentObj.get("content_type").getAsString())
                    .setSize(currentObj.get("size").getAsInt())
                    .setCreatedAt(currentObj.get("created_at").getAsString())
                    .setUpdatedAt(currentObj.get("updated_at").getAsString())
                    .setBrowserDownloadURL(currentObj.get("browser_download_url").getAsString())
                    .build());
        }

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
                    binaryAssets.add(asset);
                } else if (fileType == AssetFileType.pkg_json
                        || fileType == AssetFileType.tar_gz_json
                        || fileType == AssetFileType.msi_json
                        || fileType == AssetFileType.zip_json) {
                    jsonAssets.add(asset);
                } else if (fileType == AssetFileType.pkg_sha256_txt
                        || fileType == AssetFileType.tar_gz_sha256_txt
                        || fileType == AssetFileType.msi_sha256_txt
                        || fileType == AssetFileType.zip_sha256_txt) {
                    shaAssets.add(asset);
                }
            }
        }
    }

    public String getUrl() {
        return url;
    }

    public String getHtml_url() {
        return html_url;
    }

    public int getId() {
        return id;
    }

    public String getTagName() {
        return tag_name;
    }

    public String getTargetCommitish() {
        return target_commitish;
    }

    public String getName() {
        return name;
    }

    public boolean isDraft() {
        return draft;
    }

    public SimpleAuthor getAuthor() {
        return author;
    }

    public boolean isPrerelease() {
        return prerelease;
    }

    public String getCreatedAt() {
        return created_at;
    }

    public String getPublishedAt() {
        return published_at;
    }

    public List<SimpleAsset> getAssets() {
        return Collections.unmodifiableList(assets);
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
        return description;
    }
}
