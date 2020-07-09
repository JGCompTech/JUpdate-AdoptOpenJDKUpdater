package com.jgcomptech.adoptopenjdk;

import ch.qos.logback.classic.Level;
import com.jgcomptech.adoptopenjdk.utils.logging.Loggers;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.jgcomptech.adoptopenjdk.Argument.*;

public class Arguments {
//        JUpdate v0.1.0 - AdoptOpenJDK Updater
//
//        usage: JUpdate [-?] [-a <Asset Name>] [-b] [-d <Download Path>] [-debug]
//       [-h] [-i <Download Path>] [-info] [-j9] [-jdk] [-jre] [-pre] [-r]
//       [-trace] [-v <Java Version>]
//        -?,--help                       shows the help menu
//        -a,--asset <Asset Name>         sets the asset name to install
//        -b,--boolean                    enables minimal output
//        -d,--download <Download Path>   downloads the installer
//        -debug,--debug                  enables debug logging
//        -h,--hotspot                    enables usage of hotspot jvm type
//        -i,--install <Download Path>    downloads and installs the installer
//        -info,--showassetinfo           shows info about the os appropriate asset
//        -j9,--openj9                    enables usage of openj9 jvm type
//        -jdk,--jdk                      enables usage of jdk
//        -jre,--jre                      enables usage of jre
//        -pre,--prerelease               enables use of prerelease assets
//        -r,--refresh                    overwrites the exclusions file
//        -trace,--trace                  enables trace logging
//        -v,--version <Java Version>     checks for updates to java


    private final Logger logger = LoggerFactory.getLogger(Arguments.class);
    private final CommandLine line;

    public Arguments(final String... args) {
        line = parse(args);
        if(exists(BOOLEAN)) Loggers.RootPackage.enableLimitedConsole(Level.OFF);
        if(exists(DEBUG)) Loggers.RootPackage.enableLimitedConsole(Level.DEBUG);
        if(exists(TRACE)) Loggers.RootPackage.enableLimitedConsole(Level.TRACE);
    }

    /**
     * Parses application arguments
     *
     * @param args application arguments
     * @return {@code CommandLine} which represents a list of application
     * arguments.
     */
    private CommandLine parse(final String... args) {
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
    public Options getOptions() {
        return new Options()
                .addOption(ASSET_NAME.getOption())
                .addOption(BOOLEAN.getOption())
                .addOption(DEBUG.getOption())
                .addOption(DOWNLOAD.getOption())
                .addOption(BOOLEAN.getOption())
                .addOption(HELP.getOption())
                .addOption(HOTSPOT.getOption())
                .addOption(INSTALL.getOption())
                .addOption(JDK.getOption())
                .addOption(JRE.getOption())
                .addOption(OPENJ9.getOption())
                .addOption(PRERELEASE.getOption())
                .addOption(REFRESH.getOption())
                .addOption(SHOW_ASSET_INFO.getOption())
                .addOption(TRACE.getOption())
                .addOption(VERSION.getOption());
    }

    public boolean exists(final Argument option) {
        return line.hasOption(option.getOption().getLongOpt());
    }

    public String getValue(final Argument option) {
        return line.getOptionValue(option.getOption().getLongOpt());
    }

    /**
     * Prints application help
     */
    public void printAppHelp() {
        final Options options = getOptions();

        final HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("JUpdate", options, true);

        //TODO: Decide on help text
//        final String header = "Header Goes Here";
//        final String footer = "Footer Goes Here";
//
//        formatter.printHelp("JUpdate", header, options, footer,true);
    }
}
