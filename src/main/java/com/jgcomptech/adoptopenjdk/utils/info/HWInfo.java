package com.jgcomptech.adoptopenjdk.utils.info;

import com.jgcomptech.adoptopenjdk.utils.info.os.WindowsOSEx;
import com.sun.jna.Native;
import com.sun.jna.platform.win32.Shell32;
import com.sun.jna.platform.win32.ShlObj;
import com.sun.jna.platform.win32.WinDef;
import com.sun.management.OperatingSystemMXBean;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

import static com.jgcomptech.adoptopenjdk.utils.Utils.convertBytesToString;
import static com.jgcomptech.adoptopenjdk.utils.info.OSInfo.*;
import static java.nio.charset.StandardCharsets.UTF_8;

/** Returns information about the system hardware. */
@SuppressWarnings("HardcodedFileSeparator")
public final class HWInfo {
    /** Returns information about the system BIOS. */
    public static final class BIOS {
        /**
         * Returns the system BIOS release date stored in the registry.
         * @return BIOS date as string
         */
        public static String getReleaseDate() {
            if(isWindows()) {
                final String path = "HARDWARE\\DESCRIPTION\\System\\BIOS";
                final String key = "BIOSReleaseDate";
                return WindowsOSEx.Registry.getStringValue(WindowsOSEx.Registry.HKEY.LOCAL_MACHINE, path, key);
            }
            return "Unknown";
        }

        /**
         * Returns the system BIOS version stored in the registry.
         * @return BIOS version as string
         */
        public static String getVersion() {
            if(isWindows()) {
                final String path = "HARDWARE\\DESCRIPTION\\System\\BIOS";
                final String key = "BIOSVersion";
                return WindowsOSEx.Registry.getStringValue(WindowsOSEx.Registry.HKEY.LOCAL_MACHINE, path, key);
            }
            return "Unknown";
        }

        /**
         * Returns the system BIOS vendor name stored in the registry.
         * @return BIOS vendor name as string
         */
        public static String getVendor() {
            if(isWindows()) {
                final String path = "HARDWARE\\DESCRIPTION\\System\\BIOS";
                final String key = "BIOSVendor";
                return WindowsOSEx.Registry.getStringValue(WindowsOSEx.Registry.HKEY.LOCAL_MACHINE, path, key);
            }
            return "Unknown";
        }

        /** Prevents instantiation of this utility class. */
        private BIOS() { }
    }

    /** Returns information about the current network. */
    public static final class Network {
        /**
         * Returns the Internal IP Address.
         * @return Internal IP Address as string
         */
        public static String getInternalIPAddress() {
            try {
                final String ip = (InetAddress.getLocalHost().getHostAddress()).trim();
                return "127.0.0.1".equals(ip) ? "N/A" : ip;
            } catch(final UnknownHostException ex) { return ex.getMessage(); }
        }

        /**
         * Returns the External IP Address by connecting to "http://api.ipify.org".
         * @return External IP address as string
         */
        public static String getExternalIPAddress() {
            final URL url;
            try { url = new URL("http://api.ipify.org"); }
            catch(final MalformedURLException e) { return e.getMessage(); }
            try(final BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(), UTF_8))) {
                return (in.readLine()).trim();
            } catch(final IOException ex) {
                return ex.toString().contains("java.net.UnknownHostException:") ? "N/A" : ex.getMessage();
            }
        }

        /**
         * Returns status of internet connection.
         * @return Internet connection status as boolean
         * */
        public static boolean isConnectedToInternet() { return !getExternalIPAddress().equals("N/A"); }

        /** Prevents instantiation of this utility class. */
        private Network() { }
    }

    /** Returns information about the system manufacturer. */
    public static final class OEM {
        /**
         * Returns the system manufacturer name that is stored in the registry.
         * @return OEM name as string
         * */
        public static String getName() {
            String path = "HARDWARE\\DESCRIPTION\\System\\BIOS";
            String key = "SystemManufacturer";
            final String text = WindowsOSEx.Registry.getStringValue(WindowsOSEx.Registry.HKEY.LOCAL_MACHINE, path, key);
            if(text.trim().isEmpty()) {
                path = "SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\OEMInFormation";
                key = "Manufacturer";
                return WindowsOSEx.Registry.getStringValue(WindowsOSEx.Registry.HKEY.LOCAL_MACHINE, path, key);
            }
            return text;
        }

        /**
         * Returns the system product name that is stored in the registry.
         * @return Product name as string
         * */
        public static String getProductName() {
            String path = "HARDWARE\\DESCRIPTION\\System\\BIOS";
            String key = "SystemProductName";
            final String text = WindowsOSEx.Registry.getStringValue(WindowsOSEx.Registry.HKEY.LOCAL_MACHINE, path, key);
            if(text.trim().isEmpty()) {
                path = "SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\OEMInFormation";
                key = "Model";
                return WindowsOSEx.Registry.getStringValue(WindowsOSEx.Registry.HKEY.LOCAL_MACHINE, path, key);
            }
            return text;
        }

        /** Prevents instantiation of this utility class. */
        private OEM() { }
    }

    /** Returns information about the system processor. */
    public static final class Processor {
        /**
         * Returns the system processor name that is stored in the registry.
         * @return Processor name as string
         * */
        public static String getName() {
            final String path = "HARDWARE\\DESCRIPTION\\System\\CentralProcessor\\0";
            final String key = "ProcessorNameString";
            return WindowsOSEx.Registry.getStringValue(WindowsOSEx.Registry.HKEY.LOCAL_MACHINE, path, key);
        }

        /**
         * Returns the number of cores available on the system processor.
         * @return Number of cores as int
         * @throws IOException if error occurs
         * */
        @SuppressWarnings("CallToRuntimeExec")
        public static int getCores() throws IOException {
            String command = "";

            if(isMac()) command = "sysctl -n machdep.cpu.core_count";
            else if(isLinux()) command = "lscpu";
            else if(isWindows()) command = "cmd /C WMIC CPU Get /Format:List";

            final Process process;
            int numberOfCores = 0;
            int sockets = 0;
            if(isMac()) {
                final String[] cmd = {"/bin/sh", "-c", command};
                process = Runtime.getRuntime().exec(cmd);
            } else process = Runtime.getRuntime().exec(command);

            assert process != null;
            try(final BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), UTF_8))) {
                String line;

                while((line = reader.readLine()) != null) {
                    if(isMac()) {
                        numberOfCores = line.trim().isEmpty() ? 0 : Integer.parseInt(line);
                    } else if(isLinux()) {
                        if(line.contains("Core(s) per socket:")) {
                            numberOfCores =
                                    Integer.parseInt(line.split("\\s+")[line.split("\\s+").length - 1]);
                        }
                        if(line.contains("Socket(s):")) {
                            sockets = Integer.parseInt(line.split("\\s+")[line.split("\\s+").length - 1]);
                        }
                    } else if(isWindows() && line.contains("NumberOfCores")) {
                        numberOfCores = Integer.parseInt(line.split("=")[1]);
                    }
                }
            }

            if(isLinux()) return numberOfCores * sockets;
            return numberOfCores;
        }

        /** Prevents instantiation of this utility class. */
        private Processor() { }
    }

    /** Returns information about the system RAM. */
    public static final class RAM {
        /**
         * Returns the total ram installed on the system.
         * @return Total Ram as string
         * */
        public static String getTotalRam() {
            final long memorySize = ((OperatingSystemMXBean)
                    ManagementFactory.getOperatingSystemMXBean()).getTotalPhysicalMemorySize();
            return convertBytesToString((double) memorySize);
        }

        /** Prevents instantiation of this utility class. */
        private RAM() { }
    }

    /** Returns information about the system storage. */
    public static final class Storage {
        /**
         * Returns the file path to the root of the drive Windows is installed on.
         * @return System drive file path as string
         * */
        public static String getSystemDrivePath() {
            final char[] pszPath = new char[WinDef.MAX_PATH];
            Shell32.INSTANCE.SHGetFolderPath(
                    null, ShlObj.CSIDL_WINDOWS, null, ShlObj.SHGFP_TYPE_CURRENT, pszPath);
            return Native.toString(pszPath).replace("WINDOWS", "");
        }

        /**
         * Returns the file path to the Windows directory.
         * @return Windows directory file path as string
         * */
        public static String getWindowsPath() {
            final char[] pszPath = new char[WinDef.MAX_PATH];
            Shell32.INSTANCE.SHGetFolderPath(
                    null, ShlObj.CSIDL_WINDOWS, null, ShlObj.SHGFP_TYPE_CURRENT, pszPath);
            return Native.toString(pszPath);
        }

        /**
         * Returns the drive size of the drive Windows is installed on.
         * @return System drive size as string
         * */
        public static String getSystemDriveSize() {
            return getDriveSize(getSystemDrivePath().replace(":/", "").charAt(0));
        }

        /**
         * Returns the drive size of the specified drive by drive letter, returns "N/A" if drive doesn't exist.
         * @param driveLetter Drive letter of drive to get the size of
         * @return Drive size of the specified drive letter
         * */
        public static String getDriveSize(final char driveLetter) {
            final File aDrive = new File(driveLetter + ":");
            return aDrive.exists() ? convertBytesToString((double) aDrive.getTotalSpace()) : "N/A";
        }

        /**
         * Returns the free space of drive of the drive Windows is installed on.
         * @return System drive free space as string
         * */
        public static String getSystemDriveFreeSpace() {
            return getDriveFreeSpace(getSystemDrivePath().replace(":/", "").charAt(0));
        }

        /**
         * Returns the free space of the specified drive by drive letter, returns "N/A" if drive doesn't exist.
         * @param driveLetter Drive letter of drive to get the free space of
         * @return Drive free space of the specified drive letter
         * */
        public static String getDriveFreeSpace(final char driveLetter) {
            final File aDrive = new File(driveLetter + ":");
            return aDrive.exists() ? convertBytesToString((double) aDrive.getUsableSpace()) : "N/A";
        }

        /** Prevents instantiation of this utility class. */
        private Storage() { }
    }

    /** A Hardware Object for use with the {@link ComputerInfo} class. */
    public static final class HWObject {
        String SystemOEM;
        String ProductName;
        BIOSObject BIOS;
        NetworkObject Network;
        ProcessorObject Processor;
        RAMObject RAM;
        StorageObject Storage;

        public String getSystemOEM() { return SystemOEM; }

        public String getProductName() { return ProductName; }

        public BIOSObject getBIOS() { return BIOS; }

        public NetworkObject getNetwork() { return Network; }

        public ProcessorObject getProcessor() { return Processor; }

        public RAMObject getRAM() { return RAM; }

        public StorageObject getStorage() { return Storage; }
    }

    /** A BIOS Object for use with the {@link ComputerInfo} class. */
    public static final class BIOSObject {
        String Name;
        String ReleaseDate;
        String Vendor;
        String Version;

        public String getName() { return Name; }

        public String getReleaseDate() { return ReleaseDate; }

        public String getVendor() { return Vendor; }

        public String getVersion() { return Version; }
    }

    /** A Drive Object for use with the {@link ComputerInfo} class. */
    public static final class DriveObject {
        //String Name;
        //String Format;
        //String Label;
        String DriveType;
        String TotalSize;
        String TotalFree;

        //public String getName() { return Name; }
        //public String getFormat() { return Format; }
        //public String getLabel() { return Label; }
        public String getDriveType() { return DriveType; }

        public String getTotalSize() { return TotalSize; }

        public String getTotalFree() { return TotalFree; }
    }

    /** A Network Object for use with the {@link ComputerInfo} class. */
    public static final class NetworkObject {
        String InternalIPAddress;
        String ExternalIPAddress;
        Boolean ConnectionStatus;

        public String getInternalIPAddress() { return InternalIPAddress; }

        public String getExternalIPAddress() { return ExternalIPAddress; }

        public Boolean getConnectionStatus() { return ConnectionStatus; }
    }

    /** A Processor Object for use with the {@link ComputerInfo} class. */
    public static final class ProcessorObject {
        String Name;
        int Cores;

        public String getName() { return Name; }

        public int getCores() { return Cores; }
    }

    /** A RAM Object for use with the {@link ComputerInfo} class. */
    public static final class RAMObject {
        String TotalInstalled;

        public String getTotalInstalled() { return TotalInstalled; }
    }

    /** A Storage Object for use with the {@link ComputerInfo} class. */
    public static final class StorageObject {
        //List<DriveObject> InstalledDrives;
        DriveObject SystemDrive;

        //public List<DriveObject> InstalledDrives() { return InstalledDrives; }
        public DriveObject getSystemDrive() { return SystemDrive; }
    }

    /** Prevents instantiation of this utility class. */
    private HWInfo() { }
}
