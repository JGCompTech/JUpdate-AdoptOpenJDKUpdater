package com.jgcomptech.adoptopenjdk.utils.info;


import com.jgcomptech.adoptopenjdk.utils.info.enums.OSList;
import com.jgcomptech.adoptopenjdk.utils.info.enums.OSType;

import static com.jgcomptech.adoptopenjdk.utils.info.OSInfo.*;

@SuppressWarnings("WeakerAccess")
public final class OS {
    public static final OSType OS_TYPE;
    public static final String MANUFACTURER;
    public static final String VERSION;
    public static final int ARCHITECTURE;
    public static final String ARCHITECTURE_STRING;
    public static final String NAME;
    public static final OSList NAME_ENUM;
    public static final String NAME_EXPANDED;
    public static final boolean IS_OS_WINDOWS;
    public static final boolean IS_OS_MAC;
    public static final boolean IS_OS_LINUX;
    public static final boolean IS_OS_SOLARIS;
    public static final boolean IS_OS_FREE_BSD;
    public static final boolean IS_ANDROID;
    public static final boolean IS_SERVER;

    static {
        OS_TYPE = getOSType();
        MANUFACTURER = getManufacturer();
        VERSION = getVersion();
        ARCHITECTURE = getBitNumber();
        ARCHITECTURE_STRING = getBitString();
        NAME = getName();
        NAME_ENUM = getNameEnum();
        NAME_EXPANDED = getNameExpanded();
        IS_OS_WINDOWS = isWindows();
        IS_OS_MAC = isMac();
        IS_OS_LINUX = isLinux();
        IS_OS_SOLARIS = isSolaris();
        IS_OS_FREE_BSD = isFreeBSD();
        IS_ANDROID = isAndroid();
        IS_SERVER = isServer();
    }

    /**
     * Prevents instantiation of this utility class.
     */
    private OS() { }

    public static void load() { }
}
