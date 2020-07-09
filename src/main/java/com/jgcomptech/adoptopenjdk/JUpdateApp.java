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
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import static com.jgcomptech.adoptopenjdk.Argument.*;
import static com.jgcomptech.adoptopenjdk.utils.StringUtils.isBlank;
import static com.jgcomptech.adoptopenjdk.utils.StringUtils.isNumeric;
import static java.nio.charset.StandardCharsets.UTF_8;

public class JUpdateApp {
    private final Logger logger = LoggerFactory.getLogger(JUpdateApp.class);
    private static final int CURRENT_LTS = 11;
    private Arguments arguments;

    public JUpdateApp() {
        Loggers.RootPackage
                .setName(JUpdateApp.class.getPackage().getName())
                .enableLimitedConsole(Level.INFO);
    }

    /**
     * Runs the application
     *
     * @param args an array of String arguments to be parsed
     */
    public void run(final String... args) throws IOException, XmlPullParserException {
        arguments = new Arguments(args);

        //TODO: Figure out how to include the pom.xml file in the JAR file
        //final Model model = getMavenModel();

        //logger.info("JUpdate v" + model.getVersion() + " - AdoptOpenJDK Updater");
        logger.info("JUpdate v0.1.0 - AdoptOpenJDK Updater");
        logger.info("");

        if(arguments.exists(HELP)) arguments.printAppHelp();
        else {
            logger.info("Processing Update Info...");

            APISettings.loadPropertiesFile();
            if(APISettings.isUseOAuth()) logger.debug("GitHub OAuth Active...");
            else logger.debug("GitHub OAuth Inactive...");

            final Optional<JavaRelease> release = getRelease();

            if (release.isPresent()) {
                final Optional<AssetReleaseType> releaseType = processReleaseType();

                if (!releaseType.isPresent()) return;

                final Optional<AssetJVMType> jvmType = processJVMType();

                if (!jvmType.isPresent()) return;

                final Optional<AssetType> assetType = AssetType.parse(releaseType.get(), jvmType.get());

                if (!assetType.isPresent()) return;

                final SubRelease subRelease = getSubRelease(assetType.get(), release.get());

                Exclusions.createNewFile(arguments.exists(REFRESH));
                Exclusions.loadFile();

                subRelease.processReleases(arguments).printMissingAssets().printExtraAssets();

                final AssetName assetName = processAssetName().orElseGet(this::getLocalizedAssetName);

                final Optional<SimpleAsset> asset = subRelease.getAssets().get(assetName);

                if (!asset.isPresent()) throw new IllegalArgumentException("Asset Not Found!");

                if(arguments.exists(SHOW_ASSET_INFO)) {
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

                final String downloadUrl = asset.get().getBrowserDownloadURL();

                final Updater updater = new Updater(subRelease, asset.get());

                if (updater.needsUpdate()) {
                    if (!updater.isInstalled()) {
                        logger.info("Update Required! Currently Not Installed!");
                        if(arguments.exists(BOOLEAN)) System.out.println("true");
                    } else {
                        logger.info("Update Required! Currently Installed: " + updater.getCurrentVersion().getMain());
                        if(arguments.exists(BOOLEAN)) System.out.println("true");
                    }
                    if (arguments.exists(DOWNLOAD) || arguments.exists(INSTALL)) {

                        String path = "";

                        if (arguments.exists(DOWNLOAD)) path = arguments.getValue(DOWNLOAD);
                        if (arguments.exists(INSTALL)) path = arguments.getValue(INSTALL);

                        Optional<String> filename = updater.runDownload(path, downloadUrl);
                        if (arguments.exists(INSTALL)) {
                            if (filename.isPresent()) {
                                updater.runInstall(filename.get());
                            }
                        }
                    }
                } else {
                    logger.info("Installed Java Is Latest Version! Update Not Needed!");
                    if(arguments.exists(BOOLEAN)) System.out.println("false");
                }
            }
        }
    }

    private Optional<JavaRelease> getRelease() {
        final String arg = arguments.getValue(VERSION);
        if(isNumeric(arg)) {
            final int version = Integer.parseInt(arg);

            if(version < 8) {
                logger.error("Illegal Update Parameter! Invalid Version Number! Java 7 and earlier is unsupported!");
                return Optional.empty();
            }

            logger.info("Selected Java " + version + ", Please wait...");

            JavaRelease release = new JavaRelease(version);
            JavaRelease.releases.put("java" + version, release);
            return Optional.of(release);
        } else if(isBlank(arg)) {
            logger.info("No Version Selected, Using Current LTS, Java " + CURRENT_LTS + ", Please wait...");

            JavaRelease release = new JavaRelease(CURRENT_LTS);
            JavaRelease.releases.put("java" + CURRENT_LTS, release);
            return Optional.of(release);
        } else {
            logger.error("Illegal Update Parameter! Invalid Version Number! Parameter must be numeric only!");
            return Optional.empty();
        }
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
        if(arguments.exists(JDK) && arguments.exists(JRE)) {
            logger.error("Illegal Update Parameter! Must select either jdk or jre not both!");
            return Optional.empty();
        }

        return arguments.exists(JRE) ? Optional.of(AssetReleaseType.JRE) : Optional.of(AssetReleaseType.JDK);
    }

    private Optional<AssetJVMType> processJVMType() {
        if(arguments.exists(HOTSPOT) && arguments.exists(OPENJ9)) {
            logger.error("Illegal Update Parameter! Must select either hotspot or openj9 not both!");
            return Optional.empty();
        }

        return arguments.exists(OPENJ9) ? Optional.of(AssetJVMType.OpenJ9) : Optional.of(AssetJVMType.Hotspot);
    }

    private Optional<AssetName> processAssetName() {
        if(arguments.exists(ASSET_NAME)) {
            final Optional<AssetName> name = AssetName.parse(arguments.getValue(ASSET_NAME));

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

    private Model getMavenModel() throws IOException, XmlPullParserException {
        return new MavenXpp3Reader().read(Files.newBufferedReader(Paths.get("pom.xml"), UTF_8));
    }
}
