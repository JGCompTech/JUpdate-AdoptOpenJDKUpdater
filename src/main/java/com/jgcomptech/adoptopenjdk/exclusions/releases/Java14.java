package com.jgcomptech.adoptopenjdk.exclusions.releases;

import com.jgcomptech.adoptopenjdk.enums.AssetName;
import com.jgcomptech.adoptopenjdk.enums.AssetOS;
import com.jgcomptech.adoptopenjdk.exclusions.ExclusionsBaseRelease;

import java.util.List;

public class Java14 extends ExclusionsBaseRelease {
    public Java14() {
        List<AssetOS> java14JDKHotspotDisabledOS = JDKHotspot.getDisabledOS();
        java14JDKHotspotDisabledOS.add(AssetOS.sparcv9_solaris);
        java14JDKHotspotDisabledOS.add(AssetOS.x64_solaris);
        List<AssetName> java14JDKHotspotDisabledAssets = JDKHotspot.getDisabledAssets();
        JDKHotspot.setInstallersDisabled(false);
        JDKHotspot.setXLDisabled(true);
        List<AssetOS> java14JREHotspotDisabledOS = JREHotspot.getDisabledOS();
        java14JREHotspotDisabledOS.addAll(java14JDKHotspotDisabledOS);
        List<AssetName> java14JREHotspotDisabledAssets = JREHotspot.getDisabledAssets();
        java14JREHotspotDisabledAssets.addAll(java14JDKHotspotDisabledAssets);
        JREHotspot.setInstallersDisabled(false);
        JREHotspot.setXLDisabled(true);
        List<AssetOS> java14JDKOpenJ9DisabledOS = JDKOpenJ9.getDisabledOS();
        java14JDKOpenJ9DisabledOS.add(AssetOS.aarch64_linux);
        java14JDKOpenJ9DisabledOS.add(AssetOS.arm_linux);
        java14JDKOpenJ9DisabledOS.add(AssetOS.sparcv9_solaris);
        java14JDKOpenJ9DisabledOS.add(AssetOS.x64_solaris);
        java14JDKOpenJ9DisabledOS.add(AssetOS.x86_32_windows);
        List<AssetName> java14JDKOpenJ9DisabledAssets = JDKOpenJ9.getDisabledAssets();
        JDKOpenJ9.setInstallersDisabled(false);
        JDKOpenJ9.setXLDisabled(false);
        List<AssetOS> java14JREOpenJ9DisabledOS = JREOpenJ9.getDisabledOS();
        java14JREOpenJ9DisabledOS.addAll(java14JDKOpenJ9DisabledOS);
        List<AssetName> java14JREOpenJ9DisabledAssets = JREOpenJ9.getDisabledAssets();
        java14JREOpenJ9DisabledAssets.addAll(java14JDKOpenJ9DisabledAssets);
        JREOpenJ9.setInstallersDisabled(false);
        JREOpenJ9.setXLDisabled(false);
    }
}
