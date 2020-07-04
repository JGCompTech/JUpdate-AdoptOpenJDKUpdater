package com.jgcomptech.adoptopenjdk.info.os;

import com.jgcomptech.adoptopenjdk.info.AbstractOperatingSystem;
import com.jgcomptech.adoptopenjdk.info.enums.OSList;
import com.jgcomptech.adoptopenjdk.util.SingletonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UnknownOS extends AbstractOperatingSystem {
    private final Logger logger = LoggerFactory.getLogger(UnknownOS.class);

    private UnknownOS() { }

    public static synchronized UnknownOS getInstance() {
        return SingletonUtils.getInstance(UnknownOS.class, UnknownOS::new);
    }

    @Override
    public String getName() {
        return OS_NAME;
    }

    @Override
    public OSList getNameEnum() {
        return OSList.Unknown;
    }

    @Override
    public String getNameExpanded() {
        return OS_NAME;
    }

    @Override
    public String getVersion() {
        return "Unknown";
    }

    @Override
    public String getManufacturer() {
        return "Unknown";
    }

    @Override
    public boolean isServer() {
        return false;
    }

    @Override
    public boolean is64BitOS() {
        return System.getProperty("os.arch").contains("64");
    }
}
