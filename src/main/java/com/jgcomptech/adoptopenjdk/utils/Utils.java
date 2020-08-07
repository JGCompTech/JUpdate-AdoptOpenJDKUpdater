package com.jgcomptech.adoptopenjdk.utils;

import org.slf4j.Logger;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Locale;

import static com.jgcomptech.adoptopenjdk.utils.Literals.LOCALE_CANNOT_BE_NULL;
import static org.apache.commons.lang3.StringUtils.*;

public final class Utils {
    private Utils() { }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     * @param expression a boolean expression
     * @throws IllegalArgumentException if {@code expression} is false
     */
    @SuppressWarnings("BooleanParameter")
    public static void checkArgument(final boolean expression)
            throws IllegalArgumentException {
        if (!expression) throw new IllegalArgumentException("Invalid argument specified!");
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     * @param expression a boolean expression
     * @param errorMessage the exception message to use if the check fails; will be converted to a
     *     string using {@link String#valueOf(Object)}
     * @throws IllegalArgumentException if {@code expression} is false
     */
    @SuppressWarnings("BooleanParameter")
    public static void checkArgument(final boolean expression, final Object errorMessage)
            throws IllegalArgumentException {
        if (!expression) throw new IllegalArgumentException(String.valueOf(errorMessage));
    }

    /**
     * Ensures that {@code reference} is non-null,
     * throwing an {@code IllegalArgumentException} with a custom message otherwise.
     * @param <T> the type of the reference
     * @param reference the object to verify
     * @param errorMessage the exception message to use if the check fails; will be converted to a
     *          string using {@link String#valueOf(Object)}
     * @return {@code reference}, guaranteed to be non-null, for convenience
     * @throws IllegalArgumentException if {@code reference} is {@code null}
     */
    public static <T> T checkArgumentNotNull(final T reference, final Object errorMessage)
            throws IllegalArgumentException {
        checkArgument(reference != null, errorMessage);
        return reference;
    }

    /**
     * Ensures that {@code reference} is non-null and non-empty,
     * throwing an {@code IllegalArgumentException} with a custom message otherwise.
     * @apiNote This method calls {@link org.apache.commons.lang3.StringUtils#isNotBlank(CharSequence)} on the specified reference.
     * @param reference the object to verify
     * @param errorMessage the exception message to use if the check fails; will be converted to a
     *          string using {@link String#valueOf(Object)}
     * @return {@code reference}, guaranteed to be non-null and non-empty, for convenience
     * @throws IllegalArgumentException if {@code reference} is {@code null} or empty
     */
    public static String checkArgumentNotNullOrEmpty(final String reference, final Object errorMessage)
            throws IllegalArgumentException {
        checkArgument(isNotBlank(reference), errorMessage);
        return reference;
    }

    /**
     * Checks if a string can be converted to a Boolean.
     * @param input string to check
     * @return true if string matches a boolean, false if does not match or is null
     */
    public static boolean isBoolean(final String input) {
        return isBoolean(input, Locale.getDefault(Locale.Category.FORMAT));
    }

    /**
     * Checks if a string can be converted to a Boolean.
     * @apiNote The following strings are considered true boolean values:
     *          "true", "t", "yes", "y", "1", "succeeded", "succeed", "enabled".
     *          The following strings are considered false boolean values:
     *          "false", "f", "no", "n", "0", "-1", "failed", "fail", "disabled".
     * @param input string to check
     * @param locale the locale to use
     * @return true if string matches a boolean, false if does not match or is null
     * @throws IllegalArgumentException if locale is null
     */
    public static boolean isBoolean(final String input, final Locale locale) {
        if(input == null) return false;
        checkArgumentNotNull(locale, LOCALE_CANNOT_BE_NULL);
        final String value = strip(input.toLowerCase(locale));
        switch(value)
        {
            case "true":
            case "t":
            case "yes":
            case "y":
            case "1":
            case "succeeded":
            case "succeed":
            case "enabled":
            case "false":
            case "f":
            case "no":
            case "n":
            case "0":
            case "-1":
            case "failed":
            case "fail":
            case "disabled":
                return true;
            default:
                return false;
        }
    }

    /**
     * <p>Strips whitespace from the start and end of a String.</p>
     *
     * <p>A {@code null} input String returns {@code null}.</p>
     *
     * @param str  the String to remove whitespace from, may be null
     * @return the stripped String, {@code null} if null String input
     */
    public static String strip(final String str) {
        return strip(str, null);
    }

    /**
     * <p>Strips any of a set of characters from the start and end of a String.
     * This is similar to {@link String#trim()} but allows the characters
     * to be stripped to be controlled.</p>
     *
     * <p>A {@code null} input String returns {@code null}.
     * An empty string ("") input returns the empty string.</p>
     *
     * <p>If the stripChars String is {@code null}, whitespace is
     * stripped as defined by {@link Character#isWhitespace(char)}.
     * Alternatively use {@link #strip(String)}.</p>
     *
     * @param str  the String to remove characters from, may be null
     * @param stripChars  the characters to remove, null treated as whitespace
     * @return the stripped String, {@code null} if null String input
     */
    public static String strip(String str, final String stripChars) {
        if (str.isEmpty()) return str;
        str = stripStart(str, stripChars);
        return stripEnd(str, stripChars);
    }

    /**
     * Returns the conversion from bytes to the correct version (1024 bytes = 1 KB) as a string.
     * @param input number to convert to a readable string
     * @return the specified number converted to a readable string
     * @since 1.2
     */
    public static String convertBytesToString(final Number input) {
        final DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.DOWN);
        final double factor = 1024.0d;
        final String suffix;
        double newNum = input.doubleValue();
        if(newNum >= factor) {
            newNum /= factor;
            if(newNum >= factor) {
                newNum /= factor;
                if(newNum >= factor) {
                    newNum /= factor;
                    if(newNum >= factor) {
                        newNum /= factor;
                        suffix = " TB";
                    } else suffix = " GB";
                } else suffix = " MB";
            } else suffix = " KB";
        } else suffix = " Bytes";
        return df.format(newNum) + suffix;
    }

    /**
     * Attempts to parse a string to an int. If it fails, returns the default
     *
     * @param s
     *            The string to parse
     * @param defaultInt
     *            The value to return if parsing fails
     * @return The parsed int, or the default if parsing failed
     */
    public static int parseIntOrDefault(final String s, final int defaultInt, final Logger logger) {
        try {
            return Integer.parseInt(s);
        } catch (final NumberFormatException e) {
            logger.error(s + " didn't parse. Returning default. " + defaultInt, s, e);
            return defaultInt;
        }
    }

    /**
     * Attempts to parse a string to a long. If it fails, returns the default
     *
     * @param s
     *            The string to parse
     * @param defaultLong
     *            The value to return if parsing fails
     * @return The parsed long, or the default if parsing failed
     */
    public static long parseLongOrDefault(final String s, final long defaultLong, final Logger logger) {
        try {
            return Long.parseLong(s);
        } catch (NumberFormatException e) {
            logger.error(s + " didn't parse. Returning default. " + defaultLong, s, e);
            return defaultLong;
        }
    }
}
