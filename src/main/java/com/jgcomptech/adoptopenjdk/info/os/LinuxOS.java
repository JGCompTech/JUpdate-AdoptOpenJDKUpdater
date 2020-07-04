package com.jgcomptech.adoptopenjdk.info.os;

import com.jgcomptech.adoptopenjdk.info.AbstractOperatingSystem;
import com.jgcomptech.adoptopenjdk.info.enums.OSList;
import com.jgcomptech.adoptopenjdk.osutils.ExecutingCommand;
import com.jgcomptech.adoptopenjdk.util.SingletonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class LinuxOS extends AbstractOperatingSystem {
    private final Logger logger = LoggerFactory.getLogger(LinuxOS.class);

    private LinuxOS() { }

    public static synchronized LinuxOS getInstance() {
        return SingletonUtils.getInstance(LinuxOS.class, LinuxOS::new);
    }

    @Override
    public String getName() {
        return "Linux";
    }

    @Override
    public OSList getNameEnum() {
        return OSList.Linux;
    }

    @Override
    public String getNameExpanded() {
        return OS_NAME;
    }

    @Override
    public String getVersion() {
        return "Unknown";
    }

    @SuppressWarnings("HardcodedFileSeparator")
    @Override
    public String getManufacturer() {
        return "GNU/Linux";
    }

    @Override
    public boolean isServer() {
        return false;
    }

    @Override
    public boolean is64BitOS() {
        try {
            return ExecutingCommand.runNewCmd("uname -m").getFirstResult().contains("64");
        } catch (final IOException | InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }
}
