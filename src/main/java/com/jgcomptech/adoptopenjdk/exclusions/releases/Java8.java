package com.jgcomptech.adoptopenjdk.exclusions.releases;

import com.jgcomptech.adoptopenjdk.enums.AssetName;
import com.jgcomptech.adoptopenjdk.enums.AssetOS;
import com.jgcomptech.adoptopenjdk.exclusions.ExclusionsBaseRelease;

import java.util.List;

public class Java8 extends ExclusionsBaseRelease {
    public Java8() {
        List<AssetOS> java8JDKHotspotDisabledOS = JDKHotspot.getDisabledOS();
        List<AssetName> java8JDKHotspotDisabledAssets = JDKHotspot.getDisabledAssets();
        JDKHotspot.setInstallersDisabled(false);
        JDKHotspot.setXLDisabled(true);
        List<AssetOS> java8JREHotspotDisabledOS = JREHotspot.getDisabledOS();
        java8JREHotspotDisabledOS.addAll(java8JDKHotspotDisabledOS);
        List<AssetName> java8JREHotspotDisabledAssets = JREHotspot.getDisabledAssets();
        java8JREHotspotDisabledAssets.addAll(java8JDKHotspotDisabledAssets);
        JREHotspot.setInstallersDisabled(false);
        JREHotspot.setXLDisabled(true);
        List<AssetOS> java8JDKOpenJ9DisabledOS = JDKOpenJ9.getDisabledOS();
        java8JDKOpenJ9DisabledOS.add(AssetOS.arm_linux);
        java8JDKOpenJ9DisabledOS.add(AssetOS.aarch64_linux);
        java8JDKOpenJ9DisabledOS.add(AssetOS.sparcv9_solaris);
        java8JDKOpenJ9DisabledOS.add(AssetOS.x64_solaris);
        List<AssetName> java8JDKOpenJ9DisabledAssets = JDKOpenJ9.getDisabledAssets();
        JDKOpenJ9.setInstallersDisabled(false);
        JDKOpenJ9.setXLDisabled(false);
        List<AssetOS> java8JREOpenJ9DisabledOS = JREOpenJ9.getDisabledOS();
        java8JREOpenJ9DisabledOS.addAll(java8JDKOpenJ9DisabledOS);
        List<AssetName> java8JREOpenJ9DisabledAssets = JREOpenJ9.getDisabledAssets();
        java8JREOpenJ9DisabledAssets.addAll(java8JDKOpenJ9DisabledAssets);
        JREOpenJ9.setInstallersDisabled(false);
        JREOpenJ9.setXLDisabled(false);
    }
}
