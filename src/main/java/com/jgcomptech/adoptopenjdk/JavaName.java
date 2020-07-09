package com.jgcomptech.adoptopenjdk;

public enum JavaName {
    Java8("java8"),
    Java9("java9"),
    Java10("java10"),
    Java11("java11"),
    Java12("java12"),
    Java13("java13"),
    Java14("java14"),
    Java15("java15"),
    ;

    final String name;

    JavaName(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
