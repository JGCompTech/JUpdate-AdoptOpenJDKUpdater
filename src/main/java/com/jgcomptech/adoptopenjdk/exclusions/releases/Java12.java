package com.jgcomptech.adoptopenjdk.exclusions.releases;

import com.jgcomptech.adoptopenjdk.enums.AssetName;
import com.jgcomptech.adoptopenjdk.enums.AssetOS;
import com.jgcomptech.adoptopenjdk.exclusions.ExclusionsBaseRelease;

import java.util.List;

public class Java12 extends ExclusionsBaseRelease {
    public Java12() {
        List<AssetOS> java12JDKHotspotDisabledOS = JDKHotspot.getDisabledOS();
        java12JDKHotspotDisabledOS.add(AssetOS.sparcv9_solaris);
        java12JDKHotspotDisabledOS.add(AssetOS.x64_solaris);
        List<AssetName> java12JDKHotspotDisabledAssets = JDKHotspot.getDisabledAssets();
        java12JDKHotspotDisabledAssets.add(AssetName.ppc64_aix_tar_gz_json);
        JDKHotspot.setInstallersDisabled(false);
        JDKHotspot.setXLDisabled(true);
        List<AssetOS> java12JREHotspotDisabledOS = JREHotspot.getDisabledOS();
        java12JREHotspotDisabledOS.addAll(java12JDKHotspotDisabledOS);
        java12JREHotspotDisabledOS.add(AssetOS.arm_linux);
        List<AssetName> java12JREHotspotDisabledAssets = JREHotspot.getDisabledAssets();
        java12JREHotspotDisabledAssets.addAll(java12JDKHotspotDisabledAssets);
        JREHotspot.setInstallersDisabled(false);
        JREHotspot.setXLDisabled(true);
        List<AssetOS> java12JDKOpenJ9DisabledOS = JDKOpenJ9.getDisabledOS();
        java12JDKOpenJ9DisabledOS.add(AssetOS.aarch64_linux);
        java12JDKOpenJ9DisabledOS.add(AssetOS.arm_linux);
        java12JDKOpenJ9DisabledOS.add(AssetOS.sparcv9_solaris);
        java12JDKOpenJ9DisabledOS.add(AssetOS.x64_solaris);
        java12JDKOpenJ9DisabledOS.add(AssetOS.x86_32_windows);
        java12JDKOpenJ9DisabledOS.add(AssetOS.ppc64le_linux_linuxXL);
        java12JDKOpenJ9DisabledOS.add(AssetOS.s390x_linux_linuxXL);
        List<AssetName> java12JDKOpenJ9DisabledAssets = JDKOpenJ9.getDisabledAssets();
        java12JDKOpenJ9DisabledAssets.add(AssetName.ppc64_aix_tar_gz_json);
        JDKOpenJ9.setInstallersDisabled(false);
        JDKOpenJ9.setXLDisabled(false);
        List<AssetOS> java12JREOpenJ9DisabledOS = JREOpenJ9.getDisabledOS();
        java12JREOpenJ9DisabledOS.addAll(java12JDKOpenJ9DisabledOS);
        List<AssetName> java12JREOpenJ9DisabledAssets = JREOpenJ9.getDisabledAssets();
        java12JREOpenJ9DisabledAssets.addAll(java12JDKOpenJ9DisabledAssets);
        JREOpenJ9.setInstallersDisabled(false);
        JREOpenJ9.setXLDisabled(false);
    }
}
