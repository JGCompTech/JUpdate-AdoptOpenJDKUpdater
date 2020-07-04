package com.jgcomptech.adoptopenjdk.utils;

/**
 * Provides mutable access to a {@link Number}.
 * @since 1.6.0
 */
public abstract class NumberValue<T extends Number, V extends NumberValue<T, V>>
        extends Number implements Comparable<V>, Value<T, V> {
    protected T value;

    /**
     * Increments the value.
     * @return this instance
     */
    public abstract V increment();

    /**
     * Increments this instance's value by 1; this method returns the value associated with the instance
     * immediately prior to the increment operation. This method is not thread safe.
     *
     * @return the value associated with the instance before it was incremented
     */
    public abstract T incrementAndGet();

    /**
     * Increments this instance's value by 1; this method returns the value associated with the instance
     * immediately after the increment operation. This method is not thread safe.
     *
     * @return the value associated with the instance after it is incremented
     */
    public abstract T getAndIncrement();

    /**
     * Decrements the value.
     * @return this instance
     */
    public abstract V decrement();

    /**
     * Decrements this instance's value by 1; this method returns the value associated with the instance
     * immediately prior to the decrement operation. This method is not thread safe.
     *
     * @return the value associated with the instance before it was decremented
     */
    public abstract T decrementAndGet();

    /**
     * Decrements this instance's value by 1; this method returns the value associated with the instance
     * immediately after the decrement operation. This method is not thread safe.
     *
     * @return the value associated with the instance after it is decremented
     */
    public abstract T getAndDecrement();

    /**
     * Adds a value to the value of this instance.
     *
     * @param operand  the value to add, not null
     * @throws IllegalArgumentException if the object is null
     * @return this instance
     */
    public abstract V add(final Number operand);

    /**
     * Increments this instance's value by {@code operand}; this method returns the value associated with the instance
     * immediately after the addition operation. This method is not thread safe.
     *
     * @param operand the quantity to add, not null
     * @throws IllegalArgumentException if {@code operand} is null
     * @return the value associated with this instance after adding the operand
     */
    public abstract T addAndGet(final Number operand);

    /**
     * Increments this instance's value by {@code operand}; this method returns the value associated with the instance
     * immediately prior to the addition operation. This method is not thread safe.
     *
     * @param operand the quantity to add, not null
     * @throws IllegalArgumentException if {@code operand} is null
     * @return the value associated with this instance immediately before adding the operand
     */
    public abstract T getAndAdd(final Number operand);

    /**
     * Subtracts a value from the value of this instance.
     *
     * @param operand  the value to subtract, not null
     * @throws IllegalArgumentException if the object is null
     * @return this instance
     */
    public abstract V subtract(final Number operand);

    /**
     * Decrements this instance's value by {@code operand}; this method returns the value associated with the instance
     * immediately after the subtraction operation. This method is not thread safe.
     *
     * @param operand the quantity to subtract, not null
     * @throws IllegalArgumentException if {@code operand} is null
     * @return the value associated with this instance after subtracting the operand
     */
    public abstract T subtractAndGet(final Number operand);

    /**
     * Decrements this instance's value by {@code operand}; this method returns the value associated with the instance
     * immediately prior to the subtraction operation. This method is not thread safe.
     *
     * @param operand the quantity to subtract, not null
     * @throws IllegalArgumentException if {@code operand} is null
     * @return the value associated with this instance immediately before subtracting the operand
     */
    public abstract T getAndSubtract(final Number operand);

    /**
     * Returns {@code true} if the value is positive,
     * {@code false} otherwise.
     * @return  {@code true} if the value is positive;
     * {@code false} otherwise.
     */
    public abstract boolean isPositive();

    /**
     * Returns {@code true} if the value is negative,
     * {@code false} otherwise.
     * @return  {@code true} if the value is negative;
     * {@code false} otherwise.
     */
    public abstract boolean isNegative();

    /**
     * Returns {@code true} if the value is equal to zero,
     * {@code false} otherwise.
     * @return {@code true} if the value is equal to zero;
     * {@code false} otherwise.
     */
    public abstract boolean isZero();

    /**
     * Returns {@code true} if the value is not equal to zero,
     * {@code false} otherwise.
     * @return {@code true} if the value is not equal to zero;
     * {@code false} otherwise.
     */
    public final boolean isNotZero() { return !isZero(); }
    
    /**
     * Returns {@code true} if the value is equal to the specified number,
     * {@code false} otherwise.
     * @param number the number to check
     * @return {@code true} if the value is equal to the specified number;
     * {@code false} otherwise.
     * @throws IllegalArgumentException if {@code number} is null
     */
    public abstract boolean isEqualTo(final Number number);
    
    /**
     * Returns {@code true} if the value is not equal to the specified number,
     * {@code false} otherwise.
     * @param number the number to check
     * @return {@code true} if the value is not equal to the specified number;
     * {@code false} otherwise.
     * @throws IllegalArgumentException if {@code number} is null
     */
    public abstract boolean isNotEqualTo(final Number number);
    
    /**
     * Returns {@code true} if the value is less than
     * or equal to the specified number, {@code false} otherwise.
     * @param number the number to check
     * @return {@code true} if the value is less than
     * or equal to the specified number, {@code false} otherwise.
     * @throws IllegalArgumentException if {@code number} is null
     */
    public abstract boolean isLessThanOrEqualTo(final Number number);
    
    /**
     * Returns {@code true} if the value is greater than
     * or equal to the specified number, {@code false} otherwise.
     * @param number the number to check
     * @return {@code true} if the value is greater than
     * or equal to the specified number, {@code false} otherwise.
     * @throws IllegalArgumentException if {@code number} is null
     */
    public abstract boolean isGreaterThanOrEqualTo(final Number number);
    
    /**
     * Returns {@code true} if the value is less than
     * the specified number, {@code false} otherwise.
     * @param number the number to check
     * @return {@code true} if the value is less than
     * the specified number, {@code false} otherwise.
     * @throws IllegalArgumentException if {@code number} is null
     */
    public abstract boolean isLessThan(final Number number);
    
    /**
     * Returns {@code true} if the value is greater than
     * the specified number, {@code false} otherwise.
     * @param number the number to check
     * @return {@code true} if the value is greater than
     * the specified number, {@code false} otherwise.
     * @throws IllegalArgumentException if {@code number} is null
     */
    public abstract boolean isGreaterThan(final Number number);

    /**
     * {@inheritDoc}
     */
    @Override
    public int intValue() {
        return value.intValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long longValue() {
        return value.longValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float floatValue() {
        return value.floatValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double doubleValue() {
        return value.doubleValue();
    }

    @Override
    public abstract boolean equals(final Object o);

    @Override
    public abstract int hashCode();

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public abstract int compareTo(final V other);

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract V set(final Number value);
}
