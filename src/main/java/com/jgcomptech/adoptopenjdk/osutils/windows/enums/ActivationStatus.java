package com.jgcomptech.adoptopenjdk.osutils.windows.enums;

import com.jgcomptech.adoptopenjdk.info.enums.BaseEnum;
import com.jgcomptech.adoptopenjdk.info.os.WindowsOSEx;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Arrays;

/** A list of Activation statuses that are the result of the methods in the {@link WindowsOSEx.Activation} class. */
public enum ActivationStatus implements BaseEnum {
    Unlicensed(0, "Unlicensed"),
    Licensed(1, "Licensed"),
    OutOfBoxGrace(2, "Out-Of-Box Grace"),
    OutOfToleranceGrace(3, "Out-Of-Tolerance Grace"),
    NonGenuineGrace(4, "Non Genuine Grace"),
    Notification(5, "Notification"),
    ExtendedGrace(6, "Extended Grace"),
    Unknown(-1, "Unknown License Status");

    final int value;
    final String fullName;

    ActivationStatus(final int value, final String fullName) {
        this.value = value;
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    public static ActivationStatus parse(final int value) {
        return Arrays.stream(ActivationStatus.values())
                .filter(s -> s.value == value)
                .findFirst()
                .orElse(Unknown);
    }

    public static ActivationStatus parseString(final String name) {
        return Arrays.stream(ActivationStatus.values())
                .filter(s -> s.fullName.equalsIgnoreCase(name))
                .findFirst()
                .orElse(Unknown);
    }

    @Override
    public int getValue() { return value; }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("value", value)
                .append("fullName", fullName)
                .toString();
    }
}
