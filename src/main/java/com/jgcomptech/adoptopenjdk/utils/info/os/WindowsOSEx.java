package com.jgcomptech.adoptopenjdk.utils.info.os;

import com.jgcomptech.adoptopenjdk.utils.info.OSInfo;
import com.jgcomptech.adoptopenjdk.utils.info.OperatingSystem;
import com.jgcomptech.adoptopenjdk.utils.info.enums.OSList;
import com.jgcomptech.adoptopenjdk.utils.osutils.windows.enums.*;
import com.sun.jna.Native;
import com.sun.jna.platform.win32.*;
import com.sun.jna.platform.win32.COM.WbemcliUtil;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.win32.W32APIOptions;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.plexus.util.FileUtils;

import java.io.*;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.jgcomptech.adoptopenjdk.utils.Utils.*;
import static com.jgcomptech.adoptopenjdk.utils.info.OSInfo.*;
import static java.nio.charset.StandardCharsets.UTF_8;

/** Returns extended information about the current Windows installation. */
public final class WindowsOSEx {
    public static final OperatingSystem OS = WindowsOS.getInstance();

    /** Returns information about the Windows activation status. */
    public static final class Activation {
        /**
         * Identifies if OS is activated.
         * @return true if activated, false if not activated
         * @throws IOException if error occurs
         */
        public static boolean isActivated() throws IOException { return getStatusAsEnum() == ActivationStatus.Licensed; }

        /**
         * Identifies If Windows is Activated, uses the Software Licensing Manager Script,
         * this is the quicker method.
         * @return "Licensed" If Genuinely Activated as enum
         * @throws IOException if error occurs
         */
        public static ActivationStatus getStatusAsEnum() throws IOException {
            return ActivationStatus.parseString(getStatusFromSLMGR());
        }

        /**
         * Identifies If Windows is Activated, uses the Software Licensing Manager Script,
         * this is the quicker method.
         * @return "Licensed" If Genuinely Activated
         * @throws IOException if an error occurs
         */
        public static String getStatusString() throws IOException { return getStatusFromSLMGR(); }

        /**
         * Identifies If Windows is Activated, uses WMI.
         * @return Licensed If Genuinely Activated
         * @throws IOException if error occurs
         * @throws InterruptedException if command is interrupted
         */
        public static String getStatusFromWMI() throws IOException, InterruptedException {
            final String LicenseStatus = WMI.getWMIValue("SELECT * "
                    + "FROM SoftwareLicensingProduct "
                    + "Where PartialProductKey <> null "
                    + "AND ApplicationId='55c92734-d682-4d71-983e-d6ec3f16059f' "
                    + "AND LicenseisAddon=False", "LicenseStatus");
            return ActivationStatus.parse(Integer.parseInt(LicenseStatus)).getFullName();
        }

        /**
         * Identifies If Windows is Activated, uses the Software Licensing Manager Script,
         * this is the quicker method.
         * @return Licensed If Genuinely Activated
         * @throws IOException if an error occurs
         */
        @SuppressWarnings({"CallToRuntimeExec", "HardcodedFileSeparator"})
        public static String getStatusFromSLMGR() throws IOException {
            while(true) {
                final Process p = Runtime.getRuntime().exec(
                        "cscript C:\\Windows\\System32\\Slmgr.vbs /dli");
                try(final BufferedReader stdOut = new BufferedReader(new InputStreamReader(p.getInputStream(), UTF_8))) {
                    String s;
                    while((s = stdOut.readLine()) != null) {
                        //System.out.println(s);
                        if(s.contains("License Status: ")) {
                            return s.replace("License Status: ", "");
                        }
                    }
                }
            }
        }

        /** Prevents instantiation of this utility class. */
        private Activation() { }
    }

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

    /** Returns the different names provided by the operating system. */
    public static final class Name {
        /**
         * Returns a full version String, ex.: "Windows XP SP2 (32 Bit)".
         * @return String representing a fully displayable version as stored in Windows Registry
         */
        @SuppressWarnings("HardcodedFileSeparator")
        public static String getStringExpandedFromRegistry() {
            try {
                final String SPString = isWin8OrLater() ? "- " + Version.getBuild()
                        : " SP" + ServicePack.getString().replace("Service Pack ", "");
                final String key = "Software\\\\Microsoft\\\\Windows NT\\\\CurrentVersion";
                final String value = "ProductName";
                final String text = Registry.getStringValue(Registry.HKEY.LOCAL_MACHINE, key, value);
                return text + ' ' + SPString + " (" + OSInfo.getBitNumber() + " Bit)";
            } catch (final IOException | InterruptedException e) {
                return "Unknown";
            }
        }

        /** Prevents instantiation of this utility class. */
        private Name() { }
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

    /** Returns information about the current Windows installation. */
    public static final class SystemInfo {
        /**
         * Returns information about the current Windows installation as text.
         * @return Information as string
         * @throws IOException if command cannot be run
         */
        @SuppressWarnings("CallToRuntimeExec")
        public static String getInfo() throws IOException {
            final Process process = Runtime.getRuntime().exec("systeminfo");
            try(final BufferedReader systemInformationReader =
                        new BufferedReader(new InputStreamReader(process.getInputStream(), UTF_8))) {
                return systemInformationReader
                        .lines()
                        .map(line -> line + System.lineSeparator())
                        .collect(Collectors.joining())
                        .trim();
            }
        }

        /**
         * Returns current system time.
         * @return Current time as string
         */
        @SuppressWarnings("HardcodedFileSeparator")
        public static String getTime() {
            final WinBase.SYSTEMTIME time = new WinBase.SYSTEMTIME();
            Kernel32.INSTANCE.GetSystemTime(time);
            return time.wMonth + "/" + time.wDay + '/' + time.wYear + ' ' + time.wHour + ':' + time.wMinute;
        }

        /**
         * Returns the network domain name associated with the current user.
         * @return Current domain name as string
         * @throws IllegalStateException if cannot retrieve the joined domain
         */
        @SuppressWarnings("StringSplitter")
        public static String getCurrentDomainName() {
            final char[] userNameBuf = new char[10000];
            final IntByReference size = new IntByReference(userNameBuf.length);
            final boolean result = Secur32.INSTANCE.GetUserNameEx(
                    Secur32.EXTENDED_NAME_FORMAT.NameSamCompatible, userNameBuf, size);

            if(!result)
                throw new IllegalStateException("Cannot retrieve name of the currently joined domain");

            return new String(userNameBuf, 0, size.getValue()).split("\\\\")[0];
        }

        /**
         * Returns the current host name for the system.
         * @return Current domain name as string
         * @throws UnknownHostException if error occurs
         */
        public static String getCurrentMachineName() throws UnknownHostException {
            return InetAddress.getLocalHost().getHostName();
        }

        /**
         * Returns the current Registered Organization.
         * @return Registered Organization as string
         */
        @SuppressWarnings("HardcodedFileSeparator")
        public static String getRegisteredOrganization() {
            final String key = "Software\\Microsoft\\Windows NT\\CurrentVersion";
            final String value = "RegisteredOrganization";
            return Registry.getStringValue(Registry.HKEY.LOCAL_MACHINE, key, value);
        }

        /**
         * Returns the current Registered Owner.
         * @return Registered Owner as string
         */
        @SuppressWarnings("HardcodedFileSeparator")
        public static String getRegisteredOwner() {
            final String key = "Software\\Microsoft\\Windows NT\\CurrentVersion";
            final String value = "RegisteredOwner";
            return Registry.getStringValue(Registry.HKEY.LOCAL_MACHINE, key, value);
        }

        /**
         * Returns the current computer name.
         * @return String value of current computer name
         */
        @SuppressWarnings("HardcodedFileSeparator")
        public static String getComputerNameActive() {
            final String key = "System\\ControlSet001\\Control\\ComputerName\\ActiveComputerName";
            final String value = "ComputerName";
            return Registry.getStringValue(Registry.HKEY.LOCAL_MACHINE, key, value);
        }

        /**
         * Returns the pending computer name that it will update to on reboot.
         * @return String value of the pending computer name
         */
        @SuppressWarnings("HardcodedFileSeparator")
        public static String getComputerNamePending() {
            final String key = "System\\ControlSet001\\Control\\ComputerName\\ComputerName";
            final String value = "ComputerName";
            final String text = Registry.getStringValue(Registry.HKEY.LOCAL_MACHINE, key, value);
            return text.trim().isEmpty() ? "N/A" : text;
        }

        /** Prevents instantiation of this utility class. */
        private SystemInfo() { }
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
            String variableValue = Command.run("cmd", "/C echo " + varName).getResult().get(0);
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
            final String output = Command.run("cmd.exe", "/C cscript.exe " + tmpFileName)
                    .getResult().toString();
            Files.delete(Paths.get(tmpFileName));

            return output.trim();
        }

        @SuppressWarnings("UseOfSystemOutOrSystemErr")
        public static void executeDemoQueries() throws IOException, InterruptedException {
            System.out.println(getWMIValue("Select * FROM " + WMIClasses.OS.ComputerSystem,
                    "Model"));
            System.out.println(getWMIValue("Select Name FROM " + WMIClasses.OS.ComputerSystem,
                    "Name"));
            //System.out.println(getWMIValue("Select Description FROM Win32_PnPEntity", "Description"));
            //System.out.println(getWMIValue("Select Description, Manufacturer FROM Win32_PnPEntity",
            // "Description,Manufacturer"));
            //System.out.println(getWMIValue("Select * FROM Win32_Service WHERE State = 'Stopped'", "Name"));
            //this will return everything since the field is incorrect and was not used to a filter
            //System.out.println(getWMIValue("Select * FROM Win32_Service", "Name"));
            //this will return nothing since there is no field specified
            System.out.println(getWMIValue("Select Name FROM " + WMIClasses.OS.ComputerSystem,
                    ""));
            //this is a failing case WHERE the Win32_Service class does not contain the 'Name' field
            //System.out.println(getWMIValue("Select * FROM Win32_Service", "Name"));
        }

        /**
         * Instantiate a WmiQuery.
         *
         * @param <T>
         *            The enum type
         * @param nameSpace
         *            The WMI namespace to use.
         * @param wmiClassName
         *            The WMI class to use. Optionally include a WQL WHERE
         *            clause with filters results to properties matching the
         *            input.
         * @param propertyEnum
         *            An enum for type mapping.
         * @return a new WMI Query object
         */
        public static <T extends Enum<T>> WbemcliUtil.WmiQuery<T> newWmiQuery(
                final String nameSpace, final String wmiClassName, final Class<T> propertyEnum) {
            checkArgumentNotNullOrEmpty(wmiClassName, "wmiClassName Cannot Be Null Or Empty!");
            checkArgumentNotNull(propertyEnum, "propertyEnum Cannot Be Null!");
            return new WbemcliUtil.WmiQuery<>(nameSpace, wmiClassName, propertyEnum);
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

    /** Returns information from the Windows registry. */
    public static final class Registry {
        /**
         * Gets string value from a registry key.
         * @param hkey Root key to use to access key
         * @param path Key path to access
         * @param key Key name to access
         * @return Key value as string
         */
        public static String getStringValue(final HKEY hkey, final String path, final String key) {
            if(Advapi32Util.registryValueExists(hkey.getKeyObj(), path, key)) {
                return Advapi32Util.registryGetStringValue(hkey.getKeyObj(), path, key);
            }
            return "";
        }

        /** A list of the different parent keys in the Windows Registry that are used in the {@link Registry} class. */
        public enum HKEY {
            CLASSES_ROOT(WinReg.HKEY_CLASSES_ROOT),
            CURRENT_USER(WinReg.HKEY_CURRENT_USER),
            LOCAL_MACHINE(WinReg.HKEY_LOCAL_MACHINE),
            USERS(WinReg.HKEY_USERS),
            PERFORMANCE_DATA(WinReg.HKEY_PERFORMANCE_DATA),
            CURRENT_CONFIG(WinReg.HKEY_CURRENT_CONFIG);

            final WinReg.HKEY keyObj;

            HKEY(final WinReg.HKEY keyObj) {
                this.keyObj = keyObj;
            }

            public WinReg.HKEY getKeyObj() {
                return keyObj;
            }
        }

        /** Prevents instantiation of this utility class. */
        private Registry() { }
    }

    /** Allows you to run console commands and either run them elevated or not and return the result to a string. */
    public static final class Command {

        /**
         * Runs command and returns results to ArrayList in Output object.
         *
         * @param command Command to run
         * @param args    Arguments to pass to command
         * @return Output object
         * @throws IOException if error occurs
         * @throws InterruptedException if command is interrupted
         */
        @SuppressWarnings({"CallToRuntimeExec", "HardcodedFileSeparator"})
        public static Output run(final String command, final String args)
                throws IOException, InterruptedException {
            final Output newOutput = new Output();

            final Process process;
            if(isWindows()) {
                final String cmdString = String.format("cmd /C \"%s %s\"", command, args);
                process = Runtime.getRuntime().exec(cmdString);
            } else {
                process = Runtime.getRuntime().exec(command);
            }

            assert process != null;
            try(final BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(), UTF_8))) {
                String line;
                while((line = br.readLine()) != null) {
                    newOutput.getResult().add(line);
                }
            }

            try(final BufferedReader br = new BufferedReader(new InputStreamReader(process.getErrorStream(), UTF_8))) {
                String line;
                while((line = br.readLine()) != null) {
                    newOutput.getErrors().add(line);
                }
            }

            process.waitFor();

            newOutput.setExitCode(process.exitValue());

            return newOutput;
        }

        /**
         * Runs command elevated, shows cmd window and pauses window when command is complete. <p>
         * If OS is not Windows, command is not elevated.
         *
         * @param command Command to run
         * @param args    Arguments to pass to command
         * @throws IOException if error occurs
         * @throws InterruptedException if command is interrupted
         */
        public static void runElevated(final String command, final String args)
                throws IOException, InterruptedException {
            runElevated(command, args, false, true);
        }

        /**
         * Runs command elevated, shows cmd window and pauses window when command is complete. <p>
         * If OS is not Windows, command is not elevated.
         *
         * @param command        Command to run
         * @param args           Arguments to pass to command
         * @param hideWindow     If true, cmd window will be hidden
         * @param keepWindowOpen If true, pauses cmd window and forces it to stay open after command is completed <p>
         *                       If false, cmd window will close after command is completed
         *                       <p>
         *                       This parameter is ignored if "hideWindow" is true, this prevents cmd window from staying
         *                       open when hidden and unnecessarily using RAM
         * @throws IOException if error occurs
         * @throws InterruptedException if command is interrupted
         */
        @SuppressWarnings({"BooleanParameter", "CallToRuntimeExec"})
        public static void runElevated(final String command, final String args,
                                 final boolean hideWindow, final boolean keepWindowOpen)
                throws IOException, InterruptedException {
            if(isWindows()) {
                final String filename = "temp.bat";

                try(final Writer writer = Files.newBufferedWriter(Paths.get(filename), UTF_8)) {
                    writer.write("@Echo off" + System.lineSeparator());
                    writer.write('"' + command + "\" " + args + System.lineSeparator());
                    if(keepWindowOpen && !hideWindow) { writer.write("pause"); }
                }

                final int windowStatus = hideWindow ? 0 : 1;
                final String operation = "runas";

                final WinDef.HWND hw = null;
                //noinspection ConstantConditions
                Shell32.INSTANCE.ShellExecute(hw, operation, filename, null, null, windowStatus);

                Thread.sleep(2000);

                Files.delete(Paths.get(filename));
            } else {
                final Process process;
                process = Runtime.getRuntime().exec(command);

                assert process != null;

                process.waitFor();
            }
        }

        /** Output object that is returned after the command has completed. */
        @SuppressWarnings({"ClassExtendsConcreteCollection", "UseOfSystemOutOrSystemErr"})
        public static class Output {
            private final ArrayList<String> result = new ArrayList<String>() {
                @Override
                public String toString() {
                    return result.stream()
                            .filter(line -> !line.contains("Windows Script Host Version")
                                    && !line.contains("Microsoft Corporation. All rights reserved.")
                                    && !line.trim().isEmpty())
                            .map(line -> line + System.lineSeparator()).collect(Collectors.joining());
                }
            };
            private int exitCode;
            private final List<String> errors = new ArrayList<>();

            /**
             * Returns the text result of the command.
             * @return the text result of the command
             */
            public List<String> getResult() {
                return result;
            }

            public List<String> getErrors() { return errors; }

            /**
             * Returns the exit code, returns 0 if no error occurred.
             * @return the exit code, returns 0 if no error occurred
             */
            public int getExitCode() {
                return exitCode;
            }

            public void print() { result.forEach(System.out::println); }

            @Override
            public boolean equals(final Object o) {
                if (this == o) return true;

                if (!(o instanceof Output)) return false;

                final Output output = (Output) o;

                return new EqualsBuilder()
                        .append(getExitCode(), output.getExitCode())
                        .append(result, output.result)
                        .isEquals();
            }

            @Override
            public int hashCode() {
                return new HashCodeBuilder(17, 37)
                        .append(result)
                        .append(getExitCode())
                        .toHashCode();
            }

            @Override
            public String toString() {
                return new ToStringBuilder(this)
                        .append("result", result)
                        .append("exitCode", getExitCode())
                        .toString();
            }

            public void setExitCode(int exitCode) {
                this.exitCode = exitCode;
            }
        }

        /** Prevents instantiation of this utility class. */
        private Command() { }
    }

    /** Gets And Decrypts The Current Product Key From The Registry. */
    public static final class ProductKey {
        /**
         * Returns The Current Product Key From The Registry.
         * @return The Current Product Key From The Registry
         * @throws IOException if error occurs
         * @throws InterruptedException if command is interrupted
         */
        public static String getKey() throws IOException, InterruptedException {
            final URL inputUrl = ProductKey.class.getResource("/vbs/getProductKey.vbs");
            final File dest = new File("getProductKey.vbs");
            FileUtils.copyURLToFile(inputUrl, dest);
            final String key = Command.run("cscript GetProductKey.vbs", "").getResult().toString();
            dest.delete();
            return key;
        }
    }

    /**
     * Identifies if OS is activated.
     * @return true if activated, false if not activated
     * @throws IOException if error occurs
     */
    public static boolean isActivated() throws IOException { return Activation.isActivated(); }

    /**
     * Identifies if OS is a Windows Server OS.
     * @return true if OS is a Windows Server OS
     */
    public static boolean isWindowsServer() { return Edition.isWindowsServer(); }

    /**
     * Identifies if OS is a Windows Domain Controller.
     * @return true if OS is a Windows Server OS
     */
    public static boolean isWindowsDomainController() { return Edition.isWindowsDomainController(); }

    /**
     * Identifies if computer has joined a domain.
     * @return true if computer has joined a domain
     * @throws UnknownHostException if error occurs
     */
    public static boolean isDomainJoined() throws UnknownHostException {
        return !SystemInfo.getCurrentMachineName().equals(SystemInfo.getCurrentDomainName());
    }

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
