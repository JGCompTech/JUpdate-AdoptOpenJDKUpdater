package com.jgcomptech.adoptopenjdk.utils.info;


import java.util.Locale;

public abstract class AbstractOperatingSystem implements OperatingSystem {
    protected final String OS_NAME = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);

    @Override
    public final boolean is32BitOS() {
        return !is64BitOS();
    }

    @Override
    public final String getBitString() {
        return is64BitOS() ? "64 bit" : "32 bit";
    }

    @Override
    public final int getBitNumber() {
        return is64BitOS() ? 64 : 32;
    }
}
