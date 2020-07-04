package com.jgcomptech.adoptopenjdk.utils;

import static com.jgcomptech.adoptopenjdk.utils.Utils.checkArgumentNotNull;
import static com.jgcomptech.adoptopenjdk.utils.Utils.checkArgumentNotNullOrEmpty;

/**
 * Provides mutable access to an {@link Integer}.
 * @since 1.6.0
 */
public class IntegerValue extends NumberValue<Integer, IntegerValue> {
    /**
     * Required for serialization support.
     *
     * @see java.io.Serializable
     */
    private static final long serialVersionUID = 512176391864L;

    /** Creates a new IntegerValue instance with the default value of 0. */
    public IntegerValue() { value = 0; }

    /**
     * Creates a new IntegerValue instance with the specified default int value.
     * @param defaultValue the value to set
     */
    public IntegerValue(final int defaultValue) {
        value = defaultValue;
    }

    /**
     * Creates a new IntegerValue instance with the specified number value.
     * @param defaultValue the value to set
     * @throws IllegalArgumentException if specified default value is null
     */
    public IntegerValue(final Number defaultValue) {
        checkArgumentNotNull(defaultValue, "Default value cannot be null!");
        value = defaultValue.intValue();
    }

    /**
     * Creates a new IntegerValue instance with the specified default string value.
     * @param defaultValue the value to set
     * @throws IllegalArgumentException if specified default value is null or empty
     */
    public IntegerValue(final String defaultValue) {
        checkArgumentNotNullOrEmpty(defaultValue, "Default value cannot be null or empty!");
        value = Integer.parseInt(defaultValue);
    }

    /**
     * {@inheritDoc}
     * @throws ArithmeticException if the result overflows an int
     */
    @Override
    public IntegerValue increment() {
        value = Math.incrementExact(value);
        return this;
    }

    /**
     * {@inheritDoc}
     * @throws ArithmeticException if the result overflows an int
     */
    @Override
    public Integer incrementAndGet() {
        value = Math.incrementExact(value);
        return value;
    }

    /**
     * {@inheritDoc}
     * @throws ArithmeticException if the result overflows an int
     */
    @Override
    public Integer getAndIncrement() {
        final Integer last = value;
        value = Math.incrementExact(value);
        return last;
    }

    /**
     * {@inheritDoc}
     * @throws ArithmeticException if the result overflows an int
     */
    @Override
    public IntegerValue decrement() {
        value = Math.decrementExact(value);
        return this;
    }

    /**
     * {@inheritDoc}
     * @throws ArithmeticException if the result overflows an int
     */
    @Override
    public Integer decrementAndGet() {
        value = Math.decrementExact(value);
        return value;
    }

    /**
     * {@inheritDoc}
     * @throws ArithmeticException if the result overflows an int
     */
    @Override
    public Integer getAndDecrement() {
        final Integer last = value;
        value = Math.decrementExact(value);
        return last;
    }

    /**
     * {@inheritDoc}
     * @throws ArithmeticException if the result overflows an int
     */
    @Override
    public IntegerValue add(final Number num) {
        value = Math.addExact(value, num.intValue());
        return this;
    }

    /**
     * {@inheritDoc}
     * @throws ArithmeticException if the result overflows an int
     */
    @Override
    public Integer addAndGet(final Number operand) {
        value = Math.addExact(value, operand.intValue());
        return value;
    }

    /**
     * {@inheritDoc}
     * @throws ArithmeticException if the result overflows an int
     */
    @Override
    public Integer getAndAdd(final Number operand) {
        final Integer last = value;
        value = Math.addExact(value, operand.intValue());
        return last;
    }

    /**
     * {@inheritDoc}
     * @throws ArithmeticException if the result overflows an int
     */
    @Override
    public IntegerValue subtract(final Number num) {
        value = Math.subtractExact(value, num.intValue());
        return this;
    }

    /**
     * {@inheritDoc}
     * @throws ArithmeticException if the result overflows an int
     */
    @Override
    public Integer subtractAndGet(final Number operand) {
        value = Math.subtractExact(value, operand.intValue());
        return value;
    }

    /**
     * {@inheritDoc}
     * @throws ArithmeticException if the result overflows an int
     */
    @Override
    public Integer getAndSubtract(final Number operand) {
        final Integer last = value;
        value = Math.subtractExact(value, operand.intValue());
        return last;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isPositive() {
        return Integer.signum(value) > 0;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isNegative() {
        return Integer.signum(value) < 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isZero() {
        return Integer.signum(value) == 0;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEqualTo(final Number number) {
        return value == number.intValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isNotEqualTo(final Number number) {
        return value != number.intValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isLessThanOrEqualTo(final Number number) {
        return value <= number.intValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isGreaterThanOrEqualTo(final Number number) {
        return value >= number.intValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isLessThan(final Number number) {
        return value < number.intValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isGreaterThan(final Number number) {
        return value > number.intValue();
    }

    @Override
    public int compareTo(final IntegerValue other) {
        return Integer.compare(value, other.value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer get() {
        return value;
    }

    /**
     * {@inheritDoc}
     * @throws IllegalArgumentException if specified value is null
     */
    @Override
    public IntegerValue set(final Integer value) {
        checkArgumentNotNull(value, "Value cannot be null!");
        this.value = value;
        return this;
    }

    /**
     * {@inheritDoc}
     * @throws IllegalArgumentException if specified value is null
     */
    @Override
    public IntegerValue set(final Number value) {
        checkArgumentNotNull(value, "Value cannot be null!");
        this.value = value.intValue();
        return this;
    }

    @Override
    public boolean equals(final Object o) {
        return o instanceof IntegerValue && value == ((IntegerValue) o).intValue();
    }

    @Override
    public int hashCode() {
        return value;
    }
}
