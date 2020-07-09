package com.jgcomptech.adoptopenjdk.enums;

import java.util.Arrays;

public enum AssetJVMType {
    NONE(""),
    Hotspot("hotspot"),
    OpenJ9("openj9");

    final String value;
    AssetJVMType(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static AssetJVMType parseFromName(final String name) {
        return Arrays.stream(AssetJVMType.values())
                .filter(type -> type != NONE && name.contains(type.value))
                .findFirst()
                .orElse(NONE);
    }

    @Override
    public String toString() {
        return value;
    }
}
