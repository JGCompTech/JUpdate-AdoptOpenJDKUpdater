package com.jgcomptech.adoptopenjdk.enums;


public enum AssetOS {
    NONE(""),
    aarch64_linux("aarch64_linux"),
    arm_linux("arm_linux"),
    ppc64_aix("ppc64_aix"),
    ppc64le_linux("ppc64le_linux"),
    ppc64le_linux_linuxXL("ppc64le_linux_linuxxl"),
    s390x_linux("s390x_linux"),
    s390x_linux_linuxXL("s390x_linux_linuxxl"),
    sparcv9_solaris("sparcv9_solaris"),
    x64_linux("x64_linux"),
    x64_linux_linuxXL("x64_linux_linuxxl"),
    x64_mac_macosXL("x64_mac_macosxl"),
    x64_mac("x64_mac"),
    x64_solaris("x64_solaris"),
    x64_windows("x64_windows"),
    x64_windows_windowsXL("x64_windows_windowsxl"),
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
        //Order of if statements is important or program will break
        if(name.contains("aarch64_linux")) return aarch64_linux;
        if(name.contains("arm_linux")) return arm_linux;
        if(name.contains("ppc64_aix")) return ppc64_aix;
        if(name.contains("ppc64le_linux_linuxxl")) return ppc64le_linux_linuxXL;
        if(name.contains("ppc64le_linux")) return ppc64le_linux;
        if(name.contains("s390x_linux_linuxxl")) return s390x_linux_linuxXL;
        if(name.contains("s390x_linux")) return s390x_linux;
        if(name.contains("sparcv9_solaris")) return sparcv9_solaris;
        if(name.contains("x64_linux_linuxxl")) return x64_linux_linuxXL;
        if(name.contains("x64_linux")) return x64_linux;
        if(name.contains("x64_mac_macosxl")) return x64_mac_macosXL;
        if(name.contains("x64_mac")) return x64_mac;
        if(name.contains("x64_solaris")) return x64_solaris;
        if(name.contains("x64_windows_windowsxl")) return x64_windows_windowsXL;
        if(name.contains("x64_windows")) return x64_windows;
        if(name.contains("x86-32_windows")) return x86_32_windows;
        return NONE;
    }

    @Override
    public String toString() {
        return value;
    }
}
