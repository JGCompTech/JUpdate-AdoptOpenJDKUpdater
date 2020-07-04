package com.jgcomptech.adoptopenjdk.enums;

public enum DLStatus {
    DOWNLOADING("Downloading"),
    PAUSED("Paused"),
    COMPLETE("Complete"),
    CANCELLED("Cancelled"),
    ERROR("Error");

    final String value;

    DLStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
