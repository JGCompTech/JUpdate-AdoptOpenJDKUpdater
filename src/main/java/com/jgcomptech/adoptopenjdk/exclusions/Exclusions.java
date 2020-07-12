package com.jgcomptech.adoptopenjdk.exclusions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jgcomptech.adoptopenjdk.JavaName;
import com.jgcomptech.adoptopenjdk.JavaRelease;
import com.jgcomptech.adoptopenjdk.api.BaseAssets;
import com.jgcomptech.adoptopenjdk.enums.AssetName;
import com.jgcomptech.adoptopenjdk.enums.AssetOS;
import com.jgcomptech.adoptopenjdk.enums.AssetType;
import com.jgcomptech.adoptopenjdk.exclusions.releases.*;
import org.hjson.JsonValue;
import org.hjson.Stringify;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.jgcomptech.adoptopenjdk.utils.HJsonUtils.convertObjectToHJson;
import static com.jgcomptech.adoptopenjdk.utils.HJsonUtils.writeHJsonToFile;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * The main processing class for generating API asset exclusions.
 */
public class Exclusions {
    private static ExclusionsBase exclusions;

    /**
     * Create new empty file.
     * @param overwrite if true overwrites any existing file
     * @throws IOException if an error occurs
     */
    public static void createNewEmptyFile(final boolean overwrite) throws IOException {
        final Path exclusionsFile = Paths.get("exclusions.hjson");

        if (overwrite || (!exclusionsFile.toFile().exists() && !exclusionsFile.toFile().isDirectory())) {
            ObjectMapper objectMapper = new ObjectMapper();
            ExclusionsBase newBase = new ExclusionsBase();
            String output = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(newBase);
            String hjsonString = JsonValue.readHjson(output).toString(Stringify.HJSON);
            try(BufferedWriter writer = Files.newBufferedWriter(exclusionsFile, UTF_8)){
                writer.write(hjsonString);
            }
        }
    }

    /**
     * Create new file.
     * @param overwrite if true overwrites any existing file
     * @throws IOException if an error occurs
     */
    public static void createNewFile(final boolean overwrite) throws IOException {
        final Path exclusionsFile = Paths.get("exclusions.hjson");

        if (overwrite || (!exclusionsFile.toFile().exists() && !exclusionsFile.toFile().isDirectory())) {
            final ExclusionsBase newBase = new ExclusionsBase();
            newBase.releases.put(JavaName.Java8.getName(), new Java8());
            newBase.releases.put(JavaName.Java9.getName(), new Java9());
            newBase.releases.put(JavaName.Java10.getName(), new Java10());
            newBase.releases.put(JavaName.Java11.getName(), new Java11());
            newBase.releases.put(JavaName.Java12.getName(), new Java12());
            newBase.releases.put(JavaName.Java13.getName(), new Java13());
            newBase.releases.put(JavaName.Java14.getName(), new Java14());
            newBase.releases.put(JavaName.Java15.getName(), new Java15());

            writeHJsonToFile(convertObjectToHJson(newBase), exclusionsFile);
        }
    }

    private static List<AssetOS> getDisabledOSList(final JavaRelease release, final AssetType releaseType) {
        final List<AssetOS> list = new ArrayList<>();
        if (releaseType == AssetType.JDKHotspot) {
            list.addAll(exclusions.releases.get(release.getName()).JDKHotspot.getDisabledOS());
        } else if (releaseType == AssetType.JREHotspot) {
            list.addAll(exclusions.releases.get(release.getName()).JREHotspot.getDisabledOS());
        } else if (releaseType == AssetType.JDKOpenJ9) {
            list.addAll(exclusions.releases.get(release.getName()).JDKOpenJ9.getDisabledOS());
        } else if (releaseType == AssetType.JREOpenJ9) {
            list.addAll(exclusions.releases.get(release.getName()).JREOpenJ9.getDisabledOS());
        }
        return list;
    }

    private static List<AssetName> getDisabledAssetList(final JavaRelease release, final AssetType releaseType) {
        final List<AssetName> list = new ArrayList<>();
        if (releaseType == AssetType.JDKHotspot) {
            list.addAll(exclusions.releases.get(release.getName()).JDKHotspot.getDisabledAssets());
        } else if (releaseType == AssetType.JREHotspot) {
            list.addAll(exclusions.releases.get(release.getName()).JREHotspot.getDisabledAssets());
        } else if (releaseType == AssetType.JDKOpenJ9) {
            list.addAll(exclusions.releases.get(release.getName()).JDKOpenJ9.getDisabledAssets());
        } else if (releaseType == AssetType.JREOpenJ9) {
            list.addAll(exclusions.releases.get(release.getName()).JREOpenJ9.getDisabledAssets());
        }
        return list;
    }

    private static boolean isInstallersDisabled(final JavaRelease release, final AssetType releaseType) {
        if (releaseType == AssetType.JDKHotspot) {
            return exclusions.releases.get(release.getName()).JDKHotspot.isInstallersDisabled();
        } else if (releaseType == AssetType.JREHotspot) {
            return exclusions.releases.get(release.getName()).JREHotspot.isInstallersDisabled();
        } else if (releaseType == AssetType.JDKOpenJ9) {
            return exclusions.releases.get(release.getName()).JDKOpenJ9.isInstallersDisabled();
        } else if (releaseType == AssetType.JREOpenJ9) {
            return exclusions.releases.get(release.getName()).JREOpenJ9.isInstallersDisabled();
        } else throw new IllegalArgumentException();
    }

    private static boolean isXLDisabled(final JavaRelease release, final AssetType releaseType) {
        if (releaseType == AssetType.JDKHotspot) {
            return exclusions.releases.get(release.getName()).JDKHotspot.isXLDisabled();
        } else if (releaseType == AssetType.JREHotspot) {
            return exclusions.releases.get(release.getName()).JREHotspot.isXLDisabled();
        } else if (releaseType == AssetType.JDKOpenJ9) {
            return exclusions.releases.get(release.getName()).JDKOpenJ9.isXLDisabled();
        } else if (releaseType == AssetType.JREOpenJ9) {
            return exclusions.releases.get(release.getName()).JREOpenJ9.isXLDisabled();
        } else throw new IllegalArgumentException();
    }

    private static void enableRelease(final String releaseName) {
        final JavaRelease release = JavaRelease.getReleases().get(releaseName);

        if(release != null) {
            final BaseAssets javajdkHotspotAssets = release.getJdkHotspot().getAssets().enableAllAssets();
            final BaseAssets javajreHotspotAssets = release.getJreHotspot().getAssets().enableAllAssets();
            final BaseAssets javajdkOpenJ9Assets = release.getJdkOpenJ9().getAssets().enableAllAssets();
            final BaseAssets javajreOpenJ9Assets = release.getJreOpenJ9().getAssets().enableAllAssets();

            getDisabledOSList(release, AssetType.JDKHotspot).forEach(javajdkHotspotAssets::disableOS);
            getDisabledOSList(release, AssetType.JREHotspot).forEach(javajreHotspotAssets::disableOS);
            getDisabledOSList(release, AssetType.JDKOpenJ9).forEach(javajdkOpenJ9Assets::disableOS);
            getDisabledOSList(release, AssetType.JREOpenJ9).forEach(javajreOpenJ9Assets::disableOS);

            getDisabledAssetList(release, AssetType.JDKHotspot).forEach(javajdkHotspotAssets::disableAsset);
            getDisabledAssetList(release, AssetType.JREHotspot).forEach(javajreHotspotAssets::disableAsset);
            getDisabledAssetList(release, AssetType.JDKOpenJ9).forEach(javajdkOpenJ9Assets::disableAsset);
            getDisabledAssetList(release, AssetType.JREOpenJ9).forEach(javajreOpenJ9Assets::disableAsset);

            if(isInstallersDisabled(release, AssetType.JDKHotspot)) {
                javajdkHotspotAssets.disableAllInstallers();
            }

            if(isInstallersDisabled(release, AssetType.JREHotspot)) {
                javajreHotspotAssets.disableAllInstallers();
            }

            if(isInstallersDisabled(release, AssetType.JDKOpenJ9)) {
                javajdkOpenJ9Assets.disableAllInstallers();
            }

            if(isInstallersDisabled(release, AssetType.JREOpenJ9)) {
                javajreOpenJ9Assets.disableAllInstallers();
            }

            if(isXLDisabled(release, AssetType.JDKHotspot)) {
                javajdkHotspotAssets.disableXL();
            }

            if(isXLDisabled(release, AssetType.JREHotspot)) {
                javajreHotspotAssets.disableXL();
            }

            if(isXLDisabled(release, AssetType.JDKOpenJ9)) {
                javajdkOpenJ9Assets.disableXL();
            }

            if(isXLDisabled(release, AssetType.JREOpenJ9)) {
                javajreOpenJ9Assets.disableXL();
            }
        }
    }

    /**
     * Load the exclusions file.
     * @throws IOException if an error occurs
     */
    public static void loadFile() throws IOException {
        loadFile("exclusions.hjson");
    }

    /**
     * Load the exclusions file.
     * @param filePath the file path
     * @throws IOException if an error occurs
     */
    public static void loadFile(final String filePath) throws IOException {
        final File exclusionsFile = new File(filePath);

        if (exclusionsFile.exists()) {
            try(final FileInputStream fis = new FileInputStream(exclusionsFile);
                final InputStreamReader in = new InputStreamReader(
                        fis, StandardCharsets.UTF_8)) {
                String hjson = JsonValue.readHjson(in).toString();
                exclusions = new ObjectMapper().readValue(hjson, ExclusionsBase.class);

                for(final Map.Entry<String, ExclusionsBaseRelease> release : exclusions.releases.entrySet()) {
                    enableRelease(release.getKey());
                }
            }
        }
    }
}
