package com.jgcomptech.adoptopenjdk;

import com.jgcomptech.adoptopenjdk.demo.Demo_W_Exclusions_File;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public final class APISettings {
    private static String companyName = "AdoptOpenJDK";
    private static String oAuth_client_id = "";
    private static String oAuth_client_secret = "";
    private static int numberOfReleasesPerPage = 10;
    private static boolean useOAuth;

    private APISettings() { }

    public static String getCompanyName() {
        return companyName;
    }

    public static void setCompanyName(final String companyName) {
        APISettings.companyName = companyName;
    }

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
        if(oAuth_client_id.isEmpty() || oAuth_client_secret.isEmpty()) {
            return false;
        }
        return useOAuth;
    }

    public static void setUseOAuth(final boolean useOAuth) {
        APISettings.useOAuth = useOAuth;
    }

    public static void loadPropertiesFile() throws IOException {
        final Properties properties = new Properties();
        final File external = new File("app.properties");

        if (external.exists()) {
            //If external file exists load that
            try(final FileInputStream fis = new FileInputStream(external);
                final InputStreamReader in = new InputStreamReader(
                        fis, StandardCharsets.UTF_8)) {
                properties.load(in);
            }
        } else {
            //If external file does not exist load embedded file
            properties.load(Demo_W_Exclusions_File.class.getClassLoader().getResourceAsStream("app.properties"));
        }

        oAuth_client_id = properties.getProperty("client_id", "");
        oAuth_client_secret = properties.getProperty("client_secret", "");
        useOAuth = Boolean.parseBoolean(properties.getProperty("use_oauth", "false"));
        if(oAuth_client_id.isEmpty() || oAuth_client_secret.isEmpty()) {
            useOAuth = false;
        }
    }
}
