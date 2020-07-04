package com.jgcomptech.adoptopenjdk.demo;

import com.jgcomptech.adoptopenjdk.JavaRelease;
import com.jgcomptech.adoptopenjdk.api.APISettings;
import com.jgcomptech.adoptopenjdk.api.beans.SimpleAsset;

import java.io.IOException;
import java.util.Optional;

import static com.jgcomptech.adoptopenjdk.enums.AssetName.x86_32_windows_msi;

public class Demo_WO_Exclusions_File {
    public static void main(final String... args) throws IOException {
        APISettings.loadPropertiesFile();
        if(APISettings.isUseOAuth()) System.out.println("GitHub OAuth Active...");

//        JavaRelease.Java8
//                .getJdkHotspotAssets()
//                .enableAllAssets()
//                .getParentRelease()
//                .processReleases(ReleaseType.JDK, AssetJVMType.hotspot)
//                .printMissingJDKAssets()
//                .printExtraJDKAssets();

//        JavaRelease.Java9
//                .getJdkHotspotAssets()
//                .enableAllAssets()
//                //Disable Invalid Releases
//                .disableOS(AssetOS.arm_linux)
//                .disableOS(AssetOS.sparcv9_solaris)
//                .disableOS(AssetOS.x64_solaris)
//                .disableOS(AssetOS.x86_32_windows)
//                .disableAllInstallers()
//                //Disable Missing Files
//                .disableAsset(AssetName.aarch64_linux_tar_gz_json)
//                .disableAsset(AssetName.ppc64le_linux_tar_gz_json)
//                .disableAsset(AssetName.ppc64_aix_tar_gz_json)
//                .disableAsset(AssetName.s390x_linux_tar_gz_json)
//                .disableAsset(AssetName.x64_linux_tar_gz_json)
//                .disableAsset(AssetName.x64_mac_tar_gz_json)
//                .disableAsset(AssetName.x64_windows_zip_json)
//                .getParentRelease()
//                .processReleases(ReleaseType.JDK, AssetJVMType.hotspot)
//                .printMissingJDKAssets()
//                .printExtraJDKAssets();

//        JavaRelease.Java10
//                .getJdkHotspotAssets()
//                .enableAllAssets()
//                //Disable Invalid Releases
//                .disableOS(AssetOS.sparcv9_solaris)
//                .disableOS(AssetOS.x64_solaris)
//                .disableOS(AssetOS.x86_32_windows)
//                .disableAllInstallers()
//                //Disable Missing Files
//                .disableAsset(AssetName.aarch64_linux_tar_gz_json)
//                .disableAsset(AssetName.arm_linux_tar_gz_json)
//                .disableAsset(AssetName.ppc64le_linux_tar_gz_json)
//                .disableAsset(AssetName.ppc64_aix_tar_gz_json)
//                .disableAsset(AssetName.s390x_linux_tar_gz_json)
//                .disableAsset(AssetName.x64_linux_tar_gz_json)
//                .disableAsset(AssetName.x64_mac_tar_gz_json)
//                .disableAsset(AssetName.x64_windows_zip_json)
//                .getParentRelease()
//                .processReleases(ReleaseType.JDK, AssetJVMType.hotspot)
//                .printMissingJDKAssets()
//                .printExtraJDKAssets();

//        JavaRelease.Java11
//                .getJdkHotspotAssets()
//                .enableAllAssets()
//                .disableOS(AssetOS.sparcv9_solaris)
//                .disableOS(AssetOS.x64_solaris)
//                .getParentRelease()
//                .processReleases(ReleaseType.JDK, AssetJVMType.hotspot)
//                .printMissingJDKAssets()
//                .printExtraJDKAssets();

//        JavaRelease.Java12
//                .getJdkHotspotAssets()
//                .enableAllAssets()
//                //Disable Invalid Releases
//                .disableOS(AssetOS.sparcv9_solaris)
//                .disableOS(AssetOS.x64_solaris)
//                //Disable Missing Files
//                .disableAsset(AssetName.ppc64_aix_tar_gz_json)
//                .getParentRelease()
//                .processReleases(ReleaseType.JDK, AssetJVMType.hotspot)
//                .printMissingJDKAssets()
//                .printExtraJDKAssets();

//        JavaRelease.Java13
//                .getJdkHotspotAssets()
//                .enableAllAssets()
//                //Disable Invalid Releases
//                .disableOS(AssetOS.sparcv9_solaris)
//                .disableOS(AssetOS.x64_solaris)
//                //Return to java release object
//                .getParentRelease()
//                .processReleases(ReleaseType.JDK, AssetJVMType.hotspot)
//                .printMissingJDKAssets()
//                .printExtraJDKAssets();

//        JavaRelease.Java14
//                .getJdkHotspotAssets()
//                .enableAllAssets()
//                .disableOS(AssetOS.sparcv9_solaris)
//                .disableOS(AssetOS.x64_solaris)
//                .getParentRelease()
//                .processReleases(ReleaseType.JDK, AssetJVMType.hotspot)
//                .printMissingJDKAssets()
//                .printExtraJDKAssets();

//        JavaRelease.Java15
//                .getJdkHotspotAssets()
//                .enableAllAssets()
//                .disableOS(AssetOS.sparcv9_solaris)
//                .disableOS(AssetOS.x64_solaris)
//                .getParentRelease()
//                .processReleases(ReleaseType.JDK, AssetJVMType.hotspot)
//                .printMissingJDKAssets()
//                .printExtraJDKAssets();

        final Optional<SimpleAsset> asset = JavaRelease.Java11.getJdkHotspotAssets().get(x86_32_windows_msi);
        if(asset.isPresent()) {
            final SimpleAsset a = asset.get();

            System.out.println("Release: " + a.getParentName());
            System.out.println("JVM Type: " + a.getJVMType().getValue());
            System.out.println("OS Name: " + a.getOS().getValue());
            System.out.println("Asset Name: " + a.getAssetName());
            System.out.println("Date Created: " + a.getCreatedAt());
            System.out.println("File Size: " + a.getSizeFormatted());
            System.out.println("File Type: " + a.getContentType());
            System.out.println("Download Link: " + a.getBrowserDownloadURL());
        }

        System.out.println("Process Complete!");
    }
}
