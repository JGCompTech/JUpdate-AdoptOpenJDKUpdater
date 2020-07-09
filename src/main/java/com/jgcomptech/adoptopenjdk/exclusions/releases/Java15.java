package com.jgcomptech.adoptopenjdk.exclusions.releases;

import com.jgcomptech.adoptopenjdk.enums.AssetName;
import com.jgcomptech.adoptopenjdk.enums.AssetOS;
import com.jgcomptech.adoptopenjdk.exclusions.ExclusionsBaseRelease;

import java.util.List;

public class Java15 extends ExclusionsBaseRelease {
    public Java15() {
        List<AssetOS> java15JDKHotspotDisabledOS = JDKHotspot.getDisabledOS();
        java15JDKHotspotDisabledOS.add(AssetOS.sparcv9_solaris);
        java15JDKHotspotDisabledOS.add(AssetOS.x64_solaris);
        java15JDKHotspotDisabledOS.add(AssetOS.x86_32_windows);
        List<AssetName> java15JDKHotspotDisabledAssets = JDKHotspot.getDisabledAssets();
        JDKHotspot.setInstallersDisabled(false);
        JDKHotspot.setXLDisabled(true);
        List<AssetOS> java15JREHotspotDisabledOS = JREHotspot.getDisabledOS();
        java15JREHotspotDisabledOS.addAll(java15JDKHotspotDisabledOS);
        List<AssetName> java15JREHotspotDisabledAssets = JREHotspot.getDisabledAssets();
        java15JREHotspotDisabledAssets.addAll(java15JDKHotspotDisabledAssets);
        JREHotspot.setInstallersDisabled(false);
        JREHotspot.setXLDisabled(true);
        List<AssetOS> java15JDKOpenJ9DisabledOS = JDKOpenJ9.getDisabledOS();
        java15JDKOpenJ9DisabledOS.add(AssetOS.arm_linux);
        java15JDKOpenJ9DisabledOS.add(AssetOS.sparcv9_solaris);
        java15JDKOpenJ9DisabledOS.add(AssetOS.x64_solaris);
        java15JDKOpenJ9DisabledOS.add(AssetOS.x86_32_windows);
        List<AssetName> java15JDKOpenJ9DisabledAssets = JDKOpenJ9.getDisabledAssets();
        JDKOpenJ9.setInstallersDisabled(false);
        JDKOpenJ9.setXLDisabled(true);
        List<AssetOS> java15JREOpenJ9DisabledOS = JREOpenJ9.getDisabledOS();
        java15JREOpenJ9DisabledOS.addAll(java15JDKOpenJ9DisabledOS);
        List<AssetName> java15JREOpenJ9DisabledAssets = JREOpenJ9.getDisabledAssets();
        java15JREOpenJ9DisabledAssets.addAll(java15JDKOpenJ9DisabledAssets);
        JREOpenJ9.setInstallersDisabled(false);
        JREOpenJ9.setXLDisabled(true);
    }
}
