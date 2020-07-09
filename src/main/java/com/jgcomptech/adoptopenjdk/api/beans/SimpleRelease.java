package com.jgcomptech.adoptopenjdk.api.beans;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

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

    public String getDescription() {
        return description;
    }
}
