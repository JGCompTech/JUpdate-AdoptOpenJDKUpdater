package com.jgcomptech.adoptopenjdk.info;


import com.jgcomptech.adoptopenjdk.info.enums.OSList;

public interface OperatingSystem {
    String getName();
    OSList getNameEnum();
    String getNameExpanded();
    String getVersion() throws Exception;
    String getManufacturer();
    boolean isServer();
    boolean is32BitOS();
    boolean is64BitOS();
    String getBitString();
    int getBitNumber();
}
