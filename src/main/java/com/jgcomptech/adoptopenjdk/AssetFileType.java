package com.jgcomptech.adoptopenjdk;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Arrays;

public enum AssetFileType {
    pkg_sha256_txt("pkg.sha256.txt"),
    pkg_json("pkg.json"),
    pkg("pkg"),
    tar_gz_sha256_txt("tar.gz.sha256.txt"),
    tar_gz_json("tar.gz.json"),
    tar_gz("tar.gz"),
    msi_sha256_txt("msi.sha256.txt"),
    msi_json("msi.json"),
    msi("msi"),
    zip_sha256_txt("zip.sha256.txt"),
    zip_json("zip.json"),
    zip("zip"),
    NONE(""),
    ;

    final String value;
    AssetFileType(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static AssetFileType parseFromName(final String name) {
        return Arrays.stream(AssetFileType.values())
                .filter(type -> type != NONE && name.contains(type.value))
                .findFirst()
                .orElse(NONE);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("value", value)
                .toString();
    }
}
