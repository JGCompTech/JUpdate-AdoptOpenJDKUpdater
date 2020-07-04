package com.jgcomptech.adoptopenjdk.info.os;

import com.jgcomptech.adoptopenjdk.info.AbstractOperatingSystem;
import com.jgcomptech.adoptopenjdk.info.enums.OSList;
import com.jgcomptech.adoptopenjdk.osutils.ExecutingCommand;
import com.jgcomptech.adoptopenjdk.util.SingletonUtils;
import com.jgcomptech.adoptopenjdk.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class SolarisOS extends AbstractOperatingSystem {
    private final Logger logger = LoggerFactory.getLogger(SolarisOS.class);

    private SolarisOS() { }

    public static synchronized SolarisOS getInstance() {
        return SingletonUtils.getInstance(SolarisOS.class, SolarisOS::new);
    }

    @Override
    public String getName() {
        return "Solaris";
    }

    @Override
    public OSList getNameEnum() {
        return OSList.Solaris;
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
        return "Oracle";
    }

    @Override
    public boolean isServer() {
        return false;
    }

    @Override
    public boolean is64BitOS() {
        try {
            return Utils.parseIntOrDefault(ExecutingCommand
                    .runNewCmd("isainfo -b").getFirstResult(), 32, logger) == 64;
        } catch (final IOException | InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }
}
