package com.jgcomptech.adoptopenjdk.recentJava;

import java.util.AbstractMap;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Extends the Java 8 implementations of the Map class to add methods that were added in Java 9.
 */
@SuppressWarnings({"NewExceptionWithoutArguments", "ProhibitedExceptionThrown"})
public final class MapExt {
    private MapExt() { }

    /**
     * Returns an unmodifiable map containing zero mappings.
     * See <a href="#unmodifiable">Unmodifiable Maps</a> for details.
     *
     * @param <K> the {@code Map}'s key type
     * @param <V> the {@code Map}'s value type
     * @return an empty {@code Map}
     *
     * @since Java 9
     */
    public static <K, V> Map<K, V> of() {
        return Collections.unmodifiableMap(new HashMap<>());
    }

    /**
     * Returns an unmodifiable map containing a single mapping.
     * See <a href="#unmodifiable">Unmodifiable Maps</a> for details.
     *
     * @param <K> the {@code Map}'s key type
     * @param <V> the {@code Map}'s value type
     * @param k1 the mapping's key
     * @param v1 the mapping's value
     * @return a {@code Map} containing the specified mapping
     * @throws NullPointerException if the key or the value is {@code null}
     *
     * @since Java 9
     */
    public static <K, V> Map<K, V> of(final K k1, final V v1) {
        if(k1 == null || v1 == null) throw new NullPointerException();
        final Map<K, V> newMap = new HashMap<>();
        newMap.put(k1, v1);
        return Collections.unmodifiableMap(newMap);
    }

    /**
     * Returns an unmodifiable map containing two mappings.
     * See <a href="#unmodifiable">Unmodifiable Maps</a> for details.
     *
     * @param <K> the {@code Map}'s key type
     * @param <V> the {@code Map}'s value type
     * @param k1 the first mapping's key
     * @param v1 the first mapping's value
     * @param k2 the second mapping's key
     * @param v2 the second mapping's value
     * @return a {@code Map} containing the specified mappings
     * @throws IllegalArgumentException if the keys are duplicates
     * @throws NullPointerException if any key or value is {@code null}
     *
     * @since Java 9
     */
    public static <K, V> Map<K, V> of(final K k1, final V v1, final K k2, final V v2) {
        if(k1 == null || v1 == null || k2 == null || v2 == null) throw new NullPointerException();
        final Map<K, V> newMap = new HashMap<>();
        newMap.put(k1, v1);
        if(newMap.containsKey(k2)) throw new IllegalArgumentException();
        newMap.put(k2, v2);
        return Collections.unmodifiableMap(newMap);
    }

    /**
     * Returns an unmodifiable map containing three mappings.
     * See <a href="#unmodifiable">Unmodifiable Maps</a> for details.
     *
     * @param <K> the {@code Map}'s key type
     * @param <V> the {@code Map}'s value type
     * @param k1 the first mapping's key
     * @param v1 the first mapping's value
     * @param k2 the second mapping's key
     * @param v2 the second mapping's value
     * @param k3 the third mapping's key
     * @param v3 the third mapping's value
     * @return a {@code Map} containing the specified mappings
     * @throws IllegalArgumentException if there are any duplicate keys
     * @throws NullPointerException if any key or value is {@code null}
     *
     * @since Java 9
     */
    public static <K, V> Map<K, V> of(final K k1, final V v1, final K k2, final V v2, final K k3, final V v3) {
        if(k1 == null || v1 == null
                || k2 == null || v2 == null
                || k3 == null || v3 == null) throw new NullPointerException();
        final Map<K, V> newMap = new HashMap<>();
        newMap.put(k1, v1);
        if(newMap.containsKey(k2)) throw new IllegalArgumentException();
        newMap.put(k2, v2);
        if(newMap.containsKey(k3)) throw new IllegalArgumentException();
        newMap.put(k3, v3);
        return Collections.unmodifiableMap(newMap);
    }

    /**
     * Returns an unmodifiable map containing four mappings.
     * See <a href="#unmodifiable">Unmodifiable Maps</a> for details.
     *
     * @param <K> the {@code Map}'s key type
     * @param <V> the {@code Map}'s value type
     * @param k1 the first mapping's key
     * @param v1 the first mapping's value
     * @param k2 the second mapping's key
     * @param v2 the second mapping's value
     * @param k3 the third mapping's key
     * @param v3 the third mapping's value
     * @param k4 the fourth mapping's key
     * @param v4 the fourth mapping's value
     * @return a {@code Map} containing the specified mappings
     * @throws IllegalArgumentException if there are any duplicate keys
     * @throws NullPointerException if any key or value is {@code null}
     *
     * @since Java 9
     */
    public static <K, V> Map<K, V> of(final K k1, final V v1, final K k2, final V v2,
                               final K k3, final V v3, final K k4, final V v4) {
        if(k1 == null || v1 == null
                || k2 == null || v2 == null
                || k3 == null || v3 == null
                || k4 == null || v4 == null) throw new NullPointerException();
        final Map<K, V> newMap = new HashMap<>();
        newMap.put(k1, v1);
        if(newMap.containsKey(k2)) throw new IllegalArgumentException();
        newMap.put(k2, v2);
        if(newMap.containsKey(k3)) throw new IllegalArgumentException();
        newMap.put(k3, v3);
        if(newMap.containsKey(k4)) throw new IllegalArgumentException();
        newMap.put(k4, v4);
        return Collections.unmodifiableMap(newMap);
    }

    /**
     * Returns an unmodifiable map containing five mappings.
     * See <a href="#unmodifiable">Unmodifiable Maps</a> for details.
     *
     * @param <K> the {@code Map}'s key type
     * @param <V> the {@code Map}'s value type
     * @param k1 the first mapping's key
     * @param v1 the first mapping's value
     * @param k2 the second mapping's key
     * @param v2 the second mapping's value
     * @param k3 the third mapping's key
     * @param v3 the third mapping's value
     * @param k4 the fourth mapping's key
     * @param v4 the fourth mapping's value
     * @param k5 the fifth mapping's key
     * @param v5 the fifth mapping's value
     * @return a {@code Map} containing the specified mappings
     * @throws IllegalArgumentException if there are any duplicate keys
     * @throws NullPointerException if any key or value is {@code null}
     *
     * @since Java 9
     */
    public static <K, V> Map<K, V> of(final K k1, final V v1, final K k2, final V v2, final K k3, final V v3,
                               final K k4, final V v4, final K k5, final V v5) {
        if(k1 == null || v1 == null
                || k2 == null || v2 == null
                || k3 == null || v3 == null
                || k4 == null || v4 == null
                || k5 == null || v5 == null) throw new NullPointerException();
        final Map<K, V> newMap = new HashMap<>();
        newMap.put(k1, v1);
        if(newMap.containsKey(k2)) throw new IllegalArgumentException();
        newMap.put(k2, v2);
        if(newMap.containsKey(k3)) throw new IllegalArgumentException();
        newMap.put(k3, v3);
        if(newMap.containsKey(k4)) throw new IllegalArgumentException();
        newMap.put(k4, v4);
        if(newMap.containsKey(k5)) throw new IllegalArgumentException();
        newMap.put(k5, v5);
        return Collections.unmodifiableMap(newMap);
    }

    /**
     * Returns an unmodifiable map containing six mappings.
     * See <a href="#unmodifiable">Unmodifiable Maps</a> for details.
     *
     * @param <K> the {@code Map}'s key type
     * @param <V> the {@code Map}'s value type
     * @param k1 the first mapping's key
     * @param v1 the first mapping's value
     * @param k2 the second mapping's key
     * @param v2 the second mapping's value
     * @param k3 the third mapping's key
     * @param v3 the third mapping's value
     * @param k4 the fourth mapping's key
     * @param v4 the fourth mapping's value
     * @param k5 the fifth mapping's key
     * @param v5 the fifth mapping's value
     * @param k6 the sixth mapping's key
     * @param v6 the sixth mapping's value
     * @return a {@code Map} containing the specified mappings
     * @throws IllegalArgumentException if there are any duplicate keys
     * @throws NullPointerException if any key or value is {@code null}
     *
     * @since Java 9
     */
    public static <K, V> Map<K, V> of(final K k1, final V v1, final K k2, final V v2, final K k3, final V v3,
                               final K k4, final V v4, final K k5, final V v5, final K k6, final V v6) {
        if(k1 == null || v1 == null
                || k2 == null || v2 == null
                || k3 == null || v3 == null
                || k4 == null || v4 == null
                || k5 == null || v5 == null
                || k6 == null || v6 == null) throw new NullPointerException();
        final Map<K, V> newMap = new HashMap<>();
        newMap.put(k1, v1);
        if(newMap.containsKey(k2)) throw new IllegalArgumentException();
        newMap.put(k2, v2);
        if(newMap.containsKey(k3)) throw new IllegalArgumentException();
        newMap.put(k3, v3);
        if(newMap.containsKey(k4)) throw new IllegalArgumentException();
        newMap.put(k4, v4);
        if(newMap.containsKey(k5)) throw new IllegalArgumentException();
        newMap.put(k5, v5);
        if(newMap.containsKey(k6)) throw new IllegalArgumentException();
        newMap.put(k6, v6);
        return Collections.unmodifiableMap(newMap);
    }

    /**
     * Returns an unmodifiable map containing seven mappings.
     * See <a href="#unmodifiable">Unmodifiable Maps</a> for details.
     *
     * @param <K> the {@code Map}'s key type
     * @param <V> the {@code Map}'s value type
     * @param k1 the first mapping's key
     * @param v1 the first mapping's value
     * @param k2 the second mapping's key
     * @param v2 the second mapping's value
     * @param k3 the third mapping's key
     * @param v3 the third mapping's value
     * @param k4 the fourth mapping's key
     * @param v4 the fourth mapping's value
     * @param k5 the fifth mapping's key
     * @param v5 the fifth mapping's value
     * @param k6 the sixth mapping's key
     * @param v6 the sixth mapping's value
     * @param k7 the seventh mapping's key
     * @param v7 the seventh mapping's value
     * @return a {@code Map} containing the specified mappings
     * @throws IllegalArgumentException if there are any duplicate keys
     * @throws NullPointerException if any key or value is {@code null}
     *
     * @since Java 9
     */
    public static <K, V> Map<K, V> of(final K k1, final V v1, final K k2, final V v2, final K k3, final V v3,
                               final K k4, final V v4, final K k5, final V v5, final K k6, final V v6,
                               final K k7, final V v7) {
        if(k1 == null || v1 == null
                || k2 == null || v2 == null
                || k3 == null || v3 == null
                || k4 == null || v4 == null
                || k5 == null || v5 == null
                || k6 == null || v6 == null
                || k7 == null || v7 == null) throw new NullPointerException();
        final Map<K, V> newMap = new HashMap<>();
        newMap.put(k1, v1);
        if(newMap.containsKey(k2)) throw new IllegalArgumentException();
        newMap.put(k2, v2);
        if(newMap.containsKey(k3)) throw new IllegalArgumentException();
        newMap.put(k3, v3);
        if(newMap.containsKey(k4)) throw new IllegalArgumentException();
        newMap.put(k4, v4);
        if(newMap.containsKey(k5)) throw new IllegalArgumentException();
        newMap.put(k5, v5);
        if(newMap.containsKey(k6)) throw new IllegalArgumentException();
        newMap.put(k6, v6);
        if(newMap.containsKey(k7)) throw new IllegalArgumentException();
        newMap.put(k7, v7);
        return Collections.unmodifiableMap(newMap);
    }

    /**
     * Returns an unmodifiable map containing eight mappings.
     * See <a href="#unmodifiable">Unmodifiable Maps</a> for details.
     *
     * @param <K> the {@code Map}'s key type
     * @param <V> the {@code Map}'s value type
     * @param k1 the first mapping's key
     * @param v1 the first mapping's value
     * @param k2 the second mapping's key
     * @param v2 the second mapping's value
     * @param k3 the third mapping's key
     * @param v3 the third mapping's value
     * @param k4 the fourth mapping's key
     * @param v4 the fourth mapping's value
     * @param k5 the fifth mapping's key
     * @param v5 the fifth mapping's value
     * @param k6 the sixth mapping's key
     * @param v6 the sixth mapping's value
     * @param k7 the seventh mapping's key
     * @param v7 the seventh mapping's value
     * @param k8 the eighth mapping's key
     * @param v8 the eighth mapping's value
     * @return a {@code Map} containing the specified mappings
     * @throws IllegalArgumentException if there are any duplicate keys
     * @throws NullPointerException if any key or value is {@code null}
     *
     * @since Java 9
     */
    public static <K, V> Map<K, V> of(final K k1, final V v1, final K k2, final V v2, final K k3, final V v3,
                               final K k4, final V v4, final K k5, final V v5, final K k6, final V v6,
                               final K k7, final V v7, final K k8, final V v8) {
        if(k1 == null || v1 == null
                || k2 == null || v2 == null
                || k3 == null || v3 == null
                || k4 == null || v4 == null
                || k5 == null || v5 == null
                || k6 == null || v6 == null
                || k7 == null || v7 == null
                || k8 == null || v8 == null) throw new NullPointerException();
        final Map<K, V> newMap = new HashMap<>();
        newMap.put(k1, v1);
        if(newMap.containsKey(k2)) throw new IllegalArgumentException();
        newMap.put(k2, v2);
        if(newMap.containsKey(k3)) throw new IllegalArgumentException();
        newMap.put(k3, v3);
        if(newMap.containsKey(k4)) throw new IllegalArgumentException();
        newMap.put(k4, v4);
        if(newMap.containsKey(k5)) throw new IllegalArgumentException();
        newMap.put(k5, v5);
        if(newMap.containsKey(k6)) throw new IllegalArgumentException();
        newMap.put(k6, v6);
        if(newMap.containsKey(k7)) throw new IllegalArgumentException();
        newMap.put(k7, v7);
        if(newMap.containsKey(k8)) throw new IllegalArgumentException();
        newMap.put(k8, v8);
        return Collections.unmodifiableMap(newMap);
    }

    /**
     * Returns an unmodifiable map containing nine mappings.
     * See <a href="#unmodifiable">Unmodifiable Maps</a> for details.
     *
     * @param <K> the {@code Map}'s key type
     * @param <V> the {@code Map}'s value type
     * @param k1 the first mapping's key
     * @param v1 the first mapping's value
     * @param k2 the second mapping's key
     * @param v2 the second mapping's value
     * @param k3 the third mapping's key
     * @param v3 the third mapping's value
     * @param k4 the fourth mapping's key
     * @param v4 the fourth mapping's value
     * @param k5 the fifth mapping's key
     * @param v5 the fifth mapping's value
     * @param k6 the sixth mapping's key
     * @param v6 the sixth mapping's value
     * @param k7 the seventh mapping's key
     * @param v7 the seventh mapping's value
     * @param k8 the eighth mapping's key
     * @param v8 the eighth mapping's value
     * @param k9 the ninth mapping's key
     * @param v9 the ninth mapping's value
     * @return a {@code Map} containing the specified mappings
     * @throws IllegalArgumentException if there are any duplicate keys
     * @throws NullPointerException if any key or value is {@code null}
     *
     * @since Java 9
     */
    public static <K, V> Map<K, V> of(final K k1, final V v1, final K k2, final V v2, final K k3, final V v3,
                               final K k4, final V v4, final K k5, final V v5, final K k6, final V v6,
                               final K k7, final V v7, final K k8, final V v8, final K k9, final V v9) {
        if(k1 == null || v1 == null
                || k2 == null || v2 == null
                || k3 == null || v3 == null
                || k4 == null || v4 == null
                || k5 == null || v5 == null
                || k6 == null || v6 == null
                || k7 == null || v7 == null
                || k8 == null || v8 == null
                || k9 == null || v9 == null) throw new NullPointerException();
        final Map<K, V> newMap = new HashMap<>();
        newMap.put(k1, v1);
        if(newMap.containsKey(k2)) throw new IllegalArgumentException();
        newMap.put(k2, v2);
        if(newMap.containsKey(k3)) throw new IllegalArgumentException();
        newMap.put(k3, v3);
        if(newMap.containsKey(k4)) throw new IllegalArgumentException();
        newMap.put(k4, v4);
        if(newMap.containsKey(k5)) throw new IllegalArgumentException();
        newMap.put(k5, v5);
        if(newMap.containsKey(k6)) throw new IllegalArgumentException();
        newMap.put(k6, v6);
        if(newMap.containsKey(k7)) throw new IllegalArgumentException();
        newMap.put(k7, v7);
        if(newMap.containsKey(k8)) throw new IllegalArgumentException();
        newMap.put(k8, v8);
        if(newMap.containsKey(k9)) throw new IllegalArgumentException();
        newMap.put(k9, v9);
        return Collections.unmodifiableMap(newMap);
    }

    /**
     * Returns an unmodifiable map containing ten mappings.
     * See <a href="#unmodifiable">Unmodifiable Maps</a> for details.
     *
     * @param <K> the {@code Map}'s key type
     * @param <V> the {@code Map}'s value type
     * @param k1 the first mapping's key
     * @param v1 the first mapping's value
     * @param k2 the second mapping's key
     * @param v2 the second mapping's value
     * @param k3 the third mapping's key
     * @param v3 the third mapping's value
     * @param k4 the fourth mapping's key
     * @param v4 the fourth mapping's value
     * @param k5 the fifth mapping's key
     * @param v5 the fifth mapping's value
     * @param k6 the sixth mapping's key
     * @param v6 the sixth mapping's value
     * @param k7 the seventh mapping's key
     * @param v7 the seventh mapping's value
     * @param k8 the eighth mapping's key
     * @param v8 the eighth mapping's value
     * @param k9 the ninth mapping's key
     * @param v9 the ninth mapping's value
     * @param k10 the tenth mapping's key
     * @param v10 the tenth mapping's value
     * @return a {@code Map} containing the specified mappings
     * @throws IllegalArgumentException if there are any duplicate keys
     * @throws NullPointerException if any key or value is {@code null}
     *
     * @since Java 9
     */
    public static <K, V> Map<K, V> of(final K k1, final V v1, final K k2, final V v2, final K k3, final V v3,
                               final K k4, final V v4, final K k5, final V v5, final K k6, final V v6,
                               final K k7, final V v7, final K k8, final V v8, final K k9, final V v9,
                               final K k10, final V v10) {
        if(k1 == null || v1 == null
                || k2 == null || v2 == null
                || k3 == null || v3 == null
                || k4 == null || v4 == null
                || k5 == null || v5 == null
                || k6 == null || v6 == null
                || k7 == null || v7 == null
                || k8 == null || v8 == null
                || k9 == null || v9 == null
                || k10 == null || v10 == null) throw new NullPointerException();
        final Map<K, V> newMap = new HashMap<>();
        newMap.put(k1, v1);
        if(newMap.containsKey(k2)) throw new IllegalArgumentException();
        newMap.put(k2, v2);
        if(newMap.containsKey(k3)) throw new IllegalArgumentException();
        newMap.put(k3, v3);
        if(newMap.containsKey(k4)) throw new IllegalArgumentException();
        newMap.put(k4, v4);
        if(newMap.containsKey(k5)) throw new IllegalArgumentException();
        newMap.put(k5, v5);
        if(newMap.containsKey(k6)) throw new IllegalArgumentException();
        newMap.put(k6, v6);
        if(newMap.containsKey(k7)) throw new IllegalArgumentException();
        newMap.put(k7, v7);
        if(newMap.containsKey(k8)) throw new IllegalArgumentException();
        newMap.put(k8, v8);
        if(newMap.containsKey(k9)) throw new IllegalArgumentException();
        newMap.put(k9, v9);
        if(newMap.containsKey(k10)) throw new IllegalArgumentException();
        newMap.put(k10, v10);
        return Collections.unmodifiableMap(newMap);
    }

    /**
     * Returns an unmodifiable map containing keys and values extracted from the given entries.
     * The entries themselves are not stored in the map.
     * See <a href="#unmodifiable">Unmodifiable Maps</a> for details.
     *
     * @apiNote
     * It is convenient to create the map entries using the {@link MapExt#entry MapExt.entry()} method.
     * For example,
     *
     * <pre>{@code
     *     import public static java.util.Map.entry;
     *
     *     Map<Integer,String> map = Map.ofEntries(
     *         entry(1, "a"),
     *         entry(2, "b"),
     *         entry(3, "c"),
     *         ...
     *         entry(26, "z"));
     * }</pre>
     *
     * @param <K> the {@code Map}'s key type
     * @param <V> the {@code Map}'s value type
     * @param entries {@code Map.Entry}s containing the keys and values from which the map is populated
     * @return a {@code Map} containing the specified mappings
     * @throws IllegalArgumentException if there are any duplicate keys
     * @throws NullPointerException if any entry, key, or value is {@code null}, or if
     *         the {@code entries} array is {@code null}
     *
     * @see MapExt#entry MapExt.entry()
     * @since Java 9
     */
    @SafeVarargs
    @SuppressWarnings("varargs")
    public static <K, V> Map<K, V> ofEntries(final Map.Entry<? extends K, ? extends V>... entries) {
        if (entries.length == 0) { // implicit null check of entries array
            return Collections.unmodifiableMap(new HashMap<>());
        }
        if (entries.length == 1) {
            // implicit null check of the array slot
            if(entries[0] == null || entries[0].getKey() == null || entries[0].getValue() == null) {
                throw new NullPointerException();
            }
            final Map<K, V> newMap = new HashMap<>();
            newMap.put(entries[0].getKey(), entries[0].getValue());
            return Collections.unmodifiableMap(newMap);
        }
        final Map<K, V> newMap = new HashMap<>();
        for (final Map.Entry<? extends K, ? extends V> entry : entries) {
            if(entry == null || entry.getKey() == null || entry.getValue() == null) {
                throw new NullPointerException();
            }
            if(newMap.containsKey(entry.getKey())) throw new IllegalArgumentException();
            newMap.put(entry.getKey(), entry.getValue());
        }
        return Collections.unmodifiableMap(newMap);
    }

    /**
     * Returns an unmodifiable {@link Map.Entry} containing the given key and value.
     * These entries are suitable for populating {@code Map} instances using the
     * {@link MapExt#ofEntries MapExt.ofEntries()} method.
     * The {@code Entry} instances created by this method have the following characteristics:
     *
     * <ul>
     * <li>They disallow {@code null} keys and values. Attempts to create them using a {@code null}
     * key or value result in {@code NullPointerException}.
     * <li>They are unmodifiable. Calls to {@link Map.Entry#setValue Entry.setValue()}
     * on a returned {@code Entry} result in {@code UnsupportedOperationException}.
     * <li>They are not serializable.
     * <li>They are <a href="../lang/doc-files/ValueBased.html">value-based</a>.
     * Callers should make no assumptions about the identity of the returned instances.
     * This method is free to create new instances or reuse existing ones. Therefore,
     * identity-sensitive operations on these instances (reference equality ({@code ==}),
     * identity hash code, and synchronization) are unreliable and should be avoided.
     * </ul>
     *
     * @apiNote
     * For a serializable {@code Entry}, see {@link AbstractMap.SimpleEntry} or
     * {@link AbstractMap.SimpleImmutableEntry}.
     *
     * @param <K> the key's type
     * @param <V> the value's type
     * @param k the key
     * @param v the value
     * @return an {@code Entry} containing the specified key and value
     * @throws NullPointerException if the key or value is {@code null}
     *
     * @see MapExt#ofEntries MapExt.ofEntries()
     * @since Java 9
     */
    static <K, V> Map.Entry<K, V> entry(final K k, final V v) {
        // KeyValueHolder checks for nulls
        return new KeyValueHolder<>(k, v);
    }
}
