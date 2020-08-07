package com.jgcomptech.adoptopenjdk;

import ch.qos.logback.classic.Level;
import com.jgcomptech.adoptopenjdk.api.APISettings;
import com.jgcomptech.adoptopenjdk.api.beans.SimpleAsset;
import com.jgcomptech.adoptopenjdk.enums.AssetJVMType;
import com.jgcomptech.adoptopenjdk.enums.AssetName;
import com.jgcomptech.adoptopenjdk.enums.AssetReleaseType;
import com.jgcomptech.adoptopenjdk.enums.AssetType;
import com.jgcomptech.adoptopenjdk.exclusions.Exclusions;
import com.jgcomptech.adoptopenjdk.utils.info.OSInfo;
import com.jgcomptech.adoptopenjdk.utils.logging.Loggers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.Callable;

import static com.jgcomptech.adoptopenjdk.utils.Literals.FILE_SEPARATOR;
import static com.jgcomptech.adoptopenjdk.utils.StringUtils.isBlank;

public class JUpdateApp implements Callable<Integer> {
    private final Logger logger = LoggerFactory.getLogger(JUpdateApp.class);
    private final Arguments arguments;

    public JUpdateApp(final Arguments arguments) {
        this.arguments = arguments;
    }

    private Optional<JavaRelease> getRelease() {
        if(arguments.getVersion() < 8) {
            logger.error("Illegal Update Parameter! Invalid Version Number! Java 7 and earlier is unsupported!");
            return Optional.empty();
        }

        logger.info("Selected Java " + arguments.getVersion() + ", Please wait...");

        JavaRelease release = new JavaRelease(arguments.getVersion());
        JavaRelease.getReleases().put("java" + arguments.getVersion(), release);
        return Optional.of(release);
    }

    private SubRelease getSubRelease(final AssetType assetType, final JavaRelease release) {
        //Load current assets based on the specified JVM type
        switch (assetType) {
            case JDKHotspot:
                return release.getJdkHotspot();
            case JREHotspot:
                return release.getJreHotspot();
            case JDKOpenJ9:
                return release.getJdkOpenJ9();
            case JREOpenJ9:
                return release.getJreOpenJ9();
            default:
                throw new IllegalStateException("Unexpected value: null");
        }
    }

    private Optional<AssetReleaseType> processReleaseType() {
        if(arguments.isJdk() && arguments.isJre()) {
            logger.error("Illegal Update Parameter! Must select either jdk or jre not both!");
            return Optional.empty();
        }

        return arguments.isJre() ? Optional.of(AssetReleaseType.JRE) : Optional.of(AssetReleaseType.JDK);
    }

    private Optional<AssetJVMType> processJVMType() {
        if(arguments.isHotspot() && arguments.isOpenJ9()) {
            logger.error("Illegal Update Parameter! Must select either hotspot or openj9 not both!");
            return Optional.empty();
        }

        return arguments.isOpenJ9() ? Optional.of(AssetJVMType.OpenJ9) : Optional.of(AssetJVMType.Hotspot);
    }

    private Optional<AssetName> processAssetName() {
        if(!isBlank(arguments.getAsset())) {
            final Optional<AssetName> name = AssetName.parse(arguments.getAsset());

            if (!name.isPresent()) {
                logger.error("Illegal Update Parameter! Must provide valid asset name!");
            }

            return name;
        }

        return Optional.empty();
    }

    private AssetName getLocalizedAssetName() {
        if(OSInfo.isWindows()) {
            if(OSInfo.is64BitOS()) return AssetName.x64_windows_msi;
            else return AssetName.x86_32_windows_msi;
        } else if(OSInfo.isMac()) {
            if(OSInfo.is64BitOS()) return AssetName.x64_mac_pkg;
            else throw new IllegalStateException("Unsupported OS! 32-bit MacOSX is unsupported!");
        } else if(OSInfo.isLinux()) {
            if(OSInfo.is64BitOS()) return AssetName.x64_linux_tar_gz;
            else throw new IllegalStateException("Unsupported OS! 32-bit Linux is unsupported!");
        } else if(OSInfo.isSolaris()) {
            if(OSInfo.is64BitOS()) return AssetName.x64_solaris_tar_gz;
            else throw new IllegalArgumentException("Unsupported OS! 32-bit Linux is unsupported!");
        } else throw new IllegalStateException("Unsupported OS!");
    }

    /**
     * Runs the application
     * @return exit code
     * @throws IOException if any IO error occurs
     */
    @Override
    public Integer call() throws IOException {
        if(arguments.isShowBoolean()) Loggers.RootPackage.enableLimitedConsole(Level.OFF);
        if(arguments.isDebug()) Loggers.RootPackage.enableLimitedConsole(Level.DEBUG);
        if(arguments.isTrace()) Loggers.RootPackage.enableLimitedConsole(Level.TRACE);

        logger.info("Processing Update Info...");

        //Load API properties file and if the file doesn't exist then load OAuth from the command line
        APISettings.loadPropertiesFile();
        if(!APISettings.isUseOAuth() && !isBlank(arguments.getApiID()) && !isBlank(arguments.getApiSecret())) {
            APISettings.setOAuth_client_id(arguments.getApiID());
            APISettings.setOAuth_client_secret(arguments.getApiSecret());
            APISettings.setUseOAuth(true);
        }

        if(APISettings.isUseOAuth()) logger.info("GitHub OAuth Active...");
        else logger.info("GitHub OAuth Inactive...");

        //Load the Java release to use for lookup using the specified Java version
        final Optional<JavaRelease> release = getRelease();

        if (release.isPresent()) {
            final Optional<AssetReleaseType> releaseType = processReleaseType();

            if (!releaseType.isPresent()) return 1;

            final Optional<AssetJVMType> jvmType = processJVMType();

            if (!jvmType.isPresent()) return 1;

            final Optional<AssetType> assetType = AssetType.parse(releaseType.get(), jvmType.get());

            if (!assetType.isPresent()) return 1;

            final SubRelease subRelease = getSubRelease(assetType.get(), release.get());

            //If the exclusions refresh is enabled create a new file otherwise just create it if it doesn't exist
            Exclusions.createNewFile(arguments.isRefresh());
            Exclusions.loadFile();

            //This is the main processing task that contacts the API and download all release info
            subRelease.processReleases(arguments.isPrerelease(),
                    arguments.isShowBoolean()).printMissingAssets().printExtraAssets();

            //Retrieve the specified asset name to use for update check and installer download
            final AssetName assetName = processAssetName().orElseGet(this::getLocalizedAssetName);

            //Retrieve the matching asset object
            final Optional<SimpleAsset> asset = subRelease.getAssets().get(assetName);

            if (!asset.isPresent()) throw new IllegalArgumentException("Asset Not Found!");

            //If it was specified to show info about the retrieved asset do that here
            if (arguments.isShowAssetInfo()) {
                final SimpleAsset a = asset.get();

                logger.info("Release: " + a.getParentName());
                logger.info("JVM Type: " + a.getJVMType().getValue());
                logger.info("OS Name: " + a.getOS().getValue());
                logger.info("Asset Name: " + a.getAssetName());
                logger.info("Version: " + a.getVersion().getMain());
                logger.info("Date Created: " + a.getCreatedAt());
                logger.info("File Size: " + a.getSizeFormatted());
                logger.info("File Type: " + a.getContentType());
                logger.info("Release URL: " + a.getParent().getHtml_url());
                logger.info("Download Link: " + a.getBrowserDownloadURL());
            }

            //Initialize the updater instance and run an update check
            final Updater updater = new Updater(subRelease, asset.get());

            if (updater.needsUpdate()) {
                if (!updater.isInstalled()) {
                    logger.info("Update Required! Currently Not Installed!");
                } else {
                    logger.info("Update Required! Currently Installed: " + updater.getCurrentVersion().getMain());
                }
                if (arguments.isShowBoolean()) System.out.println("true");
                if (arguments.isDownload() || arguments.isInstall()) {

                    final String downloadUrl = asset.get().getBrowserDownloadURL();
                    final String path = arguments.getDownloadPath();

                    String filename;
                    File file;

                    if(path.isEmpty()) {
                        filename = new File(downloadUrl).getName().trim();
                    } else {
                        filename = path + FILE_SEPARATOR + new File(downloadUrl).getName().trim();
                    }

                    file = new File(filename);

                    if (!file.exists()) {
                        //Download the installer
                        updater.runDownload(path, downloadUrl);
                    }
                    if (arguments.isInstall()) {
                        if (file.exists()) {
                            //Run the installer
                            updater.runInstall(filename);
                        }
                    }
                }
            } else {
                logger.info("Installed Java Is Latest Version! Update Not Needed!");
                if (arguments.isShowBoolean()) System.out.println("false");
            }
        }
        return 0;
    }
}
