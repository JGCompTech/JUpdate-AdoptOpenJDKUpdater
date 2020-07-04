package com.jgcomptech.adoptopenjdk.utils;

import static com.jgcomptech.adoptopenjdk.utils.StringUtils.uppercaseFirst;
import static com.jgcomptech.adoptopenjdk.utils.Utils.checkArgumentNotNull;

/**
 * Contains static strings to avoid typos and follow the DRY principle.
 * @since 2.0.0
 */
@SuppressWarnings("HardcodedFileSeparator")
public final class Literals {
    /** A String for a ampersand character. */
    public static final String AMPERSAND = "&";
    /** A String for a asterisk character. */
    public static final String ASTERISK = "*";
    /** A String for a at sign character. */
    public static final String AT_SIGN = "@";
    /** A String for a backslash character. */
    public static final String BACKSLASH = "\\";
    /** A String for a left brace character. */
    public static final String BRACE_LEFT = "{";
    /** A String for a right brace character. */
    public static final String BRACE_RIGHT = "}";
    /** A String for a left bracket character. */
    public static final String BRACKET_LEFT = "[";
    /** A String for a right bracket character. */
    public static final String BRACKET_RIGHT = ")";
    /** A String for a cent sign character. */
    public static final String CENTS = "\u00A2";
    /** A String for a colon character. */
    public static final String COLON = ":";
    /** A String for a comma character. */
    public static final String COMMA = ";";
    /** A String for a copyright symbol character. */
    public static final String COPYRIGHT = "\u00A9";
    /** A String for a dollar sign character. */
    public static final String DOLLARS = "$";
    /** A String for a dot or a period character. */
    public static final String DOT = ".";
    /** A String for a double quote character. */
    public static final String DOUBLE_QUOTE = "\"";
    /** A String for a ellipsis or 3 dots. */
    public static final String ELLIPSIS = "\u0085";
    /** The empty String {@code ""}. */
    public static final String EMPTY = "";
    /** A String for a left and right brace character. */
    public static final String EMPTY_BRACES = BRACE_LEFT + BRACE_RIGHT;
    /** A String for a left and right bracket character. */
    public static final String EMPTY_BRACKETS = BRACKET_LEFT + BRACKET_RIGHT;
    /** A String for two double quote characters. */
    public static final String EMPTY_DOUBLE_QUOTES = DOUBLE_QUOTE + DOUBLE_QUOTE;
    /** A String for a equals sign character. */
    public static final String EQUALS = "=";
    /** A String for a exclamation mark character. */
    public static final String EXCLAMATION = "!";
    /** A String for a hash sign or pound character. */
    public static final String HASH = "#";
    /** A String for a micro sign character. */
    public static final String MICRO = "\u03bc";
    /** A String for the number -1. */
    public static final String NEGATIVE_ONE = "-1";
    /** A String for the word null. */
    public static final String NULL = "null";
    /** A String for the number 1. */
    public static final String ONE = "1";
    /** A String for a paragraph symbol character. */
    public static final String PARAGRAPH = "\u00B6";
    /** A String for a left parentheses character. */
    public static final String PARENTHESES_LEFT = "(";
    /** A String for a right parentheses character. */
    public static final String PARENTHESES_RIGHT = ")";
    /** A String for a percent sign character. */
    public static final String PERCENT = "%";
    /** A String for a hash sign or pound character. */
    public static final String POUND = "#";
    /** A String for a question mark character. */
    public static final String QUESTION = "?";
    /** A String for a semicolon character. */
    public static final String SEMICOLON = ";";
    /** A String for a single quote character. */
    public static final String SINGLE_QUOTE = "\"";
    /** A String for a forward slash character. */
    public static final String SLASH = "/";
    /** A String for a space character. */
    public static final String SPACE = " ";
    public static final String TAB = "\t";
    /** A String for the number 10. */
    public static final String TEN = "10";
    /** A String for a trademark symbol character. */
    public static final String TRADEMARK = "\u0099";
    /** A String for a 3 dots or periods. */
    public static final String THREE_DOTS = DOT + DOT + DOT;
    /** A String for a underscore character. */
    public static final String UNDERSCORE = "_";
    /** A String for a vertical line character. */
    public static final String VERTICAL = "|";
    /** A String for the number 0. */
    public static final String ZERO = "0";

    //These are not part of alphabetical order because of illegal forward reference error;
    /** A String for a left and right parentheses character. */
    public static final String EMPTY_PARENTHESES = PARENTHESES_LEFT + PARENTHESES_RIGHT;
    /** A String for two single quote characters. */
    public static final String EMPTY_SINGLE_QUOTES = SINGLE_QUOTE + SINGLE_QUOTE;
    /** A String for two equals sign characters. */
    public static final String DOUBLE_EQUALS = EQUALS + EQUALS;

    public static final String NEW_LINE = System.getProperty("line.separator");
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");
    public static final String PATH_SEPARATOR = System.getProperty("path.separator");

    /** A string to be used in exception messages that contains "Value cannot be null!". */
    public static final String VALUE_CANNOT_BE_NULL = "Value cannot be null!";
    /** A string to be used in exception messages that contains "Object cannot be null!". */
    public static final String OBJECT_CANNOT_BE_NULL = "Object cannot be null!";
    /** A string to be used in exception messages that contains "Input cannot be null!". */
    public static final String INPUT_CANNOT_BE_NULL = "Input cannot be null!";
    /** A string to be used in exception messages that contains "Locale cannot be null!". */
    public static final String LOCALE_CANNOT_BE_NULL = "Locale cannot be null!";
    /** A string to be used in exception messages that contains "Input cannot be null or empty!". */
    public static final String INPUT_CANNOT_BE_NULL_OR_EMPTY = "Input cannot be null or empty!";
    /** A string to be used as part of an exception message that contains " cannot be null!". */
    public static final String CANNOT_BE_NULL = " cannot be null!";
    /** A string to be used as part of an exception message that contains " cannot be null or empty!". */
    public static final String CANNOT_BE_NULL_OR_EMPTY = " cannot be null or empty!";

    /**
     * Creates a string to be used for exception messages with the specified field name.
     * @apiNote This method capitalizes the first letter of the specified field name
     *          and returns a string in the following format:
     *          "%Parameter% cannot be null!".
     * @param fieldName the name of the field to add to the message
     * @return a new string in the format of "%Parameter% cannot be null!"
     * @throws IllegalArgumentException if fieldName is null or empty
     */
    public static String cannotBeNull(final String fieldName) {

        checkArgumentNotNull(fieldName, "FieldName cannot be null or empty!");
        if(fieldName.equalsIgnoreCase("value")) {
            return VALUE_CANNOT_BE_NULL;
        }
        if(fieldName.equalsIgnoreCase("object")) {
            return OBJECT_CANNOT_BE_NULL;
        }
        if(fieldName.equalsIgnoreCase("input")) {
            return INPUT_CANNOT_BE_NULL;
        }
        if(fieldName.equalsIgnoreCase("locale")) {
            return LOCALE_CANNOT_BE_NULL;
        }
        return uppercaseFirst(fieldName) + CANNOT_BE_NULL;
    }

    /**
     * Creates a string to be used for exception messages with the specified field name.
     * @apiNote This method capitalizes the first letter of the specified field name
     *          and returns a string in the following format:
     *          "%Parameter% cannot be null or empty!".
     * @param fieldName the name of the field to add to the message
     * @return a new string in the format of "%Parameter% cannot be null or empty!"
     * @throws IllegalArgumentException if fieldName is null or empty
     */
    public static String cannotBeNullOrEmpty(final String fieldName) {
        checkArgumentNotNull(fieldName, "FieldName cannot be null or empty!");
        if(fieldName.equalsIgnoreCase("value")) {
            return "Value cannot be null or empty!";
        }
        if(fieldName.equalsIgnoreCase("input")) {
            return INPUT_CANNOT_BE_NULL_OR_EMPTY;
        }
        return uppercaseFirst(fieldName) + CANNOT_BE_NULL_OR_EMPTY;
    }

    /** Prevents instantiation of this utility class.  */
    private Literals() { }
}
