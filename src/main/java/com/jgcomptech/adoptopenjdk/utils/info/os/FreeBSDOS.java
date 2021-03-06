package com.jgcomptech.adoptopenjdk.utils.info.os;

import com.jgcomptech.adoptopenjdk.utils.SingletonUtils;
import com.jgcomptech.adoptopenjdk.utils.info.AbstractOperatingSystem;
import com.jgcomptech.adoptopenjdk.utils.info.enums.OSList;
import com.jgcomptech.adoptopenjdk.utils.osutils.ExecutingCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class FreeBSDOS extends AbstractOperatingSystem {
    private final Logger logger = LoggerFactory.getLogger(FreeBSDOS.class);

    private FreeBSDOS() { }

    public static synchronized FreeBSDOS getInstance() {
        return SingletonUtils.getInstance(FreeBSDOS.class, FreeBSDOS::new);
    }

    @Override
    public String getName() {
        return "FreeBSD";
    }

    @Override
    public OSList getNameEnum() {
        return OSList.FreeBSD;
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
        return "Unix/BSD";
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
