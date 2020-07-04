package com.jgcomptech.adoptopenjdk.utils.info.os;

import com.jgcomptech.adoptopenjdk.utils.SingletonUtils;
import com.jgcomptech.adoptopenjdk.utils.info.AbstractOperatingSystem;
import com.jgcomptech.adoptopenjdk.utils.info.OSInfo;
import com.jgcomptech.adoptopenjdk.utils.info.enums.OSList;
import com.jgcomptech.adoptopenjdk.utils.osutils.windows.WmiUtil;
import com.jgcomptech.adoptopenjdk.utils.osutils.windows.enums.OtherConsts;
import com.jgcomptech.adoptopenjdk.utils.osutils.windows.enums.WMIClasses;
import com.sun.jna.platform.win32.COM.WbemcliUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static com.jgcomptech.adoptopenjdk.utils.info.os.WindowsOSEx.getSystemMetrics;
import static com.jgcomptech.adoptopenjdk.utils.info.os.WindowsOSEx.isWin8OrLater;

public class WindowsOS extends AbstractOperatingSystem {
    private final Logger logger = LoggerFactory.getLogger(WindowsOS.class);

    private WindowsOS() { }

    public static synchronized WindowsOS getInstance() {
        return SingletonUtils.getInstance(WindowsOS.class, WindowsOS::new);
    }

    private enum ArchProperty { ADDRESSWIDTH }

    @Override
    public String getName() {
        switch(getNameEnum()) {
            case WindowsXP: return "Windows XP";
            case WindowsXP64: return "Windows XP x64";
            case WindowsVista: return "Windows Vista";
            case Windows7: return "Windows 7";
            case Windows8: return "Windows 8";
            case Windows81: return "Windows 8.1";
            case Windows10: return "Windows 10";
            case Windows2003: return "Windows 2003";
            case Windows2003R2: return "Windows 2003 R2";
            case Windows2008: return "Windows 2008";
            case Windows2008R2: return "Windows 2008 R2";
            case Windows2012: return "Windows 2012";
            case Windows2012R2: return "Windows 2012 R2";
            case Windows2016: return "Windows 2016";
            default: return "Unknown";
        }
    }

    @Override
    public OSList getNameEnum() {
        try {
            switch(WindowsOSEx.Version.getNumber()) {
                case 51:
                    return OSList.WindowsXP;
                case 52:
                    return isServer() ? (getSystemMetrics(OtherConsts.SMServerR2)
                            ? OSList.Windows2003R2 : OSList.Windows2003) : OSList.WindowsXP64;
                case 60:
                    return isServer() ? OSList.Windows2008 : OSList.WindowsVista;
                case 61:
                    return isServer() ? OSList.Windows2008R2 : OSList.Windows7;
                case 62:
                    return isServer() ? OSList.Windows2012 : OSList.Windows8;
                case 63:
                    return isServer() ? OSList.Windows2012R2 : OSList.Windows81;
                case 100:
                    return isServer() ? OSList.Windows2016 : OSList.Windows10;
                default:
                    return OSList.Unknown;
            }
        } catch (final IOException | InterruptedException e) {
            return OSList.Unknown;
        }
    }

    /**
     * Returns a full version String, ex.: "Windows XP SP2 (32 Bit)".
     * @return String representing a fully displayable version
     */
    @Override
    public String getNameExpanded() {
        try {
            final String SPString = isWin8OrLater() ? "- " + WindowsOSEx.Version.getBuild()
                    : " SP" + WindowsOSEx.ServicePack.getString().replace("Service Pack ", "");

            return getName() + ' ' + WindowsOSEx.Edition.getString() + ' '
                    + SPString + " (" + OSInfo.getBitNumber() + " Bit)";
        } catch (final IOException | InterruptedException e) {
            return "Unknown";
        }
    }

    @Override
    public String getVersion() throws IOException, InterruptedException {
        return WindowsOSEx.Version.getMain();
    }

    @Override
    public String getManufacturer() {
        return "Microsoft";
    }

    @Override
    public boolean isServer() {
        return WindowsOSEx.Edition.isWindowsServer();
    }

    @Override
    public boolean is64BitOS() {
        if (System.getenv("ProgramFiles(x86)") != null) return true;
        final WbemcliUtil.WmiQuery<ArchProperty> query = WindowsOSEx.WMI.newWmiQuery(WMIClasses.Hardware.Processor, ArchProperty.class);
        final WbemcliUtil.WmiResult<ArchProperty> result = WmiUtil.queryWMI(query);
        if (result.getResultCount() > 0) {
            return WmiUtil.getUint16(result, ArchProperty.ADDRESSWIDTH, 0) == 64;
        }

        return false;
    }
}
