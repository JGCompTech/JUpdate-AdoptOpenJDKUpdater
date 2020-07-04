package com.jgcomptech.adoptopenjdk;

import com.jgcomptech.adoptopenjdk.api.APISettings;
import com.jgcomptech.adoptopenjdk.api.BaseAssets;
import com.jgcomptech.adoptopenjdk.enums.AssetJVMType;
import com.jgcomptech.adoptopenjdk.enums.AssetName;
import com.jgcomptech.adoptopenjdk.enums.ReleaseType;
import com.jgcomptech.adoptopenjdk.utils.IntegerValue;
import com.jgcomptech.adoptopenjdk.utils.recentJava.CollectorsExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

import static com.jgcomptech.adoptopenjdk.api.ReleaseProcessor.acquireNextReleasePage;

public class JavaRelease {
    public static final JavaRelease Java8 = new JavaRelease("Java 8", 8, "openjdk8-binaries");
    public static final JavaRelease Java9 = new JavaRelease("Java 9", 9, "openjdk9-binaries");
    public static final JavaRelease Java10 = new JavaRelease("Java 10", 10, "openjdk10-binaries");
    public static final JavaRelease Java11 = new JavaRelease("Java 11", 11, "openjdk11-binaries");
    public static final JavaRelease Java12 = new JavaRelease("Java 12", 12, "openjdk12-binaries");
    public static final JavaRelease Java13 = new JavaRelease("Java 13", 13, "openjdk13-binaries");
    public static final JavaRelease Java14 = new JavaRelease("Java 14", 14, "openjdk14-binaries");
    public static final JavaRelease Java15 = new JavaRelease("Java 15", 15, "openjdk15-binaries");

    private final String name;
    private final int majorBuild;
    private final String url;
    private final BaseAssets jdkHotspotAssets = new BaseAssets(this);
    private final BaseAssets jreHotspotAssets = new BaseAssets(this);
    private final BaseAssets jdkOpenJ9Assets = new BaseAssets(this);
    private final BaseAssets jreOpenJ9Assets = new BaseAssets(this);
    private final IntegerValue pageCount = new IntegerValue(1);
    private final IntegerValue releaseCount = new IntegerValue(APISettings.getNumberOfReleasesPerPage());
    private final Logger logger = LoggerFactory.getLogger(JavaRelease.class);

    public JavaRelease(final String name, final int majorBuild, final String url) {
        this.name = name;
        this.majorBuild = majorBuild;
        this.url = url;
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

    public BaseAssets getJdkHotspotAssets() {
        return jdkHotspotAssets;
    }

    public BaseAssets getJreHotspotAssets() {
        return jreHotspotAssets;
    }

    public BaseAssets getJdkOpenJ9Assets() {
        return jdkOpenJ9Assets;
    }

    public BaseAssets getJreOpenJ9Assets() {
        return jreOpenJ9Assets;
    }

    public int getPageCount() {
        return pageCount.get();
    }

    public int getReleaseCount() {
        return releaseCount.get();
    }

    public JavaRelease incrementPageCount() {
        pageCount.increment();
        return this;
    }

    public JavaRelease decrementPageCount() {
        pageCount.decrement();
        return this;
    }

    public JavaRelease resetPageCount() {
        pageCount.set(1);
        return this;
    }

    public JavaRelease incrementReleaseCount() {
        releaseCount.add(APISettings.getNumberOfReleasesPerPage());
        return this;
    }

    public JavaRelease decrementReleaseCount() {
        releaseCount.subtract(APISettings.getNumberOfReleasesPerPage());
        return this;
    }

    public JavaRelease resetReleaseCount() {
        releaseCount.set(APISettings.getNumberOfReleasesPerPage());
        return this;
    }

    public boolean isAlljdkHotspotAssetsAcquired() {
        return jdkHotspotAssets.isAllAcquired();
    }

    public boolean isAlljreHotspotAssetsAcquired() {
        return jreHotspotAssets.isAllAcquired();
    }

    public boolean isAlljdkOpenJ9AssetsAcquired() {
        return jdkOpenJ9Assets.isAllAcquired();
    }

    public boolean isAlljreOpenJ9AssetsAcquired() {
        return jreOpenJ9Assets.isAllAcquired();
    }

    @SuppressWarnings("UnusedReturnValue")
    public JavaRelease processReleases(final ReleaseType type, final AssetJVMType jvmType) throws IOException {
        logger.info("~ Processing Java " + majorBuild + ' ' + type + ' ' + jvmType + "...");
        boolean allAcquired;
        do {
            allAcquired = acquireNextReleasePage(this, type, jvmType);
        } while(!allAcquired);
        return this;
    }

    public List<AssetName> getMissingJDKAssets() {
        return jdkHotspotAssets.getEnabledAssets().stream()
                .filter(asset -> !jdkHotspotAssets.contains(asset)).collect(CollectorsExt.toUnmodifiableList());
    }

    public List<AssetName> getMissingJREAssets() {
        return jreHotspotAssets.getEnabledAssets().stream()
                .filter(asset -> !jreHotspotAssets.contains(asset)).collect(CollectorsExt.toUnmodifiableList());
    }

    public List<AssetName> getExtraJDKAssets() {
        return jdkHotspotAssets.getAll().keySet().stream()
                .filter(asset -> !jdkHotspotAssets.getEnabledAssets().contains(asset))
                .collect(CollectorsExt.toUnmodifiableList());
    }

    public List<AssetName> getExtraJREAssets() {
        return jreHotspotAssets.getAll().keySet().stream()
                .filter(asset -> !jreHotspotAssets.getEnabledAssets().contains(asset))
                .collect(CollectorsExt.toUnmodifiableList());
    }

    @SuppressWarnings("UnusedReturnValue")
    public JavaRelease printMissingJDKAssets() {
        getMissingJDKAssets()
                .forEach(a -> logger.debug("!!" + name + " JDK Asset NOT FOUND: " + a.name() + "!!"));
        return this;
    }

    @SuppressWarnings("UnusedReturnValue")
    public JavaRelease printMissingJREAssets() {
        getMissingJREAssets()
                .forEach(a -> logger.debug("!!" + name + " JRE Asset NOT FOUND: " + a.name() + "!!"));
        return this;
    }

    @SuppressWarnings("UnusedReturnValue")
    public JavaRelease printExtraJDKAssets() {
        getExtraJDKAssets()
                .forEach(a -> logger.debug("!!Unexpected " + name + " JDK Asset FOUND: " + a.name() + "!!"));
        return this;
    }

    @SuppressWarnings("UnusedReturnValue")
    public JavaRelease printExtraJREAssets() {
        getExtraJREAssets()
                .forEach(a -> logger.debug("!!Unexpected " + name + " JRE Asset FOUND: " + a.name() + "!!"));
        return this;
    }
}
