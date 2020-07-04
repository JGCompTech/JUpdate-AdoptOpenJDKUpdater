package com.jgcomptech.adoptopenjdk.recentJava;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Extends the Java 8 implementations of the Set class to add methods that were added in Java 9.
 */
@SuppressWarnings({"ProhibitedExceptionThrown", "NewExceptionWithoutArguments"})
public final class SetExt {
    private SetExt() { }

    /**
     * Returns an unmodifiable set containing zero elements.
     * See <a href="#unmodifiable">Unmodifiable Sets</a> for details.
     *
     * @param <E> the {@code Set}'s element type
     * @return an empty {@code Set}
     *
     * @since Java 9
     */
    public static <E> Set<E> of() {
        return Collections.unmodifiableSet(new HashSet<>());
    }

    /**
     * Returns an unmodifiable set containing one element.
     * See <a href="#unmodifiable">Unmodifiable Sets</a> for details.
     *
     * @param <E> the {@code Set}'s element type
     * @param e1 the single element
     * @return a {@code Set} containing the specified element
     * @throws NullPointerException if the element is {@code null}
     *
     * @since Java 9
     */
    public static <E> Set<E> of(final E e1) {
        if(e1 == null) throw new NullPointerException();
        final Set<E> newSet = new HashSet<>();
        newSet.add(e1);
        return Collections.unmodifiableSet(newSet);
    }

    /**
     * Returns an unmodifiable set containing two elements.
     * See <a href="#unmodifiable">Unmodifiable Sets</a> for details.
     *
     * @param <E> the {@code Set}'s element type
     * @param e1 the first element
     * @param e2 the second element
     * @return a {@code Set} containing the specified elements
     * @throws IllegalArgumentException if the elements are duplicates
     * @throws NullPointerException if an element is {@code null}
     *
     * @since Java 9
     */
    public static <E> Set<E> of(final E e1, final E e2) {
        if(e1 == null || e2 == null) throw new NullPointerException();
        if(e1.equals(e2)) throw new IllegalArgumentException();
        final Set<E> newSet = new HashSet<>();
        newSet.add(e1);
        newSet.add(e2);
        return Collections.unmodifiableSet(newSet);
    }

    /**
     * Returns an unmodifiable set containing three elements.
     * See <a href="#unmodifiable">Unmodifiable Sets</a> for details.
     *
     * @param <E> the {@code Set}'s element type
     * @param e1 the first element
     * @param e2 the second element
     * @param e3 the third element
     * @return a {@code Set} containing the specified elements
     * @throws IllegalArgumentException if there are any duplicate elements
     * @throws NullPointerException if an element is {@code null}
     *
     * @since Java 9
     */
    public static <E> Set<E> of(final E e1, final E e2, final E e3) {
        if(e1 == null || e2 == null || e3 == null) throw new NullPointerException();
        if(Stream.of(e1, e2, e3).distinct().count() != 3) throw new IllegalArgumentException();
        final Set<E> newSet = new HashSet<>();
        newSet.add(e1);
        newSet.add(e2);
        newSet.add(e3);
        return Collections.unmodifiableSet(newSet);
    }

    /**
     * Returns an unmodifiable set containing four elements.
     * See <a href="#unmodifiable">Unmodifiable Sets</a> for details.
     *
     * @param <E> the {@code Set}'s element type
     * @param e1 the first element
     * @param e2 the second element
     * @param e3 the third element
     * @param e4 the fourth element
     * @return a {@code Set} containing the specified elements
     * @throws IllegalArgumentException if there are any duplicate elements
     * @throws NullPointerException if an element is {@code null}
     *
     * @since Java 9
     */
    public static <E> Set<E> of(final E e1, final E e2, final E e3, final E e4) {
        if(e1 == null || e2 == null || e3 == null || e4 == null) throw new NullPointerException();
        if(Stream.of(e1, e2, e3, e4).distinct().count() != 4) throw new IllegalArgumentException();
        final Set<E> newSet = new HashSet<>();
        newSet.add(e1);
        newSet.add(e2);
        newSet.add(e3);
        newSet.add(e4);
        return Collections.unmodifiableSet(newSet);
    }

    /**
     * Returns an unmodifiable set containing five elements.
     * See <a href="#unmodifiable">Unmodifiable Sets</a> for details.
     *
     * @param <E> the {@code Set}'s element type
     * @param e1 the first element
     * @param e2 the second element
     * @param e3 the third element
     * @param e4 the fourth element
     * @param e5 the fifth element
     * @return a {@code Set} containing the specified elements
     * @throws IllegalArgumentException if there are any duplicate elements
     * @throws NullPointerException if an element is {@code null}
     *
     * @since Java 9
     */
    public static <E> Set<E> of(final E e1, final E e2, final E e3, final E e4, final E e5) {
        if(e1 == null || e2 == null || e3 == null
                || e4 == null || e5 == null) throw new NullPointerException();
        if(Stream.of(e1, e2, e3, e4, e5).distinct().count() != 5) throw new IllegalArgumentException();
        final Set<E> newSet = new HashSet<>();
        newSet.add(e1);
        newSet.add(e2);
        newSet.add(e3);
        newSet.add(e4);
        newSet.add(e5);
        return Collections.unmodifiableSet(newSet);
    }

    /**
     * Returns an unmodifiable set containing six elements.
     * See <a href="#unmodifiable">Unmodifiable Sets</a> for details.
     *
     * @param <E> the {@code Set}'s element type
     * @param e1 the first element
     * @param e2 the second element
     * @param e3 the third element
     * @param e4 the fourth element
     * @param e5 the fifth element
     * @param e6 the sixth element
     * @return a {@code Set} containing the specified elements
     * @throws IllegalArgumentException if there are any duplicate elements
     * @throws NullPointerException if an element is {@code null}
     *
     * @since Java 9
     */
    public static <E> Set<E> of(final E e1, final E e2, final E e3,
                                final E e4, final E e5, final E e6) {
        if(e1 == null || e2 == null || e3 == null
                || e4 == null || e5 == null || e6 == null) throw new NullPointerException();
        if(Stream.of(e1, e2, e3, e4, e5, e6).distinct().count() != 6) throw new IllegalArgumentException();
        final Set<E> newSet = new HashSet<>();
        newSet.add(e1);
        newSet.add(e2);
        newSet.add(e3);
        newSet.add(e4);
        newSet.add(e5);
        newSet.add(e6);
        return Collections.unmodifiableSet(newSet);
    }

    /**
     * Returns an unmodifiable set containing seven elements.
     * See <a href="#unmodifiable">Unmodifiable Sets</a> for details.
     *
     * @param <E> the {@code Set}'s element type
     * @param e1 the first element
     * @param e2 the second element
     * @param e3 the third element
     * @param e4 the fourth element
     * @param e5 the fifth element
     * @param e6 the sixth element
     * @param e7 the seventh element
     * @return a {@code Set} containing the specified elements
     * @throws IllegalArgumentException if there are any duplicate elements
     * @throws NullPointerException if an element is {@code null}
     *
     * @since Java 9
     */
    public static <E> Set<E> of(final E e1, final E e2, final E e3, final E e4,
                                final E e5, final E e6, final E e7) {
        if(e1 == null || e2 == null || e3 == null
                || e4 == null || e5 == null || e6 == null
                || e7 == null) throw new NullPointerException();
        if(Stream.of(e1, e2, e3, e4, e5, e6, e7).distinct().count() != 7) throw new IllegalArgumentException();
        final Set<E> newSet = new HashSet<>();
        newSet.add(e1);
        newSet.add(e2);
        newSet.add(e3);
        newSet.add(e4);
        newSet.add(e5);
        newSet.add(e6);
        newSet.add(e7);
        return Collections.unmodifiableSet(newSet);
    }

    /**
     * Returns an unmodifiable set containing eight elements.
     * See <a href="#unmodifiable">Unmodifiable Sets</a> for details.
     *
     * @param <E> the {@code Set}'s element type
     * @param e1 the first element
     * @param e2 the second element
     * @param e3 the third element
     * @param e4 the fourth element
     * @param e5 the fifth element
     * @param e6 the sixth element
     * @param e7 the seventh element
     * @param e8 the eighth element
     * @return a {@code Set} containing the specified elements
     * @throws IllegalArgumentException if there are any duplicate elements
     * @throws NullPointerException if an element is {@code null}
     *
     * @since Java 9
     */
    public static <E> Set<E> of(final E e1, final E e2, final E e3, final E e4,
                                final E e5, final E e6, final E e7, final E e8) {
        if(e1 == null || e2 == null || e3 == null
                || e4 == null || e5 == null || e6 == null
                || e7 == null || e8 == null) throw new NullPointerException();
        if(Stream.of(e1, e2, e3, e4, e5, e6, e7, e8).distinct().count() != 8) throw new IllegalArgumentException();
        final Set<E> newSet = new HashSet<>();
        newSet.add(e1);
        newSet.add(e2);
        newSet.add(e3);
        newSet.add(e4);
        newSet.add(e5);
        newSet.add(e6);
        newSet.add(e7);
        newSet.add(e8);
        return Collections.unmodifiableSet(newSet);
    }

    /**
     * Returns an unmodifiable set containing nine elements.
     * See <a href="#unmodifiable">Unmodifiable Sets</a> for details.
     *
     * @param <E> the {@code Set}'s element type
     * @param e1 the first element
     * @param e2 the second element
     * @param e3 the third element
     * @param e4 the fourth element
     * @param e5 the fifth element
     * @param e6 the sixth element
     * @param e7 the seventh element
     * @param e8 the eighth element
     * @param e9 the ninth element
     * @return a {@code Set} containing the specified elements
     * @throws IllegalArgumentException if there are any duplicate elements
     * @throws NullPointerException if an element is {@code null}
     *
     * @since Java 9
     */
    public static <E> Set<E> of(final E e1, final E e2, final E e3, final E e4, final E e5,
                                final E e6, final E e7, final E e8, final E e9) {
        if(e1 == null || e2 == null || e3 == null
                || e4 == null || e5 == null || e6 == null
                || e7 == null || e8 == null || e9 == null) throw new NullPointerException();
        if(Stream.of(e1, e2, e3, e4, e5, e6, e7, e8, e9).distinct().count() != 9) throw new IllegalArgumentException();
        final Set<E> newSet = new HashSet<>();
        newSet.add(e1);
        newSet.add(e2);
        newSet.add(e3);
        newSet.add(e4);
        newSet.add(e5);
        newSet.add(e6);
        newSet.add(e7);
        newSet.add(e8);
        newSet.add(e9);
        return Collections.unmodifiableSet(newSet);
    }

    /**
     * Returns an unmodifiable set containing ten elements.
     * See <a href="#unmodifiable">Unmodifiable Sets</a> for details.
     *
     * @param <E> the {@code Set}'s element type
     * @param e1 the first element
     * @param e2 the second element
     * @param e3 the third element
     * @param e4 the fourth element
     * @param e5 the fifth element
     * @param e6 the sixth element
     * @param e7 the seventh element
     * @param e8 the eighth element
     * @param e9 the ninth element
     * @param e10 the tenth element
     * @return a {@code Set} containing the specified elements
     * @throws IllegalArgumentException if there are any duplicate elements
     * @throws NullPointerException if an element is {@code null}
     *
     * @since Java 9
     */
    public static <E> Set<E> of(final E e1, final E e2, final E e3, final E e4, final E e5,
                                final E e6, final E e7, final E e8, final E e9, final E e10) {
        if(e1 == null || e2 == null || e3 == null
                || e4 == null || e5 == null || e6 == null
                || e7 == null || e8 == null || e9 == null
                || e10 == null) throw new NullPointerException();
        if(Stream.of(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10).distinct().count() != 10) {
            throw new IllegalArgumentException();
        }
        final Set<E> newSet = new HashSet<>();
        newSet.add(e1);
        newSet.add(e2);
        newSet.add(e3);
        newSet.add(e4);
        newSet.add(e5);
        newSet.add(e6);
        newSet.add(e7);
        newSet.add(e8);
        newSet.add(e9);
        newSet.add(e10);
        return Collections.unmodifiableSet(newSet);
    }

    /**
     * Returns an unmodifiable set containing an arbitrary number of elements.
     * See <a href="#unmodifiable">Unmodifiable Sets</a> for details.
     *
     * @apiNote
     * This method also accepts a single array as an argument. The element type of
     * the resulting set will be the component type of the array, and the size of
     * the set will be equal to the length of the array. To create a set with
     * a single element that is an array, do the following:
     *
     * <pre>{@code
     *     String[] array = ... ;
     *     Set<String[]> list = Set.<String[]>of(array);
     * }</pre>
     *
     * This will cause the {@link SetExt#of(Object) SetExt.of(E)} method
     * to be invoked instead.
     *
     * @param <E> the {@code Set}'s element type
     * @param elements the elements to be contained in the set
     * @return a {@code Set} containing the specified elements
     * @throws IllegalArgumentException if there are any duplicate elements
     * @throws NullPointerException if an element is {@code null} or if the array is {@code null}
     *
     * @since Java 9
     */
    @SafeVarargs
    @SuppressWarnings("varargs")
    public static <E> Set<E> of(final E... elements) {
        switch (elements.length) { // implicit null check of elements
            case 0:
                return Collections.unmodifiableSet(new HashSet<>());
            case 1:
                if(elements[0] == null) throw new NullPointerException();
                final Set<E> newSet1 = new HashSet<>();
                newSet1.add(elements[0]);
                return Collections.unmodifiableSet(newSet1);
            case 2:
                if(elements[0] == null || elements[1] == null) throw new NullPointerException();
                if(elements[0].equals(elements[1])) throw new IllegalArgumentException();
                final Set<E> newSet2 = new HashSet<>();
                newSet2.add(elements[0]);
                newSet2.add(elements[1]);
                return Collections.unmodifiableSet(newSet2);
            default:
                for (final E element : elements) if(element == null) throw new NullPointerException();
                if(Stream.of(elements).distinct().count() != elements.length) throw new IllegalArgumentException();
                return Collections.unmodifiableSet(new HashSet<>(Arrays.asList(elements)));
        }
    }
}
