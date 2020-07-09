package com.jgcomptech.adoptopenjdk.utils.info.os;

import com.jgcomptech.adoptopenjdk.utils.info.OperatingSystem;
import com.jgcomptech.adoptopenjdk.utils.info.enums.OSList;
import com.jgcomptech.adoptopenjdk.utils.osutils.ExecutingCommand;
import com.jgcomptech.adoptopenjdk.utils.osutils.windows.enums.*;
import com.sun.jna.Native;
import com.sun.jna.platform.win32.Advapi32;
import com.sun.jna.platform.win32.Advapi32Util;
import com.sun.jna.platform.win32.COM.WbemcliUtil;
import com.sun.jna.platform.win32.Secur32;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.win32.W32APIOptions;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static com.jgcomptech.adoptopenjdk.utils.Utils.*;
import static com.jgcomptech.adoptopenjdk.utils.info.OSInfo.*;
import static java.nio.charset.StandardCharsets.UTF_8;

/** Returns extended information about the current Windows installation. */
public final class WindowsOSEx {
    public static final OperatingSystem OS = WindowsOS.getInstance();

    /** Returns the product type of the operating system running on this Computer. */
    public static final class Edition {
        /**
         * Identifies if OS is a Windows Server OS.
         * @return true if OS is a Windows Server OS
         */
        public static boolean isWindowsServer() {
            return Edition.getProductTypeEnum() != ProductType.NTWorkstation;
        }

        /**
         * Identifies if OS is a Windows Domain Controller.
         * @return true if OS is a Windows Server OS
         */
        public static boolean isWindowsDomainController() {
            return Edition.getProductTypeEnum() == ProductType.NTDomainController;
        }

        /**
         * Returns the product type of the OS as an integer.
         * @return integer equivalent of the operating system product type
         */
        public static int getProductType() {
            final WinNT.OSVERSIONINFOEX osVersionInfo = new WinNT.OSVERSIONINFOEX();
            return getVersionInfoFailed(osVersionInfo)
                    ? ProductEdition.Undefined.getValue()
                    : osVersionInfo.wProductType;
        }

        /**
         * Returns the product type of the OS as an enum.
         * @return enum equivalent of the operating system product type
         */
        public static ProductType getProductTypeEnum() {
            return ProductType.parse(getProductType());
        }

        /**
         * Returns the product type of the OS as a string.
         * @return string containing the the operating system product type
         * @throws IOException if error occurs
         * @throws InterruptedException if command is interrupted
         */
        public static String getString() throws IOException, InterruptedException {
            if(Version.getMajor() == 5) return getVersion5();
            if(Version.getMajor() == 6 || Version.getMajor() == 10) return getVersion6AndUp();
            return "";
        }

        /**
         * Returns the product type from Windows 2000 to XP and Server 2000 to 2003.
         * @throws IOException if error occurs
         * @throws InterruptedException if command is interrupted
         * @return the version string
         */
        private static String getVersion5() throws IOException, InterruptedException {
            final WinNT.OSVERSIONINFOEX osVersionInfo = new WinNT.OSVERSIONINFOEX();
            if(getVersionInfoFailed(osVersionInfo)) return "";
            final List<VERSuite> verSuite = VERSuite.parse(osVersionInfo.wSuiteMask);

            if(getSystemMetrics(OtherConsts.SMMediaCenter)) return " Media Center";
            if(getSystemMetrics(OtherConsts.SMTabletPC)) return " Tablet PC";
            if(isWindowsServer()) {
                if(Version.getMinor() == 0) {
                    // Windows 2000 Datacenter Server
                    if(verSuite.contains(VERSuite.Datacenter)) return " Datacenter Server";
                    // Windows 2000 Advanced Server
                    if(verSuite.contains(VERSuite.Enterprise)) return " Advanced Server";
                    // Windows 2000 Server
                    return " Server";
                }
                if(Version.getMinor() == 2) {
                    // Windows Server 2003 Datacenter Edition
                    if(verSuite.contains(VERSuite.Datacenter)) return " Datacenter Edition";
                    // Windows Server 2003 Enterprise Edition
                    if(verSuite.contains(VERSuite.Enterprise)) return " Enterprise Edition";
                    // Windows Server 2003 Storage Edition
                    if(verSuite.contains(VERSuite.StorageServer)) return " Storage Edition";
                    // Windows Server 2003 Compute Cluster Edition
                    if(verSuite.contains(VERSuite.ComputeServer)) return " Compute Cluster Edition";
                    // Windows Server 2003 Web Edition
                    if(verSuite.contains(VERSuite.Blade)) return " Web Edition";
                    // Windows Server 2003 Standard Edition
                    return " Standard Edition";
                }
            } else {
                //Windows XP Embedded
                if(verSuite.contains(VERSuite.EmbeddedNT)) return " Embedded";
                // Windows XP / Windows 2000 Professional
                return (verSuite.contains(VERSuite.Personal)) ? " Home" : " Professional";
            }
            return "";
        }

        /**
         * Returns the product type from Windows Vista to 10 and Server 2008 to 2016.
         * @throws IOException if error occurs
         * @throws InterruptedException if command is interrupted
         * @return the version string
         */
        private static String getVersion6AndUp() throws IOException, InterruptedException {
            final IntByReference strProductType = new IntByReference();
            Kernel32.INSTANCE.GetProductInfo(Version.getMajor(), Version.getMinor(),
                            0, 0, strProductType);
            return ProductEdition.parse(strProductType.getValue()).getFullName();
        }

        /** Prevents instantiation of this utility class. */
        private Edition() { }
    }

    /** Returns the service pack information of the operating system running on this Computer. */
    public static final class ServicePack {
        /**
         * Returns the service pack information of the operating system running on this Computer.
         * @return A String containing the operating system service pack information
         * @throws IOException if error occurs
         * @throws InterruptedException if command is interrupted
         */
        public static String getString() throws IOException, InterruptedException {
            final String sp = Integer.toString(getNumber());
            return isWin8OrLater() ? "" : sp.trim().isEmpty() ? "Service Pack 0" : "Service Pack " + sp;
        }

        /**
         * Returns the service pack information of the operating system running on this Computer.
         * @return A int containing the operating system service pack number
         */
        public static int getNumber() {
            final WinNT.OSVERSIONINFOEX osVersionInfo = new WinNT.OSVERSIONINFOEX();
            return getVersionInfoFailed(osVersionInfo)
                    ? -1
                    : osVersionInfo.wServicePackMajor.intValue();
        }

        /** Prevents instantiation of this utility class. */
        private ServicePack() { }
    }

    /** Returns info about the currently logged in user account. */
    public static final class Users {
        /**
         * Identifies if the current user is an account administrator.
         * @return true if current user is an account administrator
         */
        public static boolean isCurrentUserAdmin() {
            final Advapi32Util.Account[] groups = Advapi32Util.getCurrentUserGroups();
            for(final Advapi32Util.Account group : groups) {
                final WinNT.PSIDByReference sid = new WinNT.PSIDByReference();
                Advapi32.INSTANCE.ConvertStringSidToSid(group.sidString, sid);
                if(Advapi32.INSTANCE.IsWellKnownSid(sid.getValue(),
                        WinNT.WELL_KNOWN_SID_TYPE.WinBuiltinAdministratorsSid)) {
                    return true;
                }
            }
            return false;
        }

        /**
         * Returns the user name of the person who is currently logged on to the Windows operating system.
         * @return Logged in username as string
         * @throws IllegalStateException if cannot retrieve the logged-in username
         */
        @SuppressWarnings("StringSplitter")
        public static String getLoggedInUserName() {
            final char[] userNameBuf = new char[10000];
            final IntByReference size = new IntByReference(userNameBuf.length);
            final boolean result = Secur32.INSTANCE.GetUserNameEx(
                    Secur32.EXTENDED_NAME_FORMAT.NameSamCompatible, userNameBuf, size);

            if(!result)
                throw new IllegalStateException("Cannot retrieve name of the currently logged-in user");

            return new String(userNameBuf, 0, size.getValue()).split("\\\\")[1];
        }

        /** Prevents instantiation of this utility class. */
        private Users() { }
    }

    /** Returns the full version of the operating system running on this Computer. */
    public static final class Version {
        /**
         * Returns the full version of the operating system running on this Computer.
         * @return Full version as string
         * @throws IOException if error occurs
         * @throws InterruptedException if command is interrupted
         */
        public static String getMain() throws IOException, InterruptedException {
            return getVersionInfo(WindowsOSEx.Version.Type.Main);
        }

        /**
         * Returns the major version of the operating system running on this Computer.
         * @return Major version as int
         * @throws IOException if error occurs
         * @throws InterruptedException if command is interrupted
         */
        public static int getMajor() throws IOException, InterruptedException {
            return Integer.parseInt(getVersionInfo(Type.Major)); }

        /**
         * Returns the minor version of the operating system running on this Computer.
         * @return Minor version as int
         * @throws IOException if error occurs
         * @throws InterruptedException if command is interrupted
         */
        public static int getMinor() throws IOException, InterruptedException {
            return Integer.parseInt(getVersionInfo(Type.Minor)); }

        /**
         * Returns the build version of the operating system running on this Computer.
         * @return Build version as int
         * @throws IOException if error occurs
         * @throws InterruptedException if command is interrupted
         */
        public static int getBuild() throws IOException, InterruptedException {
            return Integer.parseInt(getVersionInfo(Type.Build)); }

        /**
         * Returns the revision version of the operating system running on this Computer.
         * @return Build Revision as int
         * @throws IOException if error occurs
         * @throws InterruptedException if command is interrupted
         */
        public static int getRevision() throws IOException, InterruptedException {
            return Integer.parseInt(getVersionInfo(Type.Revision)); }

        /**
         * Returns a numeric value representing OS version.
         * @return OSMajorVersion times 10 plus OSMinorVersion
         * @throws IOException if error occurs
         * @throws InterruptedException if command is interrupted
         */
        public static int getNumber() throws IOException, InterruptedException { return (getMajor() * 10 + getMinor()); }

        private static String getVersionInfo(final Type type) throws IOException, InterruptedException {
            final String VersionString = WMI.getWMIValue("SELECT * FROM "
                    + WMIClasses.OS.OperatingSystem,"Version");

            String Temp;
            final String Major = VersionString.substring(0, VersionString.indexOf("."));
            Temp = VersionString.substring(Major.length() + 1);
            final String Minor = Temp.substring(0, VersionString.indexOf(".") - 1);
            Temp = VersionString.substring(Major.length() + 1 + Minor.length() + 1);
            final String Build;
            if(Temp.contains(".")) {
                Build = Temp.substring(0, VersionString.indexOf(".") - 1);
                Temp = VersionString.substring(Major.length() + 1 + Minor.length() + 1 + Build.length() + 1);
            } else {
                Build = Temp;
                Temp = "0";
            }
            final String Revision = Temp;

            switch(type) {
                case Main:
                    return VersionString;
                case Major:
                    return Major;
                case Minor:
                    return Minor;
                case Build:
                    return Build;
                case Revision:
                    return Revision;
            }
            return "0";
        }


        /** A list of Version types used in the {@link WindowsOSEx} class. */
        public enum Type {
            Main,
            Major,
            Minor,
            Build,
            Revision
        }

        /** Prevents instantiation of this utility class. */
        private Version() { }
    }

    /** Returns information from WMI. */
    public static final class WMI {
        private static final String CRLF = System.lineSeparator();

        /**
         * Generate a VBScript string capable of querying the desired WMI information.
         * @param wmiQueryStr                the query string to be passed to the WMI sub-system. <br>i.e. "Select *
         *                                   FROM Win32_ComputerSystem"
         * @param wmiCommaSeparatedFieldName a comma separated list of the WMI fields to be collected from the query
         *                                   results. <br>i.e. "Model"
         * @return the vbscript string.
         */
        @SuppressWarnings("StringSplitter")
        private static String getVBScript(final String wmiQueryStr, final String wmiCommaSeparatedFieldName) {
            final StringBuilder vbs = new StringBuilder();
            vbs.append("Dim oWMI : Set oWMI = GetObject(\"winmgmts:\")").append(CRLF);
            vbs.append(String.format("Dim classComponent : Set classComponent = oWMI.ExecQuery(\"%s\")",
                    wmiQueryStr)).append(CRLF);
            vbs.append("Dim obj, strData").append(CRLF);
            vbs.append("For Each obj in classComponent").append(CRLF);
            final String[] wmiFieldNameArray = wmiCommaSeparatedFieldName.split(",");
            for(final String fieldName : wmiFieldNameArray) {
                vbs.append(String.format("  strData = strData & obj.%s & VBCrLf", fieldName)).append(CRLF);
            }
            vbs.append("Next").append(CRLF);
            vbs.append("wscript.echo strData").append(CRLF);
            return vbs.toString();
        }

        /**
         * Get an environment variable from Windows.
         * @param variableName The name of the environment variable to get
         * @return The value of the environment variable
         * @throws IOException if error occurs
         * @throws InterruptedException if command is interrupted
         * @throws IllegalArgumentException if Environment Variable does't exist
         */
        @SuppressWarnings("HardcodedFileSeparator")
        public static String getEnvironmentVar(final String variableName) throws IOException, InterruptedException {
            final String varName = '%' + variableName + '%';
            String variableValue = ExecutingCommand.runNewCmd("cmd").setArgs("/C echo " + varName).getResult().get(0);
            //execute(new String[] {"cmd.exe", "/C", "echo " + varName});
            variableValue = variableValue.replace("\"", "");
            checkArgument(!variableValue.equals(varName),
                    String.format("Environment variable '%s' does not exist!", variableName));
            return variableValue;
        }

        /**
         * Write the specified string to the specified file.
         * @param filename the file to write the string to
         * @param data     a string to be written to the file
         * @throws IOException if error occurs
         */
        private static void writeStringToFile(final String filename, final String data) throws IOException {
            try(final Writer output = Files.newBufferedWriter(Paths.get(filename), UTF_8)) {
                output.write(data);
                output.flush();
            }
        }

        /**
         * Get the given WMI value from the WMI subsystem on the local computer.
         * @param wmiQueryStr                the query string as syntactically defined by the WMI reference
         * @param wmiCommaSeparatedFieldName the field object that you want to get out of the query results
         * @return the value
         * @throws IOException if error occurs
         * @throws InterruptedException if command is interrupted
         */
        @SuppressWarnings("HardcodedFileSeparator")
        public static String getWMIValue(final String wmiQueryStr, final String wmiCommaSeparatedFieldName)
                throws IOException, InterruptedException {
            final String tmpFileName = getEnvironmentVar(
                    "TEMP").trim() + File.separator + "javawmi.vbs";
            writeStringToFile(tmpFileName, getVBScript(wmiQueryStr, wmiCommaSeparatedFieldName));
            final String output = ExecutingCommand.runNewCmd("cmd.exe").setArgs("/C cscript.exe " + tmpFileName)
                    .getResult().toString();
            Files.delete(Paths.get(tmpFileName));

            return output.trim();
        }

        /**
         * Instantiate a WMI Query in the default namespace
         *
         * @param <T>
         *                     The enum type
         * @param wmiClassName The WMI Class to use. May include a WHERE clause
         *                     with filtering conditions.
         * @param propertyEnum An Enum that contains the properties to query
         * @return a new WMI Query object
         */
        public static <T extends Enum<T>> WbemcliUtil.WmiQuery<T> newWmiQuery(final String wmiClassName, final Class<T> propertyEnum) {
            checkArgumentNotNullOrEmpty(wmiClassName, "wmiClassName Cannot Be Null Or Empty!");
            checkArgumentNotNull(propertyEnum, "propertyEnum Cannot Be Null!");
            return new WbemcliUtil.WmiQuery<>(wmiClassName, propertyEnum);
        }

        /** Prevents instantiation of this utility class. */
        private WMI() { }
    }

    /**
     * Identifies if OS is a Windows Server OS.
     * @return true if OS is a Windows Server OS
     */
    public static boolean isWindowsServer() { return Edition.isWindowsServer(); }

    /**
     * Identifies if OS is XP or later.
     * @return true if XP or later, false if 2000 or previous
     * @throws IOException if error occurs
     * @throws InterruptedException if command is interrupted
     */
    public static boolean isWinXPOrLater() throws IOException, InterruptedException {
        return Version.getNumber() >= 51; }

    /**
     * Identifies if OS is XP x64 or later.
     * @return true if XP x64 or later, false if XP or previous
     * @throws IOException if error occurs
     * @throws InterruptedException if command is interrupted
     */
    public static boolean isWinXP64OrLater() throws IOException, InterruptedException {
        return Version.getNumber() >= 52; }

    /**
     * Identifies if OS is Vista or later.
     * @return true if Vista or later, false if XP or previous
     * @throws IOException if error occurs
     * @throws InterruptedException if command is interrupted
     */
    public static boolean isWinVistaOrLater() throws IOException, InterruptedException {
        return Version.getNumber() >= 60; }

    /**
     * Identifies if OS is Windows 7 or later.
     * @return true if Windows 7 or later, false if Vista or previous
     * @throws IOException if error occurs
     * @throws InterruptedException if command is interrupted
     */
    public static boolean isWin7OrLater() throws IOException, InterruptedException {
        return Version.getNumber() >= 61; }

    /**
     * Identifies if OS is Windows 8 or later.
     * @return true if Windows 8 or later, false if Windows 7 or previous
     * @throws IOException if error occurs
     * @throws InterruptedException if command is interrupted
     */
    public static boolean isWin8OrLater() throws IOException, InterruptedException {
        return Version.getNumber() >= 62; }

    /**
     * Identifies if OS is Windows 8.1 or later.
     * @return true if Windows 8.1 or later, false if Windows 8 or previous
     * @throws IOException if error occurs
     * @throws InterruptedException if command is interrupted
     */
    public static boolean isWin81OrLater() throws IOException, InterruptedException {
        return Version.getNumber() >= 63; }

    /**
     * Identifies if OS is Windows 10 or later.
     * @return true if Windows 10 or later, false if Windows 8.1 or previous
     * @throws IOException if error occurs
     * @throws InterruptedException if command is interrupted
     */
    public static boolean isWin10OrLater() throws IOException, InterruptedException {
        return Version.getNumber() >= 100; }

    /**
     * Identifies if OS is the specified OS or later.
     * @param os the OS type to check
     * @return true if the specified OS or later
     * @throws IOException if error occurs
     * @throws InterruptedException if command is interrupted
     */
    public static boolean isOrLater(final OSList os) throws IOException, InterruptedException {
        switch(os) {
            case Unknown:
                return false;
            case MacOSX:
                return isMac();
            case Linux:
                return isLinux();
            case Solaris:
                return isSolaris();
            case Windows2000AndPrevious:
                return Version.getNumber() < 51;
            case WindowsXP:
                return isWinXPOrLater();
            case WindowsXP64:
                return isWinXP64OrLater();
            case WindowsVista:
                return isWinVistaOrLater();
            case Windows7:
                return isWin7OrLater();
            case Windows8:
                return isWin8OrLater();
            case Windows81:
                return isWin81OrLater();
            case Windows10:
                return isWin10OrLater();
            case Windows2003:
                return isWinXP64OrLater() && isWindowsServer() && !getSystemMetrics(OtherConsts.SMServerR2);
            case Windows2003R2:
                return isWinXP64OrLater() && isWindowsServer() && getSystemMetrics(OtherConsts.SMServerR2);
            case Windows2008:
                return isWinVistaOrLater() && isWindowsServer();
            case Windows2008R2:
                return isWin7OrLater() && isWindowsServer();
            case Windows2012:
                return isWin8OrLater() && isWindowsServer();
            case Windows2012R2:
                return isWin81OrLater() && isWindowsServer();
            case Windows2016:
                return isWin10OrLater() && isWindowsServer();
        }
        return false;
    }

    /**
     * Checks if a system metrics value is true.
     * @param index Value to check for
     * @return False if value is false
     */
    public static boolean getSystemMetrics(final int index) {
        return Kernel32.INSTANCE.GetSystemMetrics(index); }

    /**
     * Generates a new instance of the VersionInfo object.
     * @param osVersionInfo Empty VersionInfo object to fill
     * @return True if an error occurs
     */
    public static boolean getVersionInfoFailed(final WinNT.OSVERSIONINFOEX osVersionInfo) {
        return !Kernel32.INSTANCE.GetVersionEx(osVersionInfo); }

    /** Interface object to hold all the Kernel32 Instances. */
    @SuppressWarnings("InterfaceNeverImplemented")
    private interface Kernel32 extends com.sun.jna.platform.win32.Kernel32 {
        Kernel32 INSTANCE = Native.load("kernel32", Kernel32.class,
                W32APIOptions.DEFAULT_OPTIONS);

        boolean GetProductInfo(
                int dwOSMajorVersion,
                int dwOSMinorVersion,
                int dwSpMajorVersion,
                int dwSpMinorVersion,
                IntByReference pdwReturnedProductType);

        boolean GetSystemMetrics(int nIndex);
    }

    /** Prevents instantiation of this utility class. */
    private WindowsOSEx() { }
}
