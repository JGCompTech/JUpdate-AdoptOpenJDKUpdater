package com.jgcomptech.adoptopenjdk.utils;

import java.io.Serializable;

import static com.jgcomptech.adoptopenjdk.utils.Utils.checkArgumentNotNull;

/**
 * Provides mutable access to a {@link Boolean}.
 * @since 1.6.0
 */
public class BooleanValue implements Value<Boolean, BooleanValue>, Comparable<Boolean>, Serializable {
    /**
     * Required for serialization support.
     *
     * @see Serializable
     */
    private static final long serialVersionUID = -4830728138360036487L;

    protected Boolean value;

    /** Creates a new BooleanValue instance with the default value of false. */
    public BooleanValue() { value = Boolean.FALSE; }

    /**
     * Creates a new BooleanValue instance with the specified default value.
     * @param defaultValue the value to set
     */
    @SuppressWarnings("BooleanParameter")
    public BooleanValue(final boolean defaultValue) {
        value = defaultValue;
    }

    /**
     * Creates a new BooleanValue instance with the specified default value.
     * @param defaultValue the value to set
     * @throws IllegalArgumentException if specified default is null
     */
    public BooleanValue(final Boolean defaultValue) {
        checkArgumentNotNull(defaultValue, "Value cannot be null or empty!");
        value = defaultValue;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean get() {
        return value;
    }

    /**
     * {@inheritDoc}
     * @throws IllegalArgumentException if specified value is null
     */
    @Override
    public BooleanValue set(final Boolean value) {
        checkArgumentNotNull(value, "Value cannot be null or empty!");
        this.value = value;
        return this;
    }

    /**
     * Checks if the value equals true.
     * @return if the value equals true
     */
    public Boolean isTrue() { return value; }

    /**
     * Checks if the value equals false.
     * @return if the value equals false
     */
    public Boolean isFalse() { return !value; }

    /**
     * Sets the value to true.
     * @return this instance
     */
    public BooleanValue setTrue() {
        value = Boolean.TRUE;
        return this;
    }

    /**
     * Sets the value to false.
     * @return this instance
     */
    public BooleanValue setFalse() {
        value = Boolean.FALSE;
        return this;
    }

    /**
     * Runs the specified runnable if the value is true.
     * @param runnable the runnable to run
     * @return this instance
     * @throws IllegalArgumentException if runnable is null
     */
    public BooleanValue ifTrue(final Runnable runnable) {
        checkArgumentNotNull(runnable, "Runnable cannot be null or empty!");
        if(value) runnable.run();
        return this;
    }

    /**
     * Runs the specified runnable if the value is false.
     * @param runnable the runnable to run
     * @return this instance
     * @throws IllegalArgumentException if runnable is null
     */
    public BooleanValue ifFalse(final Runnable runnable) {
        checkArgumentNotNull(runnable, "Runnable cannot be null or empty!");
        if(!value) runnable.run();
        return this;
    }

    /**
     * Sets the value to the opposite of the current value.
     * @return this instance
     */
    public BooleanValue flip() {
        value = !value;
        return this;
    }

    @Override
    public int compareTo(final Boolean o) {
        return value.compareTo(o);
    }

    @Override
    public boolean equals(final Object o) {
        return o instanceof BooleanValue && value == ((BooleanValue) o).value;
    }

    @Override
    public int hashCode() {
        return (value ? Boolean.TRUE : Boolean.FALSE).hashCode();
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
