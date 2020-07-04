package com.jgcomptech.adoptopenjdk.recentJava;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Extends the Java 8 implementations of the List class to add methods that were added in Java 9.
 */
@SuppressWarnings({"ProhibitedExceptionThrown", "NewExceptionWithoutArguments"})
public final class ListExt {
    private ListExt() { }

    /**
     * Returns an unmodifiable list containing zero elements.
     *
     * See <a href="#unmodifiable">Unmodifiable Lists</a> for details.
     *
     * @param <E> the {@code List}'s element type
     * @return an empty {@code List}
     *
     * @since Java 9
     */
    public static <E> List<E> of() {
        return Collections.unmodifiableList(new ArrayList<>());
    }

    /**
     * Returns an unmodifiable list containing one element.
     *
     * See <a href="#unmodifiable">Unmodifiable Lists</a> for details.
     *
     * @param <E> the {@code List}'s element type
     * @param e1 the single element
     * @return a {@code List} containing the specified element
     * @throws NullPointerException if the element is {@code null}
     *
     * @since Java 9
     */
    public static <E> List<E> of(final E e1) {
        if(e1 == null) throw new NullPointerException();
        final List<E> newList = new ArrayList<>();
        newList.add(e1);
        return Collections.unmodifiableList(newList);
    }

    /**
     * Returns an unmodifiable list containing two elements.
     *
     * See <a href="#unmodifiable">Unmodifiable Lists</a> for details.
     *
     * @param <E> the {@code List}'s element type
     * @param e1 the first element
     * @param e2 the second element
     * @return a {@code List} containing the specified elements
     * @throws NullPointerException if an element is {@code null}
     *
     * @since Java 9
     */
    public static <E> List<E> of(final E e1, final E e2) {
        if(e1 == null || e2 == null) throw new NullPointerException();
        final List<E> newList = new ArrayList<>();
        newList.add(e1);
        newList.add(e2);
        return Collections.unmodifiableList(newList);
    }

    /**
     * Returns an unmodifiable list containing three elements.
     *
     * See <a href="#unmodifiable">Unmodifiable Lists</a> for details.
     *
     * @param <E> the {@code List}'s element type
     * @param e1 the first element
     * @param e2 the second element
     * @param e3 the third element
     * @return a {@code List} containing the specified elements
     * @throws NullPointerException if an element is {@code null}
     *
     * @since Java 9
     */
    public static <E> List<E> of(final E e1, final E e2, final E e3) {
        if(e1 == null || e2 == null || e3 == null) throw new NullPointerException();
        final List<E> newList = new ArrayList<>();
        newList.add(e1);
        newList.add(e2);
        newList.add(e3);
        return Collections.unmodifiableList(newList);
    }

    /**
     * Returns an unmodifiable list containing four elements.
     *
     * See <a href="#unmodifiable">Unmodifiable Lists</a> for details.
     *
     * @param <E> the {@code List}'s element type
     * @param e1 the first element
     * @param e2 the second element
     * @param e3 the third element
     * @param e4 the fourth element
     * @return a {@code List} containing the specified elements
     * @throws NullPointerException if an element is {@code null}
     *
     * @since Java 9
     */
    public static <E> List<E> of(final E e1, final E e2, final E e3, final E e4) {
        if(e1 == null || e2 == null || e3 == null || e4 == null) throw new NullPointerException();
        final List<E> newList = new ArrayList<>();
        newList.add(e1);
        newList.add(e2);
        newList.add(e3);
        newList.add(e4);
        return Collections.unmodifiableList(newList);
    }

    /**
     * Returns an unmodifiable list containing five elements.
     *
     * See <a href="#unmodifiable">Unmodifiable Lists</a> for details.
     *
     * @param <E> the {@code List}'s element type
     * @param e1 the first element
     * @param e2 the second element
     * @param e3 the third element
     * @param e4 the fourth element
     * @param e5 the fifth element
     * @return a {@code List} containing the specified elements
     * @throws NullPointerException if an element is {@code null}
     *
     * @since Java 9
     */
    public static <E> List<E> of(final E e1, final E e2, final E e3, final E e4, final E e5) {
        if(e1 == null || e2 == null || e3 == null
                || e4 == null || e5 == null) throw new NullPointerException();
        final List<E> newList = new ArrayList<>();
        newList.add(e1);
        newList.add(e2);
        newList.add(e3);
        newList.add(e4);
        newList.add(e5);
        return Collections.unmodifiableList(newList);
    }

    /**
     * Returns an unmodifiable list containing six elements.
     *
     * See <a href="#unmodifiable">Unmodifiable Lists</a> for details.
     *
     * @param <E> the {@code List}'s element type
     * @param e1 the first element
     * @param e2 the second element
     * @param e3 the third element
     * @param e4 the fourth element
     * @param e5 the fifth element
     * @param e6 the sixth element
     * @return a {@code List} containing the specified elements
     * @throws NullPointerException if an element is {@code null}
     *
     * @since Java 9
     */
    public static <E> List<E> of(final E e1, final E e2, final E e3,
                          final E e4, final E e5, final E e6) {
        if(e1 == null || e2 == null || e3 == null
                || e4 == null || e5 == null || e6 == null) throw new NullPointerException();
        final List<E> newList = new ArrayList<>();
        newList.add(e1);
        newList.add(e2);
        newList.add(e3);
        newList.add(e4);
        newList.add(e5);
        newList.add(e6);
        return Collections.unmodifiableList(newList);
    }

    /**
     * Returns an unmodifiable list containing seven elements.
     *
     * See <a href="#unmodifiable">Unmodifiable Lists</a> for details.
     *
     * @param <E> the {@code List}'s element type
     * @param e1 the first element
     * @param e2 the second element
     * @param e3 the third element
     * @param e4 the fourth element
     * @param e5 the fifth element
     * @param e6 the sixth element
     * @param e7 the seventh element
     * @return a {@code List} containing the specified elements
     * @throws NullPointerException if an element is {@code null}
     *
     * @since Java 9
     */
    public static <E> List<E> of(final E e1, final E e2, final E e3, final E e4,
                          final E e5, final E e6, final E e7) {
        if(e1 == null || e2 == null || e3 == null
                || e4 == null || e5 == null || e6 == null
                || e7 == null) throw new NullPointerException();
        final List<E> newList = new ArrayList<>();
        newList.add(e1);
        newList.add(e2);
        newList.add(e3);
        newList.add(e4);
        newList.add(e5);
        newList.add(e6);
        newList.add(e7);
        return Collections.unmodifiableList(newList);
    }

    /**
     * Returns an unmodifiable list containing eight elements.
     *
     * See <a href="#unmodifiable">Unmodifiable Lists</a> for details.
     *
     * @param <E> the {@code List}'s element type
     * @param e1 the first element
     * @param e2 the second element
     * @param e3 the third element
     * @param e4 the fourth element
     * @param e5 the fifth element
     * @param e6 the sixth element
     * @param e7 the seventh element
     * @param e8 the eighth element
     * @return a {@code List} containing the specified elements
     * @throws NullPointerException if an element is {@code null}
     *
     * @since Java 9
     */
    public static <E> List<E> of(final E e1, final E e2, final E e3, final E e4,
                          final E e5, final E e6, final E e7, final E e8) {
        if(e1 == null || e2 == null || e3 == null
                || e4 == null || e5 == null || e6 == null
                || e7 == null || e8 == null) throw new NullPointerException();
        final List<E> newList = new ArrayList<>();
        newList.add(e1);
        newList.add(e2);
        newList.add(e3);
        newList.add(e4);
        newList.add(e5);
        newList.add(e6);
        newList.add(e7);
        newList.add(e8);
        return Collections.unmodifiableList(newList);
    }

    /**
     * Returns an unmodifiable list containing nine elements.
     *
     * See <a href="#unmodifiable">Unmodifiable Lists</a> for details.
     *
     * @param <E> the {@code List}'s element type
     * @param e1 the first element
     * @param e2 the second element
     * @param e3 the third element
     * @param e4 the fourth element
     * @param e5 the fifth element
     * @param e6 the sixth element
     * @param e7 the seventh element
     * @param e8 the eighth element
     * @param e9 the ninth element
     * @return a {@code List} containing the specified elements
     * @throws NullPointerException if an element is {@code null}
     *
     * @since Java 9
     */
    public static <E> List<E> of(final E e1, final E e2, final E e3, final E e4, final E e5,
                          final E e6, final E e7, final E e8, final E e9) {
        if(e1 == null || e2 == null || e3 == null
                || e4 == null || e5 == null || e6 == null
                || e7 == null || e8 == null || e9 == null) throw new NullPointerException();
        final List<E> newList = new ArrayList<>();
        newList.add(e1);
        newList.add(e2);
        newList.add(e3);
        newList.add(e4);
        newList.add(e5);
        newList.add(e6);
        newList.add(e7);
        newList.add(e8);
        newList.add(e9);
        return Collections.unmodifiableList(newList);
    }

    /**
     * Returns an unmodifiable list containing ten elements.
     *
     * See <a href="#unmodifiable">Unmodifiable Lists</a> for details.
     *
     * @param <E> the {@code List}'s element type
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
     * @return a {@code List} containing the specified elements
     * @throws NullPointerException if an element is {@code null}
     *
     * @since Java 9
     */
    public static <E> List<E> of(final E e1, final E e2, final E e3, final E e4, final E e5,
                          final E e6, final E e7, final E e8, final E e9, final E e10) {
        if(e1 == null || e2 == null || e3 == null
                || e4 == null || e5 == null || e6 == null
                || e7 == null || e8 == null || e9 == null
                || e10 == null) throw new NullPointerException();
        final List<E> newList = new ArrayList<>();
        newList.add(e1);
        newList.add(e2);
        newList.add(e3);
        newList.add(e4);
        newList.add(e5);
        newList.add(e6);
        newList.add(e7);
        newList.add(e8);
        newList.add(e9);
        newList.add(e10);
        return Collections.unmodifiableList(newList);
    }

    /**
     * Returns an unmodifiable list containing an arbitrary number of elements.
     * See <a href="#unmodifiable">Unmodifiable Lists</a> for details.
     *
     * @apiNote
     * This method also accepts a single array as an argument. The element type of
     * the resulting list will be the component type of the array, and the size of
     * the list will be equal to the length of the array. To create a list with
     * a single element that is an array, do the following:
     *
     * <pre>{@code
     *     String[] array = ... ;
     *     List<String[]> list = List.<String[]>of(array);
     * }</pre>
     *
     * This will cause the {@link ListExt#of(Object) ListExt.of(E)} method
     * to be invoked instead.
     *
     * @param <E> the {@code List}'s element type
     * @param elements the elements to be contained in the list
     * @return a {@code List} containing the specified elements
     * @throws NullPointerException if an element is {@code null} or if the array is {@code null}
     *
     * @since Java 9
     */
    @SafeVarargs
    @SuppressWarnings("varargs")
    public static <E> List<E> of(final E... elements) {
        switch (elements.length) { // implicit null check of elements
            case 0:
                return Collections.unmodifiableList(new ArrayList<>());
            case 1:
                if(elements[0] == null) throw new NullPointerException();
                final List<E> newList1 = new ArrayList<>();
                newList1.add(elements[0]);
                return Collections.unmodifiableList(newList1);
            case 2:
                if(elements[0] == null || elements[1] == null) throw new NullPointerException();
                final List<E> newList2 = new ArrayList<>();
                newList2.add(elements[0]);
                newList2.add(elements[1]);
                return Collections.unmodifiableList(newList2);
            default:
                for (final E element : elements) {
                    if(element == null) throw new NullPointerException();
                }
                return Collections.unmodifiableList(Arrays.asList(elements));
        }
    }
}
