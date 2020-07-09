package com.jgcomptech.adoptopenjdk.exclusions.releases;

import com.jgcomptech.adoptopenjdk.enums.AssetName;
import com.jgcomptech.adoptopenjdk.enums.AssetOS;
import com.jgcomptech.adoptopenjdk.exclusions.ExclusionsBaseRelease;

import java.util.List;

public class Java13 extends ExclusionsBaseRelease {
    public Java13() {
        List<AssetOS> java13JDKHotspotDisabledOS = JDKHotspot.getDisabledOS();
        java13JDKHotspotDisabledOS.add(AssetOS.sparcv9_solaris);
        java13JDKHotspotDisabledOS.add(AssetOS.x64_solaris);
        List<AssetName> java13JDKHotspotDisabledAssets = JDKHotspot.getDisabledAssets();
        JDKHotspot.setInstallersDisabled(false);
        JDKHotspot.setXLDisabled(true);
        List<AssetOS> java13JREHotspotDisabledOS = JREHotspot.getDisabledOS();
        java13JREHotspotDisabledOS.addAll(java13JDKHotspotDisabledOS);
        java13JREHotspotDisabledOS.add(AssetOS.arm_linux);
        List<AssetName> java13JREHotspotDisabledAssets = JREHotspot.getDisabledAssets();
        java13JREHotspotDisabledAssets.addAll(java13JDKHotspotDisabledAssets);
        JREHotspot.setInstallersDisabled(false);
        JREHotspot.setXLDisabled(true);
        List<AssetOS> java13JDKOpenJ9DisabledOS = JDKOpenJ9.getDisabledOS();
        java13JDKOpenJ9DisabledOS.add(AssetOS.aarch64_linux);
        java13JDKOpenJ9DisabledOS.add(AssetOS.arm_linux);
        java13JDKOpenJ9DisabledOS.add(AssetOS.sparcv9_solaris);
        java13JDKOpenJ9DisabledOS.add(AssetOS.x64_solaris);
        java13JDKOpenJ9DisabledOS.add(AssetOS.x86_32_windows);
        List<AssetName> java13JDKOpenJ9DisabledAssets = JDKOpenJ9.getDisabledAssets();
        JDKOpenJ9.setInstallersDisabled(false);
        JDKOpenJ9.setXLDisabled(false);
        List<AssetOS> java13JREOpenJ9DisabledOS = JREOpenJ9.getDisabledOS();
        java13JREOpenJ9DisabledOS.addAll(java13JDKOpenJ9DisabledOS);
        List<AssetName> java13JREOpenJ9DisabledAssets = JREOpenJ9.getDisabledAssets();
        java13JREOpenJ9DisabledAssets.addAll(java13JDKOpenJ9DisabledAssets);
        JREOpenJ9.setInstallersDisabled(false);
        JREOpenJ9.setXLDisabled(false);
    }
}
