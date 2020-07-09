package com.jgcomptech.adoptopenjdk;

import com.jgcomptech.adoptopenjdk.api.APISettings;
import com.jgcomptech.adoptopenjdk.enums.AssetType;
import com.jgcomptech.adoptopenjdk.utils.IntegerValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.Map;

public class JavaRelease {
    public static final Map<String, JavaRelease> releases = new LinkedHashMap<>();

    private final String name;
    private final int majorBuild;
    private final String url;
    private final SubRelease jdkHotspot = new SubRelease(AssetType.JDKHotspot, this);
    private final SubRelease jdkOpenJ9 = new SubRelease(AssetType.JDKOpenJ9, this);
    private final SubRelease jreHotspot = new SubRelease(AssetType.JREHotspot, this);
    private final SubRelease jreOpenJ9 = new SubRelease(AssetType.JREOpenJ9, this);
    private final IntegerValue pageCount = new IntegerValue(1);
    private final IntegerValue releaseCount = new IntegerValue(APISettings.getNumberOfReleasesPerPage());
    private final Logger logger = LoggerFactory.getLogger(JavaRelease.class);

    public JavaRelease(final int majorBuild) {
        this.majorBuild = majorBuild;
        this.name = "java" + majorBuild;
        this.url = "openjdk" + majorBuild + "-binaries";
    }

    public String getName() {
        return name;
    }

    public int getMajorBuild() {
        return majorBuild;
    }

    public String getUrl() {
        return url;
    }

    public SubRelease getJdkHotspot() {
        return jdkHotspot;
    }

    public SubRelease getJdkOpenJ9() {
        return jdkOpenJ9;
    }

    public SubRelease getJreHotspot() {
        return jreHotspot;
    }

    public SubRelease getJreOpenJ9() {
        return jreOpenJ9;
    }

    public int getPageCount() {
        return pageCount.get();
    }

    public int getReleaseCount() {
        return releaseCount.get();
    }

    public void incrementPageAndReleaseCount() {
        pageCount.increment();
        releaseCount.add(APISettings.getNumberOfReleasesPerPage());
    }

    @Override
    public String toString() {
        return "Java " + majorBuild;
    }
}
