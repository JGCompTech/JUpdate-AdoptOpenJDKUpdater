package com.jgcomptech.adoptopenjdk;

/** A list of all currently supported Java releases. */
public enum JavaName {
    /** Java 8. */
    Java8("java8"),
    /** Java 9. */
    Java9("java9"),
    /** Java 10. */
    Java10("java10"),
    /** Java 11. */
    Java11("java11"),
    /** Java 12. */
    Java12("java12"),
    /** Java 13. */
    Java13("java13"),
    /** Java 14. */
    Java14("java14"),
    /** Java 15. */
    Java15("java15"),
    ;

    private final String name;

    JavaName(final String name) {
        this.name = name;
    }

    /**
     * Returns the name as a string.
     * @return the name as a string
     */
    public String getName() {
        return name;
    }
}
