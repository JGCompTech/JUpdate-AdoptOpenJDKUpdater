package com.jgcomptech.adoptopenjdk.util;

/**
 * Provides mutable access to a value.
 *
 * @param <T> the type to set and get
 * @param <V> the value of the object that implements
 *            this class to allow for method chaining
 * @since 1.6.0
 */
public interface Value<T, V> {
    /**
     * Returns the value.
     * @return the stored value
     */
    T get();
    /**
     * Sets the value.
     * @param value the value to store
     * @return this instance
     */
    V set(T value);
}
