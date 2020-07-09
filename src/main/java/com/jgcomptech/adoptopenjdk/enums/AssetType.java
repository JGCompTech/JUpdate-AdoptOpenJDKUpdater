package com.jgcomptech.adoptopenjdk.enums;

import java.util.Optional;

public enum AssetType {
    JDKHotspot(AssetReleaseType.JDK, AssetJVMType.Hotspot),
    JREHotspot(AssetReleaseType.JRE, AssetJVMType.Hotspot),
    JDKOpenJ9(AssetReleaseType.JDK, AssetJVMType.OpenJ9),
    JREOpenJ9(AssetReleaseType.JRE, AssetJVMType.OpenJ9);

    final AssetReleaseType releaseType;
    final AssetJVMType jvmType;

    AssetType(AssetReleaseType releaseType, AssetJVMType jvmType) {
        this.releaseType = releaseType;
        this.jvmType = jvmType;
    }

    public AssetReleaseType getReleaseType() {
        return releaseType;
    }

    public AssetJVMType getJvmType() {
        return jvmType;
    }

    public static Optional<AssetType> parse(AssetReleaseType releaseType, AssetJVMType jvmType) {
        if (releaseType == AssetReleaseType.JDK && jvmType == AssetJVMType.Hotspot) {
            return Optional.of(JDKHotspot);
        } else if (releaseType == AssetReleaseType.JRE && jvmType == AssetJVMType.Hotspot) {
            return Optional.of(JREHotspot);
        } else if (releaseType == AssetReleaseType.JDK && jvmType == AssetJVMType.OpenJ9) {
            return Optional.of(JDKOpenJ9);
        } else if (releaseType == AssetReleaseType.JRE && jvmType == AssetJVMType.OpenJ9) {
            return Optional.of(JREOpenJ9);
        } else return Optional.empty();
    }

    @Override
    public String toString() {
        return releaseType.name() + ' ' + jvmType.name();
    }
}
