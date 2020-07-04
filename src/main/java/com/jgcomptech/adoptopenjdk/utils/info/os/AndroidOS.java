package com.jgcomptech.adoptopenjdk.utils.info.os;

import com.jgcomptech.adoptopenjdk.utils.SingletonUtils;
import com.jgcomptech.adoptopenjdk.utils.info.AbstractOperatingSystem;
import com.jgcomptech.adoptopenjdk.utils.info.enums.OSList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AndroidOS extends AbstractOperatingSystem {
    private final Logger logger = LoggerFactory.getLogger(AndroidOS.class);

    private AndroidOS() { }

    public static synchronized AndroidOS getInstance() {
        return SingletonUtils.getInstance(AndroidOS.class, AndroidOS::new);
    }

    @Override
    public String getName() {
        return "Android";
    }

    @Override
    public OSList getNameEnum() {
        return OSList.Android;
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
        return "Google";
    }

    @Override
    public boolean isServer() {
        return false;
    }

    @Override
    public boolean is64BitOS() {
        return false;
    }
}
