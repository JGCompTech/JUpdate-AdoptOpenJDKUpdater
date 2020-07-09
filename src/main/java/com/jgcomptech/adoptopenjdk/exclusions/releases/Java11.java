package com.jgcomptech.adoptopenjdk.exclusions.releases;

import com.jgcomptech.adoptopenjdk.enums.AssetName;
import com.jgcomptech.adoptopenjdk.enums.AssetOS;
import com.jgcomptech.adoptopenjdk.exclusions.ExclusionsBaseRelease;

import java.util.List;

public class Java11 extends ExclusionsBaseRelease {
    public Java11() {
        List<AssetOS> java11JDKHotspotDisabledOS = JDKHotspot.getDisabledOS();
        java11JDKHotspotDisabledOS.add(AssetOS.sparcv9_solaris);
        java11JDKHotspotDisabledOS.add(AssetOS.x64_solaris);
        List<AssetName> java11JDKHotspotDisabledAssets = JDKHotspot.getDisabledAssets();
        JDKHotspot.setInstallersDisabled(false);
        JDKHotspot.setXLDisabled(true);
        List<AssetOS> java11JREHotspotDisabledOS = JREHotspot.getDisabledOS();
        java11JREHotspotDisabledOS.addAll(java11JDKHotspotDisabledOS);
        List<AssetName> java11JREHotspotDisabledAssets = JREHotspot.getDisabledAssets();
        java11JREHotspotDisabledAssets.addAll(java11JDKHotspotDisabledAssets);
        JREHotspot.setInstallersDisabled(false);
        JREHotspot.setXLDisabled(true);
        List<AssetOS> java11JDKOpenJ9DisabledOS = JDKOpenJ9.getDisabledOS();
        java11JDKOpenJ9DisabledOS.add(AssetOS.arm_linux);
        java11JDKOpenJ9DisabledOS.add(AssetOS.sparcv9_solaris);
        java11JDKOpenJ9DisabledOS.add(AssetOS.x64_solaris);
        java11JDKOpenJ9DisabledOS.add(AssetOS.x86_32_windows);
        List<AssetName> java11JDKOpenJ9DisabledAssets = JDKOpenJ9.getDisabledAssets();
        JDKOpenJ9.setInstallersDisabled(false);
        JDKOpenJ9.setXLDisabled(false);
        List<AssetOS> java11JREOpenJ9DisabledOS = JREOpenJ9.getDisabledOS();
        java11JREOpenJ9DisabledOS.addAll(java11JDKOpenJ9DisabledOS);
        List<AssetName> java11JREOpenJ9DisabledAssets = JREOpenJ9.getDisabledAssets();
        java11JREOpenJ9DisabledAssets.addAll(java11JDKOpenJ9DisabledAssets);
        JREOpenJ9.setInstallersDisabled(false);
        JREOpenJ9.setXLDisabled(false);
    }
}
