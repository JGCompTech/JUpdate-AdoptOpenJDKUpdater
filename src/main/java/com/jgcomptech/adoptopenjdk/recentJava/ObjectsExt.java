package com.jgcomptech.adoptopenjdk.recentJava;

import static java.util.Objects.requireNonNull;

public class ObjectsExt {
    /**
     * Returns the first argument if it is non-{@code null} and
     * otherwise returns the non-{@code null} second argument.
     *
     * @param obj an object
     * @param defaultObj a non-{@code null} object to return if the first argument
     *                   is {@code null}
     * @param <T> the type of the reference
     * @return the first argument if it is non-{@code null} and
     *        otherwise the second argument if it is non-{@code null}
     * @throws NullPointerException if both {@code obj} is null and
     *        {@code defaultObj} is {@code null}
     * @since Java 9
     */
    public static <T> T requireNonNullElse(final T obj, final T defaultObj) {
        return (obj != null) ? obj : requireNonNull(defaultObj, "defaultObj");
    }
}
