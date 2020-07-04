package com.jgcomptech.adoptopenjdk.demo;

import ch.qos.logback.classic.Level;
import com.jgcomptech.adoptopenjdk.*;
import com.jgcomptech.adoptopenjdk.logging.Loggers;
import com.jgcomptech.adoptopenjdk.util.Exclusions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;

import static com.jgcomptech.adoptopenjdk.AssetName.x86_32_windows_msi;

public final class Demo_W_Exclusions_File {
    public static void main(final String... args) throws IOException {
        final Logger logger = LoggerFactory.getLogger(Demo_W_Exclusions_File.class);
        Loggers.RootPackage
                .setName(Demo_W_Exclusions_File.class.getPackage().getName())
                .enableLimitedConsole(Level.INFO);

        //No Args
        logger.info("JUpdate v0.0.1 - AdoptOpenJDK Updater");
        logger.info("Please run 'jupdate -?' or 'jupdate <command> -?' for help menu.");

        //-?
        logger.info("JUpdate v0.0.1 - AdoptOpenJDK Updater");
        logger.info("");
        logger.info("JUpdate command line utility enables updating Java OpenJDK from the command line.");
        logger.info("");
        logger.info("usage: jupdate [<command>] [<options>]");
        logger.info("");
        logger.info("The following commands are available:");
        logger.info("");

        APISettings.loadPropertiesFile();
        if(APISettings.isUseOAuth()) logger.debug("GitHub OAuth Active...");

        Exclusions.createNewFile();
        Exclusions.loadFile();

        JavaRelease.Java8
                .processReleases(ReleaseType.JDK, AssetJVMType.hotspot)
                .printMissingJDKAssets()
                .printExtraJDKAssets();

        JavaRelease.Java9
                .processReleases(ReleaseType.JDK, AssetJVMType.hotspot)
                .printMissingJDKAssets()
                .printExtraJDKAssets();

        JavaRelease.Java10
                .processReleases(ReleaseType.JDK, AssetJVMType.hotspot)
                .printMissingJDKAssets()
                .printExtraJDKAssets();

        JavaRelease.Java11
                .processReleases(ReleaseType.JDK, AssetJVMType.hotspot)
                .printMissingJDKAssets()
                .printExtraJDKAssets();

//        JavaRelease.Java12
//                .processReleases(ReleaseType.JDK, AssetJVMType.hotspot)
//                .printMissingJDKAssets()
//                .printExtraJDKAssets();

//        JavaRelease.Java13
//                .processReleases(ReleaseType.JDK, AssetJVMType.hotspot)
//                .printMissingJDKAssets()
//                .printExtraJDKAssets();

//        JavaRelease.Java14
//                .processReleases(ReleaseType.JDK, AssetJVMType.hotspot)
//                .printMissingJDKAssets()
//                .printExtraJDKAssets();

//        JavaRelease7.Java15
//                .processReleases(ReleaseType.JDK, AssetJVMType.hotspot)
//                .printMissingJDKAssets()
//                .printExtraJDKAssets();

        final Optional<SimpleAsset> asset = JavaRelease.Java11.getJdkHotspotAssets().get(x86_32_windows_msi);
        if(asset.isPresent()) {
            final SimpleAsset a = asset.get();

            logger.info("Release: " + a.getParentName());
            logger.info("JVM Type: " + a.getJVMType().getValue());
            logger.info("OS Name: " + a.getOS().getValue());
            logger.info("Asset Name: " + a.getAssetName());
            logger.info("Date Created: " + a.getCreatedAt());
            logger.info("File Size: " + a.getSizeFormatted());
            logger.info("File Type: " + a.getContentType());
            logger.info("Download Link: " + a.getBrowserDownloadURL());
        }

        logger.info("Process Complete!");
    }
}
