package com.jgcomptech.adoptopenjdk.api.beans;

import com.jgcomptech.adoptopenjdk.api.classes.Filename;
import com.jgcomptech.adoptopenjdk.enums.*;
import com.jgcomptech.adoptopenjdk.utils.Utils;

import java.util.Locale;

@SuppressWarnings("FieldNotUsedInToString")
public final class SimpleAsset {
    private final String url;
    private final int id;
    private final Filename filename;
    private final AssetName assetName;
    private final AssetJVMType jvmType;
    private final ReleaseType releaseType;
    private final String content_type;
    private final int size;
    private final String created_at;
    private final String updated_at;
    private final String browser_download_url;

    private SimpleAsset(final String url, final int id, final String filename, final String content_type,
                        final int size, final String created_at, final String updated_at,
                        final String browser_download_url) {
        this.url = url;
        this.id = id;
        this.filename = new Filename(filename);
        this.content_type = content_type;
        this.size = size;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.browser_download_url = browser_download_url;
        jvmType = AssetJVMType.parseFromName(this.filename.getBaseName().toLowerCase(Locale.getDefault()));
        assetName = AssetName.parseFromName(this.filename.getFullName());
        releaseType = ReleaseType.parseFromName(this.filename.getBaseName());
    }

    public String getUrl() {
        return url;
    }

    public int getId() {
        return id;
    }

    public String getFilename() {
        return filename.getFullName();
    }

    public String getBaseName() {
        return filename.getBaseName();
    }

    public String getFileExtension() {
        return filename.getExtension();
    }

    public String getContentType() {
        return content_type;
    }

    public int getSize() {
        return size;
    }

    public String getSizeFormatted() { return Utils.convertBytesToString(size); }

    public String getCreatedAt() {
        return created_at;
    }

    public String getUpdatedAt() {
        return updated_at;
    }

    public String getBrowserDownloadURL() {
        return browser_download_url;
    }

    public String getParentName() {
        return "Java " + getMajorVersion() + ' ' + releaseType + ' ' + jvmType.getValue();
    }

    public int getMajorVersion() {
        final String temp = filename.getBaseName().replace("OpenJDK", "");
        return Integer.parseInt(temp.substring(0, temp.indexOf("U-")));
    }

    @SuppressWarnings("StringConcatenationMissingWhitespace")
    public boolean isMajorVersion(final int version) {
        return filename.getBaseName().contains("OpenJDK" + version + 'U');
    }

    public boolean isJDK() {
        return releaseType == ReleaseType.JDK;
    }

    public boolean isJRE() {
        return releaseType == ReleaseType.JRE;
    }

    public boolean isTestImage() {
        return releaseType == ReleaseType.TestImage;
    }

    public ReleaseType getReleaseType() {
        return releaseType;
    }

    public boolean isAssetOS(final AssetOS os) {
        return filename.getBaseName().contains(os.getValue());
    }

    public AssetOS getOS() {
        return AssetOS.parseFromName(filename.getBaseName());
    }

    public AssetJVMType getJVMType() {
        return jvmType;
    }

    public AssetFileType getFileType() {
        return filename.getFileType();
    }

    public AssetName getAssetName() {
        return assetName;
    }

    @Override
    public String toString() { return filename.getFullName(); }

    public static Builder getBuilder() { return new Builder(); }

    public boolean isAssetFileType(final AssetFileType fileType) {
        return getFileType() == fileType;
    }

    public boolean isAssetJVMType(final AssetJVMType jvmType) {
        return this.jvmType == jvmType;
    }

    @SuppressWarnings("ClassHasNoToStringMethod")
    public static final class Builder {
        private String url;
        private int id;
        private String fileName;
        private String content_type;
        private int size;
        private String created_at;
        private String updated_at;
        private String browser_download_url;

        private Builder() {}

        public Builder setUrl(final String url) {
            this.url = url;
            return this;
        }

        public Builder setId(final int id) {
            this.id = id;
            return this;
        }

        public Builder setFileName(final String fileName) {
            this.fileName = fileName;
            return this;
        }

        public Builder setContentType(final String content_type) {
            this.content_type = content_type;
            return this;
        }

        public Builder setSize(final int size) {
            this.size = size;
            return this;
        }

        public Builder setCreatedAt(final String created_at) {
            this.created_at = created_at;
            return this;
        }

        public Builder setUpdatedAt(final String updated_at) {
            this.updated_at = updated_at;
            return this;
        }

        public Builder setBrowserDownloadURL(final String browser_download_url) {
            this.browser_download_url = browser_download_url;
            return this;
        }

        public SimpleAsset build() {
            return new SimpleAsset(url, id, fileName, content_type, size,
                    created_at, updated_at, browser_download_url);
        }
    }
}
