package com.jgcomptech.adoptopenjdk.exclusions.releases;

import com.jgcomptech.adoptopenjdk.enums.AssetName;
import com.jgcomptech.adoptopenjdk.enums.AssetOS;
import com.jgcomptech.adoptopenjdk.exclusions.ExclusionsBaseRelease;

import java.util.Arrays;
import java.util.List;

public class Java10 extends ExclusionsBaseRelease {
    public Java10() {
        List<AssetOS> java10JDKHotspotDisabledOS = JDKHotspot.getDisabledOS();
        java10JDKHotspotDisabledOS.add(AssetOS.sparcv9_solaris);
        java10JDKHotspotDisabledOS.add(AssetOS.x64_solaris);
        java10JDKHotspotDisabledOS.add(AssetOS.x86_32_windows);
        List<AssetName> java10JDKHotspotDisabledAssets = JDKHotspot.getDisabledAssets();
        java10JDKHotspotDisabledAssets.add(AssetName.aarch64_linux_tar_gz_json);
        java10JDKHotspotDisabledAssets.add(AssetName.arm_linux_tar_gz_json);
        java10JDKHotspotDisabledAssets.add(AssetName.ppc64le_linux_tar_gz_json);
        java10JDKHotspotDisabledAssets.add(AssetName.ppc64_aix_tar_gz_json);
        java10JDKHotspotDisabledAssets.add(AssetName.s390x_linux_tar_gz_json);
        java10JDKHotspotDisabledAssets.add(AssetName.x64_linux_tar_gz_json);
        java10JDKHotspotDisabledAssets.add(AssetName.x64_mac_tar_gz_json);
        java10JDKHotspotDisabledAssets.add(AssetName.x64_windows_zip_json);
        JDKHotspot.setInstallersDisabled(true);
        JDKHotspot.setXLDisabled(true);
        List<AssetOS> java10JREHotspotDisabledOS = JREHotspot.getDisabledOS();
        java10JREHotspotDisabledOS.addAll(java10JDKHotspotDisabledOS);
        List<AssetName> java10JREHotspotDisabledAssets = JREHotspot.getDisabledAssets();
        java10JREHotspotDisabledAssets.addAll(java10JDKHotspotDisabledAssets);
        JREHotspot.setInstallersDisabled(true);
        JREHotspot.setXLDisabled(true);
        List<AssetOS> java10JDKOpenJ9DisabledOS = JDKOpenJ9.getDisabledOS();
        java10JDKOpenJ9DisabledOS.add(AssetOS.aarch64_linux);
        java10JDKOpenJ9DisabledOS.add(AssetOS.arm_linux);
        java10JDKOpenJ9DisabledOS.add(AssetOS.sparcv9_solaris);
        java10JDKOpenJ9DisabledOS.add(AssetOS.x64_mac);
        java10JDKOpenJ9DisabledOS.add(AssetOS.x64_solaris);
        java10JDKOpenJ9DisabledOS.add(AssetOS.x86_32_windows);
        List<AssetName> java10JDKOpenJ9DisabledAssets = JDKOpenJ9.getDisabledAssets();
        java10JDKOpenJ9DisabledAssets.add(AssetName.ppc64le_linux_tar_gz_json);
        java10JDKOpenJ9DisabledAssets.add(AssetName.ppc64_aix_tar_gz_json);
        java10JDKOpenJ9DisabledAssets.add(AssetName.s390x_linux_tar_gz_json);
        java10JDKOpenJ9DisabledAssets.add(AssetName.x64_linux_tar_gz_json);
        java10JDKOpenJ9DisabledAssets.add(AssetName.x64_windows_zip_json);
        JDKOpenJ9.setInstallersDisabled(true);
        JDKOpenJ9.setXLDisabled(true);
        List<AssetOS> java10JREOpenJ9DisabledOS = JREOpenJ9.getDisabledOS();
        java10JREOpenJ9DisabledOS.addAll(Arrays.asList(AssetOS.values()));
        List<AssetName> java10JREOpenJ9DisabledAssets = JREOpenJ9.getDisabledAssets();
        JREOpenJ9.setInstallersDisabled(true);
        JREOpenJ9.setXLDisabled(true);
    }
}
