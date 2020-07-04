package com.jgcomptech.adoptopenjdk;

import java.util.Arrays;
import java.util.Optional;

import static com.jgcomptech.adoptopenjdk.AssetFileType.*;

public enum AssetName {
    aarch64_linux_tar_gz,
    aarch64_linux_tar_gz_json,
    aarch64_linux_tar_gz_sha256_txt,

    arm_linux_tar_gz,
    arm_linux_tar_gz_json,
    arm_linux_tar_gz_sha256_txt,

    ppc64_aix_tar_gz,
    ppc64_aix_tar_gz_json,
    ppc64_aix_tar_gz_sha256_txt,

    ppc64le_linux_tar_gz,
    ppc64le_linux_tar_gz_json,
    ppc64le_linux_tar_gz_sha256_txt,

    s390x_linux_tar_gz,
    s390x_linux_tar_gz_json,
    s390x_linux_tar_gz_sha256_txt,

    sparcv9_solaris_tar_gz,
    sparcv9_solaris_tar_gz_json,
    sparcv9_solaris_tar_gz_sha256_txt,

    x64_linux_tar_gz,
    x64_linux_tar_gz_json,
    x64_linux_tar_gz_sha256_txt,

//    x64_mac_macosXL_pkg,
//    x64_mac_macosXL_pkg_json,
//    x64_mac_macosXL_pkg_sha256_txt,
//
//    x64_mac_macosXL_tar_gz,
//    x64_mac_macosXL_tar_gz_json,
//    x64_mac_macosXL_tar_gz_sha256_txt,

    x64_mac_pkg,
    x64_mac_pkg_json,
    x64_mac_pkg_sha256_txt,

    x64_mac_tar_gz,
    x64_mac_tar_gz_json,
    x64_mac_tar_gz_sha256_txt,

    x64_solaris_tar_gz,
    x64_solaris_tar_gz_json,
    x64_solaris_tar_gz_sha256_txt,

    x64_windows_msi,
    x64_windows_msi_json,
    x64_windows_msi_sha256_txt,

//    x64_windows_windowsXL_msi,
//    x64_windows_windowsXL_msi_json,
//    x64_windows_windowsXL_msi_sha256_txt,
//
//    x64_windows_windowsXL_zip,
//    x64_windows_windowsXL_zip_json,
//    x64_windows_windowsXL_zip_sha256_txt,

    x64_windows_zip,
    x64_windows_zip_json,
    x64_windows_zip_sha256_txt,

    x86_32_windows_msi,
    x86_32_windows_msi_json,
    x86_32_windows_msi_sha256_txt,

    x86_32_windows_zip,
    x86_32_windows_zip_json,
    x86_32_windows_zip_sha256_txt,
    ;

    public static Optional<AssetName> parse(final String name) {
        return Arrays.stream(values())
                .filter(asset -> asset.name().equalsIgnoreCase(name))
                .findAny();
    }

    public static AssetName parseFromName(final String name) {
        final AssetFileType fileType = AssetFileType.parseFromName(name);

        switch(AssetOS.parseFromName(name)) {
            case NONE:
                break;
            case aarch64_linux:
                if (fileType == tar_gz_json) return aarch64_linux_tar_gz_json;
                if (fileType == tar_gz_sha256_txt) return aarch64_linux_tar_gz_sha256_txt;
                if (fileType == tar_gz) return aarch64_linux_tar_gz;
                break;
            case arm_linux:
                if(fileType == tar_gz_json) return arm_linux_tar_gz_json;
                if(fileType == tar_gz_sha256_txt) return arm_linux_tar_gz_sha256_txt;
                if(fileType == tar_gz) return arm_linux_tar_gz;
                break;
            case ppc64le_linux:
                if(fileType == tar_gz_json) return ppc64le_linux_tar_gz_json;
                if(fileType == tar_gz_sha256_txt) return ppc64le_linux_tar_gz_sha256_txt;
                if(fileType == tar_gz) return ppc64le_linux_tar_gz;
                break;
            case ppc64_aix:
                if(fileType == tar_gz_json) return ppc64_aix_tar_gz_json;
                if(fileType == tar_gz_sha256_txt) return ppc64_aix_tar_gz_sha256_txt;
                if(fileType == tar_gz) return ppc64_aix_tar_gz;
                break;
            case s390x_linux:
                if(fileType == tar_gz_json) return s390x_linux_tar_gz_json;
                if(fileType == tar_gz_sha256_txt) return s390x_linux_tar_gz_sha256_txt;
                if(fileType == tar_gz) return s390x_linux_tar_gz;
                break;
            case sparcv9_solaris:
                if (fileType == tar_gz_json) return sparcv9_solaris_tar_gz_json;
                if (fileType == tar_gz_sha256_txt) return sparcv9_solaris_tar_gz_sha256_txt;
                if (fileType == tar_gz) return sparcv9_solaris_tar_gz;
                break;
            case x64_linux:
                if(fileType == tar_gz_json) return x64_linux_tar_gz_json;
                if(fileType == tar_gz_sha256_txt) return x64_linux_tar_gz_sha256_txt;
                if(fileType == tar_gz) return x64_linux_tar_gz;
                break;
//            case x64_mac_macosXL:
//                if(fileType == pkg_json) return x64_mac_macosXL_pkg_json;
//                if(fileType == pkg_sha256_txt) return x64_mac_macosXL_pkg_sha256_txt;
//                if(fileType == pkg) return x64_mac_macosXL_pkg;
//                if(fileType == tar_gz_json) return x64_mac_macosXL_tar_gz_json;
//                if(fileType == tar_gz_sha256_txt) return x64_mac_macosXL_tar_gz_sha256_txt;
//                if(fileType == tar_gz) return x64_mac_macosXL_tar_gz;
//                break;
            case x64_mac:
                if(fileType == pkg_json) return x64_mac_pkg_json;
                if(fileType == pkg_sha256_txt) return x64_mac_pkg_sha256_txt;
                if(fileType == pkg) return x64_mac_pkg;
                if(fileType == tar_gz_json) return x64_mac_tar_gz_json;
                if(fileType == tar_gz_sha256_txt) return x64_mac_tar_gz_sha256_txt;
                if(fileType == tar_gz) return x64_mac_tar_gz;
                break;
            case x64_solaris:
                if (fileType == tar_gz_json) return x64_solaris_tar_gz_json;
                if (fileType == tar_gz_sha256_txt) return x64_solaris_tar_gz_sha256_txt;
                if (fileType == tar_gz) return x64_solaris_tar_gz;
                break;
//            case x64_windows_windowsXL:
//                if(fileType == msi_json) return x64_windows_windowsXL_msi_json;
//                if(fileType == msi_sha256_txt) return x64_windows_windowsXL_msi_sha256_txt;
//                if(fileType == msi) return x64_windows_windowsXL_msi;
//                if(fileType == zip_json) return x64_windows_windowsXL_zip_json;
//                if(fileType == zip_sha256_txt) return x64_windows_windowsXL_zip_sha256_txt;
//                if(fileType == zip) return x64_windows_windowsXL_zip;
//                break;
            case x64_windows:
                if(fileType == msi_json) return x64_windows_msi_json;
                if(fileType == msi_sha256_txt) return x64_windows_msi_sha256_txt;
                if(fileType == msi) return x64_windows_msi;
                if(fileType == zip_json) return x64_windows_zip_json;
                if(fileType == zip_sha256_txt) return x64_windows_zip_sha256_txt;
                if(fileType == zip) return x64_windows_zip;
                break;
            case x86_32_windows:
                if(fileType == msi_json) return x86_32_windows_msi_json;
                if(fileType == msi_sha256_txt) return x86_32_windows_msi_sha256_txt;
                if(fileType == msi) return x86_32_windows_msi;
                if(fileType == zip_json) return x86_32_windows_zip_json;
                if(fileType == zip_sha256_txt) return x86_32_windows_zip_sha256_txt;
                if(fileType == zip) return x86_32_windows_zip;
                break;
        }

        //Should Never Return Null!!!
        return null;
    }
}
