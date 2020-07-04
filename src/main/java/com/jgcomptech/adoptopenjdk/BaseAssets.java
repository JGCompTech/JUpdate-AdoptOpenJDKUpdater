package com.jgcomptech.adoptopenjdk;

import java.util.*;

import static com.jgcomptech.adoptopenjdk.AssetName.*;

public class BaseAssets {
    private final EnumMap<AssetName, SimpleAsset> assets = new EnumMap<>(AssetName.class);
    //private final LinkedHashSet<AssetName> enabledAssets = new LinkedHashSet<>();
    private final Collection<AssetName> enabledAssets = new ArrayList<>();
    private final JavaRelease parentRelease;

    public BaseAssets(final JavaRelease parentRelease) {
        this.parentRelease = parentRelease;
    }

    public Optional<SimpleAsset> get(final AssetName name) {
        return Optional.ofNullable(assets.get(name));
    }

    public Map<AssetName, SimpleAsset> getAll() {
        return Collections.unmodifiableMap(assets);
    }

    public BaseAssets disableAsset(final AssetName name) {
        enabledAssets.remove(name);
        return this;
    }

    public BaseAssets disableAllAssets() {
        enabledAssets.clear();
        return this;
    }

    public BaseAssets disableInstallers(final AssetOS osName) {
        switch(osName) {
//            case x64_mac_macosXL:
//                enabledAssets.remove(x64_mac_macosXL_pkg);
//                enabledAssets.remove(x64_mac_macosXL_pkg_json);
//                enabledAssets.remove(x64_mac_macosXL_pkg_sha256_txt);
//                break;
            case x64_mac:
                enabledAssets.remove(x64_mac_pkg);
                enabledAssets.remove(x64_mac_pkg_json);
                enabledAssets.remove(x64_mac_pkg_sha256_txt);
                break;
//            case x64_windows_windowsXL:
//                enabledAssets.remove(x64_windows_windowsXL_msi);
//                enabledAssets.remove(x64_windows_windowsXL_msi_json);
//                enabledAssets.remove(x64_windows_windowsXL_msi_sha256_txt);
//                break;
            case x64_windows:
                enabledAssets.remove(x64_windows_msi);
                enabledAssets.remove(x64_windows_msi_json);
                enabledAssets.remove(x64_windows_msi_sha256_txt);
                break;
            case x86_32_windows:
                enabledAssets.remove(x86_32_windows_msi);
                enabledAssets.remove(x86_32_windows_msi_json);
                enabledAssets.remove(x86_32_windows_msi_sha256_txt);
                break;
        }
        return this;
    }

    public BaseAssets disableAllInstallers() {
//        enabledAssets.remove(x64_mac_macosXL_pkg);
//        enabledAssets.remove(x64_mac_macosXL_pkg_json);
//        enabledAssets.remove(x64_mac_macosXL_pkg_sha256_txt);
        enabledAssets.remove(x64_mac_pkg);
        enabledAssets.remove(x64_mac_pkg_json);
        enabledAssets.remove(x64_mac_pkg_sha256_txt);
//        enabledAssets.remove(x64_windows_windowsXL_msi);
//        enabledAssets.remove(x64_windows_windowsXL_msi_json);
//        enabledAssets.remove(x64_windows_windowsXL_msi_sha256_txt);
        enabledAssets.remove(x64_windows_msi);
        enabledAssets.remove(x64_windows_msi_json);
        enabledAssets.remove(x64_windows_msi_sha256_txt);
        enabledAssets.remove(x86_32_windows_msi);
        enabledAssets.remove(x86_32_windows_msi_json);
        enabledAssets.remove(x86_32_windows_msi_sha256_txt);
        return this;
    }

    public BaseAssets disableOS(final AssetOS osName) {
        switch(osName) {
            case NONE:
                break;
            case aarch64_linux:
                enabledAssets.remove(aarch64_linux_tar_gz);
                enabledAssets.remove(aarch64_linux_tar_gz_json);
                enabledAssets.remove(aarch64_linux_tar_gz_sha256_txt);
                break;
            case arm_linux:
                enabledAssets.remove(arm_linux_tar_gz);
                enabledAssets.remove(arm_linux_tar_gz_json);
                enabledAssets.remove(arm_linux_tar_gz_sha256_txt);
                break;
            case ppc64le_linux:
                enabledAssets.remove(ppc64le_linux_tar_gz);
                enabledAssets.remove(ppc64le_linux_tar_gz_json);
                enabledAssets.remove(ppc64le_linux_tar_gz_sha256_txt);
                break;
            case ppc64_aix:
                enabledAssets.remove(ppc64_aix_tar_gz);
                enabledAssets.remove(ppc64_aix_tar_gz_json);
                enabledAssets.remove(ppc64_aix_tar_gz_sha256_txt);
                break;
            case s390x_linux:
                enabledAssets.remove(s390x_linux_tar_gz);
                enabledAssets.remove(s390x_linux_tar_gz_json);
                enabledAssets.remove(s390x_linux_tar_gz_sha256_txt);
                break;
            case sparcv9_solaris:
                enabledAssets.remove(sparcv9_solaris_tar_gz);
                enabledAssets.remove(sparcv9_solaris_tar_gz_json);
                enabledAssets.remove(sparcv9_solaris_tar_gz_sha256_txt);
                break;
            case x64_linux:
                enabledAssets.remove(x64_linux_tar_gz);
                enabledAssets.remove(x64_linux_tar_gz_json);
                enabledAssets.remove(x64_linux_tar_gz_sha256_txt);
                break;
//            case x64_mac_macosXL:
//                enabledAssets.remove(x64_mac_macosXL_pkg);
//                enabledAssets.remove(x64_mac_macosXL_pkg_json);
//                enabledAssets.remove(x64_mac_macosXL_pkg_sha256_txt);
//                enabledAssets.remove(x64_mac_macosXL_tar_gz);
//                enabledAssets.remove(x64_mac_macosXL_tar_gz_json);
//                enabledAssets.remove(x64_mac_macosXL_tar_gz_sha256_txt);
//                break;
            case x64_mac:
                enabledAssets.remove(x64_mac_pkg);
                enabledAssets.remove(x64_mac_pkg_json);
                enabledAssets.remove(x64_mac_pkg_sha256_txt);
                enabledAssets.remove(x64_mac_tar_gz);
                enabledAssets.remove(x64_mac_tar_gz_json);
                enabledAssets.remove(x64_mac_tar_gz_sha256_txt);
                break;
            case x64_solaris:
                enabledAssets.remove(x64_solaris_tar_gz);
                enabledAssets.remove(x64_solaris_tar_gz_json);
                enabledAssets.remove(x64_solaris_tar_gz_sha256_txt);
                break;
//            case x64_windows_windowsXL:
//                enabledAssets.remove(x64_windows_windowsXL_msi);
//                enabledAssets.remove(x64_windows_windowsXL_msi_json);
//                enabledAssets.remove(x64_windows_windowsXL_msi_sha256_txt);
//                enabledAssets.remove(x64_windows_windowsXL_zip);
//                enabledAssets.remove(x64_windows_windowsXL_zip_json);
//                enabledAssets.remove(x64_windows_windowsXL_zip_sha256_txt);
//                break;
            case x64_windows:
                enabledAssets.remove(x64_windows_msi);
                enabledAssets.remove(x64_windows_msi_json);
                enabledAssets.remove(x64_windows_msi_sha256_txt);
                enabledAssets.remove(x64_windows_zip);
                enabledAssets.remove(x64_windows_zip_json);
                enabledAssets.remove(x64_windows_zip_sha256_txt);
                break;
            case x86_32_windows:
                enabledAssets.remove(x86_32_windows_msi);
                enabledAssets.remove(x86_32_windows_msi_json);
                enabledAssets.remove(x86_32_windows_msi_sha256_txt);
                enabledAssets.remove(x86_32_windows_zip);
                enabledAssets.remove(x86_32_windows_zip_json);
                enabledAssets.remove(x86_32_windows_zip_sha256_txt);
                break;
        }
        return this;
    }

    public BaseAssets enableAsset(final AssetName name) {
        enabledAssets.add(name);
        return this;
    }

    public BaseAssets enableAllAssets() {
        enabledAssets.addAll(Arrays.asList(AssetName.values()));
        return this;
    }

    public BaseAssets enableInstallers(final AssetOS osName) {
        switch(osName) {
//            case x64_mac_macosXL:
//                enabledAssets.add(x64_mac_macosXL_pkg);
//                enabledAssets.add(x64_mac_macosXL_pkg_json);
//                enabledAssets.add(x64_mac_macosXL_pkg_sha256_txt);
//                break;
            case x64_mac:
                enabledAssets.add(x64_mac_pkg);
                enabledAssets.add(x64_mac_pkg_json);
                enabledAssets.add(x64_mac_pkg_sha256_txt);
                break;
//            case x64_windows_windowsXL:
//                enabledAssets.add(x64_windows_windowsXL_msi);
//                enabledAssets.add(x64_windows_windowsXL_msi_json);
//                enabledAssets.add(x64_windows_windowsXL_msi_sha256_txt);
//                break;
            case x64_windows:
                enabledAssets.add(x64_windows_msi);
                enabledAssets.add(x64_windows_msi_json);
                enabledAssets.add(x64_windows_msi_sha256_txt);
                break;
            case x86_32_windows:
                enabledAssets.add(x86_32_windows_msi);
                enabledAssets.add(x86_32_windows_msi_json);
                enabledAssets.add(x86_32_windows_msi_sha256_txt);
                break;
        }
        return this;
    }

    public BaseAssets enableAllInstallers() {
//        enabledAssets.add(x64_mac_macosXL_pkg);
//        enabledAssets.add(x64_mac_macosXL_pkg_json);
//        enabledAssets.add(x64_mac_macosXL_pkg_sha256_txt);
        enabledAssets.add(x64_mac_pkg);
        enabledAssets.add(x64_mac_pkg_json);
        enabledAssets.add(x64_mac_pkg_sha256_txt);
//        enabledAssets.add(x64_windows_windowsXL_msi);
//        enabledAssets.add(x64_windows_windowsXL_msi_json);
//        enabledAssets.add(x64_windows_windowsXL_msi_sha256_txt);
        enabledAssets.add(x64_windows_msi);
        enabledAssets.add(x64_windows_msi_json);
        enabledAssets.add(x64_windows_msi_sha256_txt);
        enabledAssets.add(x86_32_windows_msi);
        enabledAssets.add(x86_32_windows_msi_json);
        enabledAssets.add(x86_32_windows_msi_sha256_txt);
        return this;
    }

    public BaseAssets enableOS(final AssetOS osName) {
        switch(osName) {
            case NONE:
                break;
            case aarch64_linux:
                enabledAssets.add(aarch64_linux_tar_gz);
                enabledAssets.add(aarch64_linux_tar_gz_json);
                enabledAssets.add(aarch64_linux_tar_gz_sha256_txt);
                break;
            case arm_linux:
                enabledAssets.add(arm_linux_tar_gz);
                enabledAssets.add(arm_linux_tar_gz_json);
                enabledAssets.add(arm_linux_tar_gz_sha256_txt);
                break;
            case ppc64le_linux:
                enabledAssets.add(ppc64le_linux_tar_gz);
                enabledAssets.add(ppc64le_linux_tar_gz_json);
                enabledAssets.add(ppc64le_linux_tar_gz_sha256_txt);
                break;
            case ppc64_aix:
                enabledAssets.add(ppc64_aix_tar_gz);
                enabledAssets.add(ppc64_aix_tar_gz_json);
                enabledAssets.add(ppc64_aix_tar_gz_sha256_txt);
                break;
            case s390x_linux:
                enabledAssets.add(s390x_linux_tar_gz);
                enabledAssets.add(s390x_linux_tar_gz_json);
                enabledAssets.add(s390x_linux_tar_gz_sha256_txt);
                break;
            case sparcv9_solaris:
                enabledAssets.add(sparcv9_solaris_tar_gz);
                enabledAssets.add(sparcv9_solaris_tar_gz_json);
                enabledAssets.add(sparcv9_solaris_tar_gz_sha256_txt);
                break;
            case x64_linux:
                enabledAssets.add(x64_linux_tar_gz);
                enabledAssets.add(x64_linux_tar_gz_json);
                enabledAssets.add(x64_linux_tar_gz_sha256_txt);
                break;
//            case x64_mac_macosXL:
//                enabledAssets.add(x64_mac_macosXL_pkg);
//                enabledAssets.add(x64_mac_macosXL_pkg_json);
//                enabledAssets.add(x64_mac_macosXL_pkg_sha256_txt);
//                enabledAssets.add(x64_mac_macosXL_tar_gz);
//                enabledAssets.add(x64_mac_macosXL_tar_gz_json);
//                enabledAssets.add(x64_mac_macosXL_tar_gz_sha256_txt);
//                break;
            case x64_mac:
                enabledAssets.add(x64_mac_pkg);
                enabledAssets.add(x64_mac_pkg_json);
                enabledAssets.add(x64_mac_pkg_sha256_txt);
                enabledAssets.add(x64_mac_tar_gz);
                enabledAssets.add(x64_mac_tar_gz_json);
                enabledAssets.add(x64_mac_tar_gz_sha256_txt);
                break;
            case x64_solaris:
                enabledAssets.add(x64_solaris_tar_gz);
                enabledAssets.add(x64_solaris_tar_gz_json);
                enabledAssets.add(x64_solaris_tar_gz_sha256_txt);
                break;
//            case x64_windows_windowsXL:
//                enabledAssets.add(x64_windows_windowsXL_msi);
//                enabledAssets.add(x64_windows_windowsXL_msi_json);
//                enabledAssets.add(x64_windows_windowsXL_msi_sha256_txt);
//                enabledAssets.add(x64_windows_windowsXL_zip);
//                enabledAssets.add(x64_windows_windowsXL_zip);
//                enabledAssets.add(x64_windows_windowsXL_zip_sha256_txt);
//                break;
            case x64_windows:
                enabledAssets.add(x64_windows_msi);
                enabledAssets.add(x64_windows_msi_json);
                enabledAssets.add(x64_windows_msi_sha256_txt);
                enabledAssets.add(x64_windows_zip);
                enabledAssets.add(x64_windows_zip_json);
                enabledAssets.add(x64_windows_zip_sha256_txt);
                break;
            case x86_32_windows:
                enabledAssets.add(x86_32_windows_msi);
                enabledAssets.add(x86_32_windows_msi_json);
                enabledAssets.add(x86_32_windows_msi_sha256_txt);
                enabledAssets.add(x86_32_windows_zip);
                enabledAssets.add(x86_32_windows_zip_json);
                enabledAssets.add(x86_32_windows_zip_sha256_txt);
                break;
        }
        return this;
    }

    public Collection<AssetName> getEnabledAssets() {
        return Collections.unmodifiableCollection(enabledAssets);
    }

    public boolean contains(final AssetName name) {
        return assets.containsKey(name);
    }

    public boolean isAllAcquired() {
        return enabledAssets.stream().allMatch(assets::containsKey);
    }

    public boolean setAsset(final SimpleAsset asset) {
        if(!assets.containsKey(asset.getAssetName())) {
            assets.put(asset.getAssetName(), asset);
            return true;
        }
        return false;
    }

    public JavaRelease getParentRelease() {
        return parentRelease;
    }
}