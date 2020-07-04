package com.jgcomptech.adoptopenjdk.util;

import com.jgcomptech.adoptopenjdk.AssetName;
import com.jgcomptech.adoptopenjdk.AssetOS;
import com.jgcomptech.adoptopenjdk.BaseAssets;
import com.jgcomptech.adoptopenjdk.JavaRelease;
import org.hjson.JsonObject;
import org.hjson.JsonValue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import static com.jgcomptech.adoptopenjdk.util.StringUtils.unwrap;

public class Exclusions {
    private static JsonObject exclusions;

    public static void createNewEmptyFile() throws IOException {
        final File exclusionsFile = new File("exclusions.hjson");

        if (!exclusionsFile.exists()) {
            final Collection<String> lines = new ArrayList<>();
            lines.add("{");
            lines.add("    java8disabledOS: [");
            lines.add("    ]");
            lines.add("    java9disabledOS: [");
            lines.add("    ]");
            lines.add("    java10disabledOS: [");
            lines.add("    ]");
            lines.add("    java11disabledOS: [");
            lines.add("    ]");
            lines.add("    java12disabledOS: [");
            lines.add("    ]");
            lines.add("    java13disabledOS: [");
            lines.add("    ]");
            lines.add("    java14disabledOS: [");
            lines.add("    ]");
            lines.add("    java15disabledOS: [");
            lines.add("    ]");
            lines.add("    java8disabledAssets: [");
            lines.add("    ]");
            lines.add("    java9disabledAssets: [");
            lines.add("    ]");
            lines.add("    java10disabledAssets: [");
            lines.add("    ]");
            lines.add("    java11disabledAssets: [");
            lines.add("    ]");
            lines.add("    java12disabledAssets: [");
            lines.add("    ]");
            lines.add("    java13disabledAssets: [");
            lines.add("    ]");
            lines.add("    java14disabledAssets: [");
            lines.add("    ]");
            lines.add("    java15disabledAssets: [");
            lines.add("    ]");
            lines.add("    java8disableInstallers: false");
            lines.add("    java9disableInstallers: false");
            lines.add("    java10disableInstallers: false");
            lines.add("    java11disableInstallers: false");
            lines.add("    java12disableInstallers: false");
            lines.add("    java13disableInstallers: false");
            lines.add("    java14disableInstallers: false");
            lines.add("    java15disableInstallers: false");
            lines.add("}");

            Files.write(Paths.get("exclusions.hjson"),
                    lines,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);
        }
    }

    public static void createNewFile() throws IOException {
        final File exclusionsFile = new File("exclusions.hjson");

        if (!exclusionsFile.exists()) {
            final Collection<String> lines = new ArrayList<>();
            lines.add("{");
            lines.add("    java8disabledOS: [");
            lines.add("    ]");
            lines.add("    java9disabledOS: [");
            lines.add("        arm_linux");
            lines.add("        sparcv9_solaris");
            lines.add("        x64_solaris");
            lines.add("        x86-32_windows");
            lines.add("    ]");
            lines.add("    java10disabledOS: [");
            lines.add("        sparcv9_solaris");
            lines.add("        x64_solaris");
            lines.add("        x86-32_windows");
            lines.add("    ]");
            lines.add("    java11disabledOS: [");
            lines.add("        sparcv9_solaris");
            lines.add("        x64_solaris");
            lines.add("    ]");
            lines.add("    java12disabledOS: [");
            lines.add("        sparcv9_solaris");
            lines.add("        x64_solaris");
            lines.add("    ]");
            lines.add("    java13disabledOS: [");
            lines.add("        sparcv9_solaris");
            lines.add("        x64_solaris");
            lines.add("    ]");
            lines.add("    java14disabledOS: [");
            lines.add("        sparcv9_solaris");
            lines.add("        x64_solaris");
            lines.add("    ]");
            lines.add("    java15disabledOS: [");
            lines.add("        sparcv9_solaris");
            lines.add("        x64_solaris");
            lines.add("    ]");
            lines.add("    java8disabledAssets: [");
            lines.add("    ]");
            lines.add("    java9disabledAssets: [");
            lines.add("        aarch64_linux_tar_gz_json");
            lines.add("        ppc64le_linux_tar_gz_json");
            lines.add("        ppc64_aix_tar_gz_json");
            lines.add("        s390x_linux_tar_gz_json");
            lines.add("        x64_linux_tar_gz_json");
            lines.add("        x64_mac_tar_gz_json");
            lines.add("        x64_windows_zip_json");
            lines.add("    ]");
            lines.add("    java10disabledAssets: [");
            lines.add("        aarch64_linux_tar_gz_json");
            lines.add("        arm_linux_tar_gz_json");
            lines.add("        ppc64le_linux_tar_gz_json");
            lines.add("        ppc64_aix_tar_gz_json");
            lines.add("        s390x_linux_tar_gz_json");
            lines.add("        x64_linux_tar_gz_json");
            lines.add("        x64_mac_tar_gz_json");
            lines.add("        x64_windows_zip_json");
            lines.add("    ]");
            lines.add("    java11disabledAssets: [");
            lines.add("    ]");
            lines.add("    java12disabledAssets: [");
            lines.add("        ppc64_aix_tar_gz_json");
            lines.add("    ]");
            lines.add("    java13disabledAssets: [");
            lines.add("    ]");
            lines.add("    java14disabledAssets: [");
            lines.add("    ]");
            lines.add("    java15disabledAssets: [");
            lines.add("    ]");
            lines.add("    java8disableInstallers: false");
            lines.add("    java9disableInstallers: true");
            lines.add("    java10disableInstallers: true");
            lines.add("    java11disableInstallers: false");
            lines.add("    java12disableInstallers: false");
            lines.add("    java13disableInstallers: false");
            lines.add("    java14disableInstallers: false");
            lines.add("    java15disableInstallers: false");
            lines.add("}");

            Files.write(Paths.get("exclusions.hjson"),
                    lines,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);
        }
    }

    private static String buildReleaseKey(final JavaRelease release) {
        return release.getName().replace(" ", "").toLowerCase(Locale.ENGLISH);
    }

    private static List<String> getOSList(final JavaRelease release) {
        final List<String> list = new ArrayList<>();
        exclusions.get(buildReleaseKey(release) + "disabledOS").asArray()
                .forEach(e -> list.add(unwrap(e.toString(), '"')));
        return list;
    }

    private static List<String> getAssetList(final JavaRelease release) {
        final List<String> list = new ArrayList<>();
        exclusions.get(buildReleaseKey(release) + "disabledAssets").asArray()
                .forEach(e -> list.add(unwrap(e.toString(), '"')));
        return list;
    }

    private static boolean getInstallerStatus(final JavaRelease release) {
        return exclusions.getBoolean(buildReleaseKey(release) + "disableInstallers", false);
    }

    public static void loadFile() throws IOException {
        loadFile("exclusions.hjson");
    }

    public static void loadFile(final String filePath) throws IOException {
        final File exclusionsFile = new File(filePath);

        if (exclusionsFile.exists()) {
            try(final FileInputStream fis = new FileInputStream(exclusionsFile);
                final InputStreamReader in = new InputStreamReader(
                        fis, StandardCharsets.UTF_8)) {
                exclusions = JsonValue.readHjson(in).asObject();

                final List<String> java8OS = getOSList(JavaRelease.Java8);
                final List<String> java9OS = getOSList(JavaRelease.Java9);
                final List<String> java10OS = getOSList(JavaRelease.Java10);
                final List<String> java11OS = getOSList(JavaRelease.Java11);
                final List<String> java12OS = getOSList(JavaRelease.Java12);
                final List<String> java13OS = getOSList(JavaRelease.Java13);
                final List<String> java14OS = getOSList(JavaRelease.Java14);
                final List<String> java15OS = getOSList(JavaRelease.Java15);

                final List<String> java8Assets = getAssetList(JavaRelease.Java8);
                final List<String> java9Assets = getAssetList(JavaRelease.Java9);
                final List<String> java10Assets = getAssetList(JavaRelease.Java10);
                final List<String> java11Assets = getAssetList(JavaRelease.Java11);
                final List<String> java12Assets = getAssetList(JavaRelease.Java12);
                final List<String> java13Assets = getAssetList(JavaRelease.Java13);
                final List<String> java14Assets = getAssetList(JavaRelease.Java14);
                final List<String> java15Assets = getAssetList(JavaRelease.Java15);

                final boolean java8InstallersDisabled = getInstallerStatus(JavaRelease.Java8);
                final boolean java9InstallersDisabled = getInstallerStatus(JavaRelease.Java9);
                final boolean java10InstallersDisabled = getInstallerStatus(JavaRelease.Java10);
                final boolean java11InstallersDisabled = getInstallerStatus(JavaRelease.Java11);
                final boolean java12InstallersDisabled = getInstallerStatus(JavaRelease.Java12);
                final boolean java13InstallersDisabled = getInstallerStatus(JavaRelease.Java13);
                final boolean java14InstallersDisabled = getInstallerStatus(JavaRelease.Java14);
                final boolean java15InstallersDisabled = getInstallerStatus(JavaRelease.Java15);

                final BaseAssets java8jdkHotspotAssets = JavaRelease.Java8.getJdkHotspotAssets().enableAllAssets();
                final BaseAssets java9jdkHotspotAssets = JavaRelease.Java9.getJdkHotspotAssets().enableAllAssets();
                final BaseAssets java10jdkHotspotAssets = JavaRelease.Java10.getJdkHotspotAssets().enableAllAssets();
                final BaseAssets java11jdkHotspotAssets = JavaRelease.Java11.getJdkHotspotAssets().enableAllAssets();
                final BaseAssets java12jdkHotspotAssets = JavaRelease.Java12.getJdkHotspotAssets().enableAllAssets();
                final BaseAssets java13jdkHotspotAssets = JavaRelease.Java13.getJdkHotspotAssets().enableAllAssets();
                final BaseAssets java14jdkHotspotAssets = JavaRelease.Java14.getJdkHotspotAssets().enableAllAssets();
                final BaseAssets java15jdkHotspotAssets = JavaRelease.Java15.getJdkHotspotAssets().enableAllAssets();

                java8OS.forEach(ex -> java8jdkHotspotAssets.disableOS(AssetOS.parseFromName(ex)));
                java9OS.forEach(ex -> java9jdkHotspotAssets.disableOS(AssetOS.parseFromName(ex)));
                java10OS.forEach(ex -> java10jdkHotspotAssets.disableOS(AssetOS.parseFromName(ex)));
                java11OS.forEach(ex -> java11jdkHotspotAssets.disableOS(AssetOS.parseFromName(ex)));
                java12OS.forEach(ex -> java12jdkHotspotAssets.disableOS(AssetOS.parseFromName(ex)));
                java13OS.forEach(ex -> java13jdkHotspotAssets.disableOS(AssetOS.parseFromName(ex)));
                java14OS.forEach(ex -> java14jdkHotspotAssets.disableOS(AssetOS.parseFromName(ex)));
                java15OS.forEach(ex -> java15jdkHotspotAssets.disableOS(AssetOS.parseFromName(ex)));

                if(java8InstallersDisabled) java8jdkHotspotAssets.disableAllInstallers();
                if(java9InstallersDisabled) java9jdkHotspotAssets.disableAllInstallers();
                if(java10InstallersDisabled) java10jdkHotspotAssets.disableAllInstallers();
                if(java11InstallersDisabled) java11jdkHotspotAssets.disableAllInstallers();
                if(java12InstallersDisabled) java12jdkHotspotAssets.disableAllInstallers();
                if(java13InstallersDisabled) java13jdkHotspotAssets.disableAllInstallers();
                if(java14InstallersDisabled) java14jdkHotspotAssets.disableAllInstallers();
                if(java15InstallersDisabled) java15jdkHotspotAssets.disableAllInstallers();

                java8Assets.forEach(ex -> java8jdkHotspotAssets.disableAsset(AssetName.valueOf(ex)));
                java9Assets.forEach(ex -> java9jdkHotspotAssets.disableAsset(AssetName.valueOf(ex)));
                java10Assets.forEach(ex -> java10jdkHotspotAssets.disableAsset(AssetName.valueOf(ex)));
                java11Assets.forEach(ex -> java11jdkHotspotAssets.disableAsset(AssetName.valueOf(ex)));
                java12Assets.forEach(ex -> java12jdkHotspotAssets.disableAsset(AssetName.valueOf(ex)));
                java13Assets.forEach(ex -> java13jdkHotspotAssets.disableAsset(AssetName.valueOf(ex)));
                java14Assets.forEach(ex -> java14jdkHotspotAssets.disableAsset(AssetName.valueOf(ex)));
                java15Assets.forEach(ex -> java15jdkHotspotAssets.disableAsset(AssetName.valueOf(ex)));
            }
        }
    }
}
