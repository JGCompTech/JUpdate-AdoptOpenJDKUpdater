package com.jgcomptech.adoptopenjdk;

/**
 * A list of settings that are used throughout the application.
 */
public class Settings {
    /**
     * The application version.
     */
    public static final String APP_VERSION = "0.1.2";
    /**
     * The company name string for use in all URLs and paths.
     */
    public static final String COMPANY_NAME = "AdoptOpenJDK";
    /**
     * The registry path for the JDK installation checks.
     */
    public static final String JDK_REGISTRY_PATH = "SOFTWARE\\" + COMPANY_NAME + "\\JDK\\";
    /**
     * The registry path for the JRE installation checks.
     */
    public static final String JRE_REGISTRY_PATH = "SOFTWARE\\" + COMPANY_NAME + "\\JRE\\";
    /**
     * The default installation path for windows.
     */
    public static final String WINDOWS_DEFAULT_INSTALL_PATH = "C:\\Program Files\\" + COMPANY_NAME;
    /**
     * The current Java LTS version number.
     */
    public static final int CURRENT_LTS = 11;
}
