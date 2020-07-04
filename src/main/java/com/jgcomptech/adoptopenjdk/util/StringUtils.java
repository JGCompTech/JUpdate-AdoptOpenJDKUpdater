package com.jgcomptech.adoptopenjdk.util;

import org.apache.commons.lang3.CharUtils;

import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.stream.IntStream;

import static com.jgcomptech.adoptopenjdk.util.Literals.INPUT_CANNOT_BE_NULL;
import static com.jgcomptech.adoptopenjdk.util.Literals.LOCALE_CANNOT_BE_NULL;
import static com.jgcomptech.adoptopenjdk.util.Utils.checkArgumentNotNull;

public class StringUtils {
    /**
     * Checks if the specified String is a number that may be negative or
     * contain a decimal point using the default locale.
     * <p>
     *     This method is more robust then most isNumeric methods
     *     in other libraries because it checks for a maximum of one
     *     minus sign and checks for a maximum of one decimal point.
     *     It also does not use pattern matching or number parsing
     *     with exception checking thus increasing speed.
     *
     *     Examples:
     *     StringUtils.isNumeric("1"); - true
     *     StringUtils.isNumeric("1.5"); - true
     *     StringUtils.isNumeric("1.556"); - true
     *     StringUtils.isNumeric("1..5"); - false
     *     StringUtils.isNumeric("1.5D"); - false
     *     StringUtils.isNumeric("1A.5"); - false
     *
     *     StringUtils.isNumeric("-1"); - true
     *     StringUtils.isNumeric("-1.5"); - true
     *     StringUtils.isNumeric("-1.556"); - true
     *     StringUtils.isNumeric("-1..5"); - false
     *     StringUtils.isNumeric("-1.5D"); - false
     *     StringUtils.isNumeric("-1A.5"); - false
     *
     *     StringUtils.isNumeric("-"); - false
     *     StringUtils.isNumeric("--1"); - false
     * </p>
     * @param input string to check
     * @return true if the specified String is a number
     * @since 1.6.0
     */
    public static boolean isNumeric(final CharSequence input) {
        return isNumeric(input, Locale.getDefault(Locale.Category.FORMAT));
    }

    /**
     * Checks if the specified String is a number that may be negative or
     * contain a decimal point using the specified locale.
     * <p>
     *     This method is more robust then most isNumeric methods
     *     in other libraries because it checks for a maximum of one
     *     minus sign and checks for a maximum of one decimal point.
     *     It also does not use pattern matching or number parsing
     *     with exception checking thus increasing speed.
     *
     *     Examples:
     *     StringUtils.isNumeric("1"); - true
     *     StringUtils.isNumeric("1.5"); - true
     *     StringUtils.isNumeric("1.556"); - true
     *     StringUtils.isNumeric("1..5"); - false
     *     StringUtils.isNumeric("1.5D"); - false
     *     StringUtils.isNumeric("1A.5"); - false
     *
     *     StringUtils.isNumeric("-1"); - true
     *     StringUtils.isNumeric("-1.5"); - true
     *     StringUtils.isNumeric("-1.556"); - true
     *     StringUtils.isNumeric("-1..5"); - false
     *     StringUtils.isNumeric("-1.5D"); - false
     *     StringUtils.isNumeric("-1A.5"); - false
     *
     *     StringUtils.isNumeric("-"); - false
     *     StringUtils.isNumeric("--1"); - false
     * </p>
     * @param input string to check
     * @param locale the locale to use
     * @return true if the specified String is a number
     * @throws IllegalArgumentException if locale is null
     */
    public static boolean isNumeric(final CharSequence input, final Locale locale) {
        checkArgumentNotNull(locale, Literals.cannotBeNull("locale"));
        //Check for null or blank string
        if(isBlank(input)) return false;

        //Retrieve the minus sign and decimal separator characters from the current Locale
        final char localeMinusSign = DecimalFormatSymbols.getInstance(locale).getMinusSign();
        final char localeDecimalSeparator = DecimalFormatSymbols.getInstance(locale).getDecimalSeparator();

        //Check if first character is a minus sign
        final boolean isNegative = input.charAt(0) == localeMinusSign;
        //Check if string is not just a minus sign
        if (isNegative && input.length() == 1) return false;

        boolean isDecimalSeparatorFound = false;

        //If the string has a minus sign ignore the first character
        final int startCharIndex = isNegative ? 1 : 0;

        //Check if each character is a number or a decimal separator
        //and make sure string only has a maximum of one decimal separator
        for (int i = startCharIndex; i < input.length(); i++) {
            if(!Character.isDigit(input.charAt(i))) {
                if(input.charAt(i) == localeDecimalSeparator && !isDecimalSeparatorFound) {
                    isDecimalSeparatorFound = true;
                } else return false;
            }
        }
        return true;
    }

    /**
     * <p>Checks if a CharSequence is empty ("") or null.</p>
     *
     * <pre>
     * StringUtils.isEmpty(null)      = true
     * StringUtils.isEmpty("")        = true
     * StringUtils.isEmpty(" ")       = false
     * StringUtils.isEmpty("bob")     = false
     * StringUtils.isEmpty("  bob  ") = false
     * </pre>
     *
     * <p>NOTE: This method changed in Lang version 2.0.
     * It no longer trims the CharSequence.
     * That functionality is available in isBlank().</p>
     *
     * @param cs  the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is empty or null
     */
    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    /**
     * <p>
     * Unwraps a given string from a character.
     * </p>
     *
     * <pre>
     * StringUtils.unwrap(null, null)         = null
     * StringUtils.unwrap(null, '\0')         = null
     * StringUtils.unwrap(null, '1')          = null
     * StringUtils.unwrap("\'abc\'", '\'')    = "abc"
     * StringUtils.unwrap("AABabcBAA", 'A')  = "ABabcBA"
     * StringUtils.unwrap("A", '#')           = "A"
     * StringUtils.unwrap("#A", '#')          = "#A"
     * StringUtils.unwrap("A#", '#')          = "A#"
     * </pre>
     *
     * @param str
     *          the String to be unwrapped, can be null
     * @param wrapChar
     *          the character used to unwrap
     * @return unwrapped String or the original string
     *          if it is not quoted properly with the wrapChar
     */
    public static String unwrap(final String str, final char wrapChar) {
        if (isEmpty(str) || wrapChar == CharUtils.NUL) {
            return str;
        }

        if (str.charAt(0) == wrapChar && str.charAt(str.length() - 1) == wrapChar) {
            final int startIndex = 0;
            final int endIndex = str.length() - 1;
            return str.substring(startIndex + 1, endIndex);
        }

        return str;
    }

    /**
     * Returns string with first char uppercase.
     * @apiNote Since 2.0.0 removed the empty check on the input
     *          that threw an IllegalArgumentException because
     *          this method already checked if the input length
     *          was greater than 1 and returns an empty string
     *          if the input is empty.
     *
     * @param input string to edit
     * @return string with first char uppercase
     * @throws IllegalArgumentException if Input is null
     */

    public static String uppercaseFirst(final String input) {
        checkArgumentNotNull(input, INPUT_CANNOT_BE_NULL);
        return input.length() > 1
                ? input.substring(0, 1).toUpperCase(Locale.getDefault()) + input.substring(1)
                : input.toUpperCase(Locale.getDefault());
    }

    /**
     * Returns string with first char uppercase.
     * @apiNote Since 2.0.0 removed the empty check on the input
     *          that threw an IllegalArgumentException because
     *          this method already checked if the input length
     *          was greater than 1 and returns an empty string
     *          if the input is empty.
     *
     * @param input string to edit
     * @param locale use the case transformation rules for this locale
     * @return string with first char uppercase
     * @throws IllegalArgumentException if Input or Locale is null
     */

    public static String uppercaseFirst(final String input, final Locale locale) {
        checkArgumentNotNull(input, INPUT_CANNOT_BE_NULL);
        checkArgumentNotNull(locale, LOCALE_CANNOT_BE_NULL);
        return input.length() > 1
                ? input.substring(0, 1).toUpperCase(locale) + input.substring(1)
                : input.toUpperCase(locale);
    }

    /**
     * <p>Checks if a CharSequence is empty (""), null or whitespace only.</p>
     * <p>Whitespace is defined by {@link Character#isWhitespace(char)}.</p>
     *
     * @param cs  the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is null, empty or whitespace only
     */
    public static boolean isBlank(final CharSequence cs) {
        return (cs == null || cs.length() == 0)
                || IntStream.range(0, cs.length())
                .allMatch(i -> Character.isWhitespace(cs.charAt(i)));
    }
}
