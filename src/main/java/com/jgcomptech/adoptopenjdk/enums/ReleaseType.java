package com.jgcomptech.adoptopenjdk.enums;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ReleaseType {
    NONE(""),
    JDK("jdk"),
    JRE("jre"),
    TestImage("testimage");

    private static final Pattern OPENJDK = Pattern.compile("openjdk", Pattern.LITERAL);
    final String value;
    ReleaseType(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static ReleaseType parseFromName(final String name) {
        final String newName = OPENJDK.matcher(name.toLowerCase(Locale.getDefault()))
                .replaceAll(Matcher.quoteReplacement(""));

        if(newName.contains(JDK.value)) return JDK;
        if(newName.contains(JRE.value)) return JRE;
        if(newName.contains(TestImage.value)) return TestImage;
        return NONE;
    }

    @Override
    public String toString() {
        return value.toUpperCase(Locale.getDefault());
    }
}
