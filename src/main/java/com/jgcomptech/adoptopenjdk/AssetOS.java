package com.jgcomptech.adoptopenjdk;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Arrays;

public enum AssetOS {
    NONE(""),
    aarch64_linux("aarch64_linux"),
    arm_linux("arm_linux"),
    ppc64_aix("ppc64_aix"),
    ppc64le_linux("ppc64le_linux"),
    s390x_linux("s390x_linux"),
    sparcv9_solaris("sparcv9_solaris"),
    x64_linux("x64_linux"),
    //x64_mac_macosXL("x64_mac_macosXL"),
    x64_mac("x64_mac"),
    x64_solaris("x64_solaris"),
    x64_windows("x64_windows"),
    //x64_windows_windowsXL("x64_windows_windowsXL"),
    x86_32_windows("x86-32_windows"),
    ;

    final String value;
    AssetOS(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static AssetOS parseFromName(final String name) {
        return Arrays.stream(AssetOS.values())
                .filter(os -> os != NONE && name.contains(os.value))
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
