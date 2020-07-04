package com.jgcomptech.adoptopenjdk.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.RoundingMode;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

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
     * @apiNote This method calls {@link StringUtils#isNotBlank(CharSequence)} on the specified reference.
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

    public static JsonArray processJSONAsArray(final String url) throws IOException {
        return processJSONAsArray(new URL(url));
    }

    public static JsonArray processJSONAsArray(final URL url) throws IOException {
        final URLConnection request = url.openConnection();
        request.connect();

        try(final InputStreamReader isr = new InputStreamReader((InputStream) request.getContent())) {
            return JsonParser.parseReader(isr).getAsJsonArray();
        }
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
