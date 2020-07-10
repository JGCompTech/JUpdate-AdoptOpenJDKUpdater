package com.jgcomptech.adoptopenjdk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;

import java.io.IOException;
import java.util.concurrent.Callable;

import static com.jgcomptech.adoptopenjdk.Settings.APP_VERSION;
import static com.jgcomptech.adoptopenjdk.Settings.CURRENT_LTS;
import static picocli.CommandLine.Command;
import static picocli.CommandLine.Option;

@Command(name = "jupdate", version = APP_VERSION,
        description = "A basic Java updater for AdoptOpenJDK.",
        optionListHeading = "%nOptions:%n")
public class Arguments implements Callable<Integer> {
    private final Logger logger = LoggerFactory.getLogger(Arguments.class);
    private CommandLine cmd;

    public CommandLine getCmd() {
        return cmd;
    }

    public void setCmd(CommandLine cmd) {
        this.cmd = cmd;
    }

    @SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
    @Option(names = {"-version", "--version"}, versionHelp = true, description = "display version info")
    private boolean versionInfoRequested = false;

    @SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
    @Option(names = {"-?", "--help"}, usageHelp = true, description = "display this help message")
    private boolean usageHelpRequested = false;

    @SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
    @Option(names = {"-jv", "--javaVersion"}, arity = "0..1",
            defaultValue = "11", fallbackValue = "11",
            description = "the version of java to lookup (default: ${DEFAULT-VALUE}),\n" +
                    "if specified without parameter: ${FALLBACK-VALUE}")
    private int version = CURRENT_LTS;

    @SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
    @Option(names = {"-debug", "--debug"}, description = "enables debug logging")
    private boolean debug = false;

    @SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
    @Option(names = {"-trace", "--trace"}, description = "enables trace logging")
    private boolean trace = false;

    @SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
    @Option(names = {"-jdk", "--jdk"}, description = "enables usage of jdk (default)")
    private boolean jdk = false;

    @SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
    @Option(names = {"-jre", "--jre"}, description = "enables usage of jre")
    private boolean jre = false;

    @SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
    @Option(names = {"-h", "--hotspot"}, description = "enables usage of hotspot jvm type (default)")
    private boolean hotspot = false;

    @SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
    @Option(names = {"-j9", "--openj9"}, description = "enables usage of openj9 jvm type")
    private boolean openJ9 = false;

    @SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
    @Option(names = {"-a", "--asset"}, description = "sets the asset name to install")
    private String asset = "";

    @SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
    @Option(names = {"-r", "--refresh"}, description = "overwrites the exclusions file")
    private boolean refresh = false;

    @SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
    @Option(names = {"-info", "--showassetinfo"}, description = "shows info about the os appropriate asset")
    private boolean showAssetInfo = false;

    @SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
    @Option(names = {"-b", "--boolean"}, description = "enables minimal output")
    private boolean showBoolean = false;

    @SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
    @Option(names = {"-pre", "--prerelease"}, description = "enables use of prerelease assets")
    private boolean prerelease = false;

    @SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
    @Option(names = {"-i", "--install"}, description = "downloads and installs the installer")
    private boolean install = false;

    @SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
    @Option(names = {"-d", "--download"}, description = "downloads the installer")
    private boolean download = false;

    @SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
    @Option(names = {"-dp", "--downloadpath"}, description = "sets the path to download to")
    private String downloadPath = "";

    @SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
    @Option(names = {"-apiid", "--apiid"}, description = "sets the id for api usage\n" +
            "(is ignored if both id and secret are not specified)")
    private String apiID = "";

    @SuppressWarnings({"FieldMayBeFinal", "CanBeFinal"})
    @Option(names = {"-apisecret", "--apisecret"}, description = "sets the secret for api usage\n" +
            "(is ignored if both id and secret are not specified)")
    private String apiSecret = "";

    public boolean isVersionInfoRequested() {
        return versionInfoRequested;
    }

    public boolean isUsageHelpRequested() {
        return usageHelpRequested;
    }

    public int getVersion() {
        return version;
    }

    public boolean isDebug() {
        return debug;
    }

    public boolean isTrace() {
        return trace;
    }

    public boolean isJdk() {
        return jdk;
    }

    public boolean isJre() {
        return jre;
    }

    public boolean isHotspot() {
        return hotspot;
    }

    public boolean isOpenJ9() {
        return openJ9;
    }

    public String getAsset() {
        return asset;
    }

    public boolean isRefresh() {
        return refresh;
    }

    public boolean isShowAssetInfo() {
        return showAssetInfo;
    }

    public boolean isShowBoolean() {
        return showBoolean;
    }

    public boolean isPrerelease() {
        return prerelease;
    }

    public String getDownloadPath() {
        return downloadPath;
    }

    public boolean isInstall() {
        return install;
    }

    public boolean isDownload() {
        return download;
    }

    public String getApiID() {
        return apiID;
    }

    public String getApiSecret() {
        return apiSecret;
    }

    /**
     * Runs the application
     * @return exit code
     * @throws IOException if any IO error occurs
     */
    @Override
    public Integer call() throws Exception {
        return new JUpdateApp(this).call();
    }
}
