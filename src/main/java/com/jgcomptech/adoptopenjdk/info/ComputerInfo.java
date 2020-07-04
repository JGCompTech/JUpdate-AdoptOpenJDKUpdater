package com.jgcomptech.adoptopenjdk.info;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.IOException;

/**
 * Returns Information about the current OS and Hardware on the current system.
 */
public final class ComputerInfo {
    //public static OSInfo.OSObject OS;
    public static HWInfo.HWObject HW;

    private ComputerInfo() { }

    public static void initialize() throws IOException {
        HW = ReInitializeHW();
        //OS = ReInitializeOS();
    }

//    /* Reprocesses the OS information and returns a new OSObject. */
//    private static OSInfo.OSObject ReInitializeOS() throws IOException, InterruptedException {
//        final var vobj = new OSInfo.VersionObject();
//        vobj.Build = WindowsOSEx.Version.getBuild();
//        vobj.Main = WindowsOSEx.Version.getMain();
//        vobj.Major = WindowsOSEx.Version.getMajor();
//        vobj.Minor = WindowsOSEx.Version.getMinor();
//        vobj.Number = WindowsOSEx.Version.getNumber();
//        vobj.Revision = WindowsOSEx.Version.getRevision();
//
//        final var iiobj = new OSInfo.InstallInfoObject();
//        iiobj.ActivationStatus = WindowsOSEx.Activation.getStatusString();
//        iiobj.Architecture = OSInfo.getBitString();
//        iiobj.NameExpanded = OSInfo.getNameExpanded();
//        iiobj.Name = OSInfo.getName();
//        iiobj.ServicePack = WindowsOSEx.ServicePack.getString();
//        iiobj.ServicePackNumber = WindowsOSEx.ServicePack.getNumber();
//        iiobj.Version = vobj;
//
//        final var osobj = new OSInfo.OSObject();
//        osobj.ComputerName = WindowsOSEx.SystemInfo.getComputerNameActive();
//        osobj.ComputerNamePending = WindowsOSEx.SystemInfo.getComputerNamePending();
//        osobj.DomainName = WindowsOSEx.SystemInfo.getCurrentDomainName();
//        osobj.LoggedInUserName = WindowsOSEx.Users.getLoggedInUserName();
//        osobj.RegisteredOrganization = WindowsOSEx.SystemInfo.getRegisteredOrganization();
//        osobj.RegisteredOwner = WindowsOSEx.SystemInfo.getRegisteredOwner();
//        osobj.InstallInfo = iiobj;
//
//        return osobj;
//    }

    /* Reprocesses the Hardware information and returns a new HWObject. */
    private static HWInfo.HWObject ReInitializeHW() throws IOException {
        final HWInfo.BIOSObject biosobj = new HWInfo.BIOSObject();
        biosobj.Name = HWInfo.BIOS.getVendor() + ' ' + HWInfo.BIOS.getVersion();
        biosobj.ReleaseDate = HWInfo.BIOS.getReleaseDate();
        biosobj.Vendor = HWInfo.BIOS.getVendor();
        biosobj.Version = HWInfo.BIOS.getVersion();

        final HWInfo.NetworkObject nwobj = new HWInfo.NetworkObject();
        nwobj.ConnectionStatus = HWInfo.Network.isConnectedToInternet();
        nwobj.InternalIPAddress = HWInfo.Network.getInternalIPAddress();
        nwobj.ExternalIPAddress = HWInfo.Network.getExternalIPAddress();

        final HWInfo.ProcessorObject pobj = new HWInfo.ProcessorObject();
        pobj.Name = HWInfo.Processor.getName();
        pobj.Cores = HWInfo.Processor.getCores();

        final HWInfo.RAMObject robj = new HWInfo.RAMObject();
        robj.TotalInstalled = HWInfo.RAM.getTotalRam();

        final HWInfo.StorageObject sobj = new HWInfo.StorageObject();
        sobj.SystemDrive = new HWInfo.DriveObject();
        sobj.SystemDrive.DriveType = "Fixed";
        sobj.SystemDrive.TotalFree = HWInfo.Storage.getSystemDriveFreeSpace();
        sobj.SystemDrive.TotalSize = HWInfo.Storage.getSystemDriveSize();

        final HWInfo.HWObject hwobj = new HWInfo.HWObject();
        hwobj.SystemOEM = HWInfo.OEM.getName();
        hwobj.ProductName = HWInfo.OEM.getProductName();
        hwobj.BIOS = biosobj;
        hwobj.Network = nwobj;
        hwobj.Processor = pobj;
        hwobj.RAM = robj;
        hwobj.Storage = sobj;

        return hwobj;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                //.append("OS", OS)
                .append("HW", HW)
                .toString();
    }
}
