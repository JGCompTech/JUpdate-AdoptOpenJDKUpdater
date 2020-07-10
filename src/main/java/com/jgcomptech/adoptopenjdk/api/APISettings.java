package com.jgcomptech.adoptopenjdk.api;

import com.jgcomptech.adoptopenjdk.Main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import static com.jgcomptech.adoptopenjdk.utils.Literals.FILE_SEPARATOR;

public final class APISettings {

    private static String oAuth_client_id = "";
    private static String oAuth_client_secret = "";
    private static int numberOfReleasesPerPage = 10;
    private static boolean useOAuth;

    private APISettings() { }

    public static String getOAuth_client_id() {
        return oAuth_client_id;
    }

    public static void setOAuth_client_id(final String oauth_client_id) {
        APISettings.oAuth_client_id = oauth_client_id;
    }

    public static String getOAuth_client_secret() {
        return oAuth_client_secret;
    }

    public static void setOAuth_client_secret(final String oauth_client_secret) {
        APISettings.oAuth_client_secret = oauth_client_secret;
    }

    public static int getNumberOfReleasesPerPage() {
        return numberOfReleasesPerPage;
    }

    public static void setNumberOfReleasesPerPage(final int numberOfReleasesPerPage) {
        APISettings.numberOfReleasesPerPage = numberOfReleasesPerPage;
    }

    public static boolean isUseOAuth() {
        return (!oAuth_client_id.isEmpty() && !oAuth_client_secret.isEmpty()) && useOAuth;
    }

    public static void setUseOAuth(final boolean useOAuth) {
        APISettings.useOAuth = useOAuth;
    }

    public static void loadPropertiesFile() throws IOException {
        final Properties properties = new Properties();
        File external = new File("app.properties");

        if(!external.exists()) {
            String path = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath()
                    .replace("/", FILE_SEPARATOR).substring(1);
            if(path.endsWith("jar")) {
                path = path.substring(0, path.lastIndexOf(FILE_SEPARATOR));
            }
            external = new File(path + FILE_SEPARATOR + "app.properties");
        }

        if (external.exists()) {
            //If external file exists load that
            try(final FileInputStream fis = new FileInputStream(external);
                final InputStreamReader in = new InputStreamReader(
                        fis, StandardCharsets.UTF_8)) {
                properties.load(in);
            }
        }

        oAuth_client_id = properties.getProperty("client_id", "");
        oAuth_client_secret = properties.getProperty("client_secret", "");
        useOAuth = Boolean.parseBoolean(properties.getProperty("use_oauth", "false"));
        if(oAuth_client_id.isEmpty() || oAuth_client_secret.isEmpty()) {
            useOAuth = false;
        }
    }
}
