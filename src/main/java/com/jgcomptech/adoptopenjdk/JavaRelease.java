package com.jgcomptech.adoptopenjdk;

import com.jgcomptech.adoptopenjdk.enums.AssetType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.Map;

/** The main object that contains all info about a Java version. */
public class JavaRelease {
    private static final Map<String, JavaRelease> releases = new LinkedHashMap<>();

    /**
     * Returns a list of all releases for this version.
     * @return a list of all releases for this version
     */
    public static Map<String, JavaRelease> getReleases() {
        return releases;
    }

    private final String name;
    private final int majorBuild;
    private final String url;
    private final SubRelease jdkHotspot = new SubRelease(AssetType.JDKHotspot, this);
    private final SubRelease jdkOpenJ9 = new SubRelease(AssetType.JDKOpenJ9, this);
    private final SubRelease jreHotspot = new SubRelease(AssetType.JREHotspot, this);
    private final SubRelease jreOpenJ9 = new SubRelease(AssetType.JREOpenJ9, this);
    private final Logger logger = LoggerFactory.getLogger(JavaRelease.class);

    /**
     * Instantiates a new Java release.
     * @param majorBuild the major build
     */
    public JavaRelease(final int majorBuild) {
        this.majorBuild = majorBuild;
        this.name = "java" + majorBuild;
        this.url = "openjdk" + majorBuild + "-binaries";
    }

    /**
     * Returns the Java version name.
     * @return the Java version name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the major build.
     *
     * @return the major build
     */
    public int getMajorBuild() {
        return majorBuild;
    }

    /**
     * Gets the url stub for the API.
     *
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Gets the JDK Hotspot Sub-Release.
     *
     * @return the JDK Hotspot Sub-Release
     */
    public SubRelease getJdkHotspot() {
        return jdkHotspot;
    }

    /**
     * Gets the JDK OpenJ9 Sub-Release.
     *
     * @return the JDK OpenJ9 Sub-Release
     */
    public SubRelease getJdkOpenJ9() {
        return jdkOpenJ9;
    }

    /**
     * Gets the JRE Hotspot Sub-Release.
     *
     * @return the JRE Hotspot Sub-Release
     */
    public SubRelease getJreHotspot() {
        return jreHotspot;
    }

    /**
     * Gets the JRE OpenJ9 Sub-Release.
     *
     * @return the JRE OpenJ9 Sub-Release
     */
    public SubRelease getJreOpenJ9() {
        return jreOpenJ9;
    }

    @Override
    public String toString() {
        return "Java " + majorBuild;
    }
}
