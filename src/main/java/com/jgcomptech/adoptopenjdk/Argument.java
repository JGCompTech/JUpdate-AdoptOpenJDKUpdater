package com.jgcomptech.adoptopenjdk;

import org.apache.commons.cli.Option;

public enum Argument {
    ASSET_NAME(Option.builder("a")
            .longOpt("asset")
            .desc("sets the asset name to install")
            .hasArg()
            .argName("Asset Name")
            .optionalArg(true)
            .build()),
    BOOLEAN(new Option("b", "boolean", false, "enables minimal output")),
    DEBUG(new Option("debug", "debug", false, "enables debug logging")),
    DOWNLOAD(Option.builder("d")
                    .longOpt("download")
                    .desc("downloads the installer")
                    .hasArg()
                    .argName("Download Path")
                    .optionalArg(true)
                    .build()),
    HELP(new Option("?", "help", false, "shows the help menu")),
    HOTSPOT(new Option("h", "hotspot", false, "enables usage of hotspot jvm type")),
    INSTALL(Option.builder("i")
                    .longOpt("install")
                    .desc("downloads and installs the installer")
                    .hasArg()
                    .argName("Download Path")
                    .optionalArg(true)
                    .build()),
    JDK(new Option("jdk", "jdk", false, "enables usage of jdk")),
    JRE(new Option("jre", "jre", false, "enables usage of jre")),
    OPENJ9(new Option("j9", "openj9", false, "enables usage of openj9 jvm type")),
    PRERELEASE(new Option("pre", "prerelease", false, "enables use of prerelease assets")),
    REFRESH(new Option("r", "refresh", false, "overwrites the exclusions file")),
    SHOW_ASSET_INFO(new Option("info", "showassetinfo", false,
                    "shows info about the os appropriate asset")),
    TRACE(new Option("trace", "trace", false, "enables trace logging")),
    VERSION(Option.builder("v")
                    .longOpt("version")
                    .desc("checks for updates to java")
                    .hasArg()
                    .argName("Java Version")
                    .optionalArg(true)
                    .build());

    private Option option;

    Argument(Option option) {
        this.option = option;
    }

    public Option getOption() {
        return option;
    }
}
