package com.jgcomptech.adoptopenjdk;

import ch.qos.logback.classic.Level;
import com.jgcomptech.adoptopenjdk.logging.Loggers;
import com.jgcomptech.adoptopenjdk.osutils.windows.powershell.choco.ChocoInstaller;
import com.jgcomptech.adoptopenjdk.progressbar.ProgressBar;
import com.jgcomptech.adoptopenjdk.progressbar.ProgressBarBuilder;
import com.jgcomptech.adoptopenjdk.progressbar.ProgressBarStyle;
import com.jgcomptech.adoptopenjdk.util.Exclusions;
import org.apache.commons.cli.*;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import static com.jgcomptech.adoptopenjdk.util.StringUtils.isBlank;
import static com.jgcomptech.adoptopenjdk.util.StringUtils.isNumeric;
import static java.nio.charset.StandardCharsets.UTF_8;

public class JUpdateApp {
    private final Logger logger = LoggerFactory.getLogger(JUpdateApp.class);
    private final JavaRelease CURRENT_LTS = JavaRelease.Java11;

    public JUpdateApp() throws IOException {
        Loggers.RootPackage
                .setName(JUpdateApp.class.getPackage().getName())
                .enableLimitedConsole(Level.INFO);

        APISettings.loadPropertiesFile();
        if(APISettings.isUseOAuth()) logger.debug("GitHub OAuth Active...");

        Exclusions.createNewFile();
        Exclusions.loadFile();
    }

    /**
     * Runs the application
     *
     * @param args an array of String arguments to be parsed
     */
    public void run(final String... args) throws IOException, XmlPullParserException {
        final MavenXpp3Reader reader = new MavenXpp3Reader();
        final Model model = reader.read(Files.newBufferedReader(Paths.get("pom.xml"), UTF_8));

        logger.info("JUpdate v" + model.getVersion() + " - AdoptOpenJDK Updater");
        logger.info("");
        //OS.load();
        //logger.info(OSInfo.getName());
        //logger.info(OSInfo.getNameExpanded());
        //logger.info(OS.NAME_EXPANDED);
        logger.info("");

        final CommandLine line = parseArguments(args);

        if(line.hasOption("help")) {
            printAppHelp();
        } else if(line.hasOption("demo")) {
            ChocoInstaller.install();
            logger.info("");
        } else if (line.hasOption("update")) {
            logger.info("Processing Update Info...");
            final String arg = line.getOptionValue("update");
            if(isNumeric(arg)) {
                final int version = Integer.parseInt(arg);

                //if(version < 8) {
                if(version < 8 || version > 15) {
                    logger.error("Illegal Update Parameter! Invalid Version Number!");
                    return;
                }

                final Optional<ReleaseType> releaseType = processReleaseType(line);

                if(!releaseType.isPresent()) return;

                final Optional<AssetJVMType> jvmType = processJVMType(line);

                if(!jvmType.isPresent()) return;

                final Optional<AssetName> assetName = processAssetName(line);

                final JavaRelease releases;

                switch (version) {
                    case 8:
                        releases = JavaRelease.Java8;
                        break;
                    case 9:
                        releases = JavaRelease.Java9;
                        break;
                    case 10:
                        releases = JavaRelease.Java10;
                        break;
                    case 11:
                        releases = JavaRelease.Java11;
                        break;
                    case 12:
                        releases = JavaRelease.Java12;
                        break;
                    case 13:
                        releases = JavaRelease.Java13;
                        break;
                    case 14:
                        releases = JavaRelease.Java14;
                        break;
                    case 15:
                        releases = JavaRelease.Java15;
                        break;
                    default:
                        releases = CURRENT_LTS;
//                        releases =
//                                new JavaRelease("Java " + version, version, "openjdk" + version + "-binaries");
                        break;
                }

                logger.info("Selected Java " + version + ", Please wait...");
                releases
                        .processReleases(releaseType.get(), jvmType.get())
                        .printMissingJDKAssets()
                        .printExtraJDKAssets();

                assetName.ifPresent(name -> getDownloadInfo(releases, name));
            } else if(isBlank(arg)) {
                final Optional<ReleaseType> releaseType = processReleaseType(line);

                if(!releaseType.isPresent()) return;

                final Optional<AssetJVMType> jvmType = processJVMType(line);

                if(!jvmType.isPresent()) return;

                final Optional<AssetName> assetName = processAssetName(line);

                if(!assetName.isPresent()) return;

                CURRENT_LTS
                        .processReleases(releaseType.get(), jvmType.get())
                        .printMissingJDKAssets()
                        .printExtraJDKAssets();

                assetName.ifPresent(name -> getDownloadInfo(CURRENT_LTS, name));
            } else {
                logger.error("Illegal Update Parameter! Invalid Version Number!");
            }
        } else {
            printAppHelp();
        }
    }


    private Optional<ReleaseType> processReleaseType(final CommandLine line) {
        if(line.hasOption("jdk") && line.hasOption("jre")) {
            logger.error("Illegal Update Parameter! Must select either jdk or jre not both!");
            return Optional.empty();
        }

        return line.hasOption("jre") ? Optional.of(ReleaseType.JRE) : Optional.of(ReleaseType.JDK);
    }

    private Optional<AssetJVMType> processJVMType(final CommandLine line) {
        if(line.hasOption("hotspot") && line.hasOption("openj9")) {
            logger.error("Illegal Update Parameter! Must select either hotspot or openj9 not both!");
            return Optional.empty();
        }

        return line.hasOption("openj9") ? Optional.of(AssetJVMType.openj9) : Optional.of(AssetJVMType.hotspot);
    }

    private Optional<AssetName> processAssetName(final CommandLine line) {
        if(line.hasOption("asset")) {
            final Optional<AssetName> name = AssetName.parse(line.getOptionValue("asset"));

            if (!name.isPresent()) {
                logger.error("Illegal Update Parameter! Must provide valid asset name!");
            }

            return name;
        }

        return Optional.empty();
    }

    private void getDownloadInfo(final JavaRelease release, final AssetName assetName) {
        final Optional<SimpleAsset> asset = release.getJdkHotspotAssets().get(assetName);
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

            download(a.getBrowserDownloadURL());
        }
    }

    private void download(final String url) {
        try {
            download(new URL(url));
        } catch (final MalformedURLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void download(final URL url) {
        try {
            final Download download = new Download(url);
            while(download.getSize() == -1) Thread.sleep(10);
            logger.info("Downloading installer...");
            try (final ProgressBar pb = new ProgressBarBuilder()
                                            .setTaskName("")
                                            .setInitialMax(download.getSize())
                                            .setStyle(ProgressBarStyle.UNICODE_BLOCK)
                                            .useFileProgressBarRenderer()
                                            .build()) {
                while(download.getStatus() == 0) {
                    pb.stepTo(download.getDownloaded());
                }
            }
            logger.info("Download Complete!");
        } catch (final InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * Parses application arguments
     *
     * @param args application arguments
     * @return {@code CommandLine} which represents a list of application
     * arguments.
     */
    private CommandLine parseArguments(final String... args) {
        final Options options = getOptions();
        final CommandLineParser parser = new DefaultParser();

        try {
            return parser.parse(options, args);

        } catch (final ParseException ex) {

            logger.error("Failed to parse command line arguments");
            logger.error(ex.toString());
            printAppHelp();

            System.exit(1);
        }

        return null;
    }

    /**
     * Generates application command line options
     *
     * @return application {@code Options}
     */
    private Options getOptions() {
//        JUpdate v0.0.1 - AdoptOpenJDK Updater
//
//        usage: JavaStatsEx [-?] [-d] [-h] [-j9] [-jdk] [-jre] [-p] [-u <Java
//        Version>]
//        -?,--help                    shows the help menu
//        -d,--download                downloads the installer
//        -h,--hotspot                 enables usage of hotspot jvm type
//        -j9,--openj9                 enables usage of openj9 jvm type
//        -jdk,--jdk                   enables usage of jdk
//        -jre,--jre                   enables usage of jre
//        -p,--pre                     enables use of prerelease assets
//        -u,--update <Java Version>   checks for updates to java

        return new Options()
                .addOption("?", "help", false, "shows the help menu")
                .addOption(Option.builder("u")
                        .longOpt("update")
                        .desc("checks for updates to java")
                        .hasArg()
                        .argName("Java Version")
                        .optionalArg(true)
                        .build())
                .addOption("p", "pre", false, "enables use of prerelease assets")
                .addOption("jdk", "jdk", false, "enables usage of jdk")
                .addOption("jre", "jre", false, "enables usage of jre")
                .addOption("h", "hotspot", false, "enables usage of hotspot jvm type")
                .addOption("j9", "openj9", false, "enables usage of openj9 jvm type")
                .addOption("d", "download", false, "downloads the installer")
                .addOption("a", "asset", true, "sets the asset name to install")
                .addOption("d", "demo", false, "demo code")
                .addOption(Option.builder("a")
                        .longOpt("asset")
                        .desc("sets the asset name to install")
                        .hasArg()
                        .argName("Asset Name")
                        .optionalArg(true)
                        .build());
    }

    /**
     * Prints application help
     */
    private void printAppHelp() {
        final Options options = getOptions();

        final HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("JavaStatsEx", options, true);
    }
}
