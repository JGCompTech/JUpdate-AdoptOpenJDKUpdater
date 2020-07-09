package com.jgcomptech.adoptopenjdk.exclusions;

import com.jgcomptech.adoptopenjdk.enums.AssetName;
import com.jgcomptech.adoptopenjdk.enums.AssetOS;

import java.util.ArrayList;
import java.util.List;

public class ExclusionsSubRelease {
    protected List<AssetOS> disabledOS = new ArrayList<>();
    protected List<AssetName> disabledAssets = new ArrayList<>();
    protected boolean installersDisabled = false;
    protected boolean XLDisabled = false;

    public List<AssetOS> getDisabledOS() {
        return disabledOS;
    }

    public void setDisabledOS(List<AssetOS> disabledOS) {
        this.disabledOS = disabledOS;
    }

    public List<AssetName> getDisabledAssets() {
        return disabledAssets;
    }

    public void setDisabledAssets(List<AssetName> disabledAssets) {
        this.disabledAssets = disabledAssets;
    }

    public boolean isInstallersDisabled() {
        return installersDisabled;
    }

    public void setInstallersDisabled(boolean installersDisabled) {
        this.installersDisabled = installersDisabled;
    }

    public boolean isXLDisabled() {
        return XLDisabled;
    }

    public void setXLDisabled(boolean XLDisabled) {
        this.XLDisabled = XLDisabled;
    }
}
