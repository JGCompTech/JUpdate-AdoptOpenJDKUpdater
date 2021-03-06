package com.jgcomptech.adoptopenjdk.utils.info.os;

import com.jgcomptech.adoptopenjdk.utils.SingletonUtils;
import com.jgcomptech.adoptopenjdk.utils.Utils;
import com.jgcomptech.adoptopenjdk.utils.info.AbstractOperatingSystem;
import com.jgcomptech.adoptopenjdk.utils.info.enums.OSList;
import com.jgcomptech.adoptopenjdk.utils.osutils.ExecutingCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class MacOS extends AbstractOperatingSystem {
    private final Logger logger = LoggerFactory.getLogger(MacOS.class);

    private MacOS() { }

    public static synchronized MacOS getInstance() {
        return SingletonUtils.getInstance(MacOS.class, MacOS::new);
    }

    @Override
    public String getName() {
        return "Mac OSX";
    }

    @Override
    public OSList getNameEnum() {
        return OSList.MacOSX;
    }

    @Override
    public String getNameExpanded() {
        return OS_NAME;
    }

    @Override
    public String getVersion() {
        return System.getProperty("os.version");
    }

    @Override
    public String getManufacturer() {
        return "Apple";
    }

    @Override
    public boolean isServer() {
        return false;
    }

    @Override
    public boolean is64BitOS() {
        try {
            return Utils.parseIntOrDefault(ExecutingCommand
                    .runNewCmd("getconf LONG_BIT").getFirstResult(), 32, logger) == 64;
        } catch (final IOException | InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }
}
