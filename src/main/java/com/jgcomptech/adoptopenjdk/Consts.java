package com.jgcomptech.adoptopenjdk;

/**
 * Collected constants of very general utility.
 *
 * All constants must be immutable.
 * No instances of this class can be constructed.
 */
public final class Consts {

    /**
     * Prevent object construction outside of this class.
     */
    private Consts(){
        //empty
    }

    /**
     * Only refer to primitives and immutable objects.
     *
     * Arrays present a problem since arrays are always mutable.
     * DO NOT USE public static final array fields.
     * One style is to instead use an unmodifiable List, built in a
     * static initializer block.
     *
     * Another style is to use a private array and wrap it up like so:
     * <pre>
     *  private static final Vehicle[] PRIVATE_VEHICLES = {...};
     *  public static final List VEHICLES =
     *         Collections.unmodifiableList(Arrays.asList(PRIVATE_VEHICLES));
     * </pre>
     */

    //characters
    public static final String NEW_LINE = System.getProperty("line.separator");
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");
    public static final String PATH_SEPARATOR = System.getProperty("path.separator");

    public static final String EMPTY_STRING = "";
    public static final String SPACE = " ";
    public static final String PERIOD = ".";
    public static final String TAB = "\t";

    //algebraic signs
    public static final int POSITIVE = 1;
    public static final int NEGATIVE = -1;
    public static final String PLUS_SIGN = "+";
    public static final String NEGATIVE_SIGN = "-";
}
