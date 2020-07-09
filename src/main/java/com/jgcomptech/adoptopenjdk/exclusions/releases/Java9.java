package com.jgcomptech.adoptopenjdk.exclusions.releases;

import com.jgcomptech.adoptopenjdk.enums.AssetName;
import com.jgcomptech.adoptopenjdk.enums.AssetOS;
import com.jgcomptech.adoptopenjdk.exclusions.ExclusionsBaseRelease;

import java.util.Arrays;
import java.util.List;

public class Java9 extends ExclusionsBaseRelease {
    public Java9() {
        List<AssetOS> java9JDKHotspotDisabledOS = JDKHotspot.getDisabledOS();
        java9JDKHotspotDisabledOS.add(AssetOS.arm_linux);
        java9JDKHotspotDisabledOS.add(AssetOS.sparcv9_solaris);
        java9JDKHotspotDisabledOS.add(AssetOS.x64_solaris);
        java9JDKHotspotDisabledOS.add(AssetOS.x86_32_windows);
        List<AssetName> java9JDKHotspotDisabledAssets = JDKHotspot.getDisabledAssets();
        java9JDKHotspotDisabledAssets.add(AssetName.aarch64_linux_tar_gz_json);
        java9JDKHotspotDisabledAssets.add(AssetName.ppc64le_linux_tar_gz_json);
        java9JDKHotspotDisabledAssets.add(AssetName.ppc64_aix_tar_gz_json);
        java9JDKHotspotDisabledAssets.add(AssetName.s390x_linux_tar_gz_json);
        java9JDKHotspotDisabledAssets.add(AssetName.x64_linux_tar_gz_json);
        java9JDKHotspotDisabledAssets.add(AssetName.x64_mac_tar_gz_json);
        java9JDKHotspotDisabledAssets.add(AssetName.x64_windows_zip_json);
        JDKHotspot.setInstallersDisabled(true);
        JDKHotspot.setXLDisabled(true);
        List<AssetOS> java9JREHotspotDisabledOS = JREHotspot.getDisabledOS();
        java9JREHotspotDisabledOS.addAll(java9JDKHotspotDisabledOS);
        java9JREHotspotDisabledOS.add(AssetOS.ppc64_aix);
        List<AssetName> java9JREHotspotDisabledAssets = JREHotspot.getDisabledAssets();
        java9JREHotspotDisabledAssets.addAll(java9JDKHotspotDisabledAssets);
        JREHotspot.setInstallersDisabled(true);
        JREHotspot.setXLDisabled(true);
        List<AssetOS> java9JDKOpenJ9DisabledOS = JDKOpenJ9.getDisabledOS();
        java9JDKOpenJ9DisabledOS.add(AssetOS.aarch64_linux);
        java9JDKOpenJ9DisabledOS.add(AssetOS.arm_linux);
        java9JDKOpenJ9DisabledOS.add(AssetOS.sparcv9_solaris);
        java9JDKOpenJ9DisabledOS.add(AssetOS.x64_mac);
        java9JDKOpenJ9DisabledOS.add(AssetOS.x64_solaris);
        java9JDKOpenJ9DisabledOS.add(AssetOS.x86_32_windows);
        List<AssetName> java9JDKOpenJ9DisabledAssets = JDKOpenJ9.getDisabledAssets();
        java9JDKOpenJ9DisabledAssets.add(AssetName.ppc64le_linux_tar_gz_json);
        java9JDKOpenJ9DisabledAssets.add(AssetName.ppc64_aix_tar_gz_json);
        java9JDKOpenJ9DisabledAssets.add(AssetName.s390x_linux_tar_gz_json);
        java9JDKOpenJ9DisabledAssets.add(AssetName.x64_linux_tar_gz_json);
        java9JDKOpenJ9DisabledAssets.add(AssetName.x64_windows_zip_json);
        JDKOpenJ9.setInstallersDisabled(true);
        JDKOpenJ9.setXLDisabled(true);
        List<AssetOS> java9JREOpenJ9DisabledOS = JREOpenJ9.getDisabledOS();
        java9JREOpenJ9DisabledOS.addAll(Arrays.asList(AssetOS.values()));
        List<AssetName> java9JREOpenJ9DisabledAssets = JREOpenJ9.getDisabledAssets();
        JREOpenJ9.setInstallersDisabled(false);
        JREOpenJ9.setXLDisabled(true);
    }
}
