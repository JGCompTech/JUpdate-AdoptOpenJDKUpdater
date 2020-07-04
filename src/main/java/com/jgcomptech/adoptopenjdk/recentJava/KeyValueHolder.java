package com.jgcomptech.adoptopenjdk.recentJava;

import java.util.Map;
import java.util.Objects;

/**
 * An immutable container for a key and a value, suitable for use
 * in creating and populating {@code Map} instances.
 *
 * <p>This is a <a href="../lang/doc-files/ValueBased.html">value-based</a>
 * class; use of identity-sensitive operations (including reference equality
 * ({@code ==}), identity hash code, or synchronization) on instances of
 * {@code KeyValueHolder} may have unpredictable results and should be avoided.
 *
 * @apiNote
 * This class is not public. Instances can be created using the
 * {@link MapExt#entry Map.entry(k, v)} factory method, which is public.
 *
 * <p>This class differs from AbstractMap.SimpleImmutableEntry in the following ways:
 * it is not serializable, it is final, and its key and value must be non-null.
 *
 * @param <K> the key type
 * @param <V> the value type
 *
 * @see MapExt#ofEntries MapExt.ofEntries()
 * @since 9
 */
final class KeyValueHolder<K,V> implements Map.Entry<K,V> {
    private final K key;
    private final V value;

    KeyValueHolder(final K k, final V v) {
        key = Objects.requireNonNull(k);
        value = Objects.requireNonNull(v);
    }

    /**
     * Gets the key from this holder.
     *
     * @return the key
     */
    @Override
    public K getKey() {
        return key;
    }

    /**
     * Gets the value from this holder.
     *
     * @return the value
     */
    @Override
    public V getValue() {
        return value;
    }

    /**
     * Throws {@link UnsupportedOperationException}.
     *
     * @param value ignored
     * @return never returns normally
     */
    @Override
    public V setValue(final V value) {
        throw new UnsupportedOperationException("not supported");
    }

    /**
     * Compares the specified object with this entry for equality.
     * Returns {@code true} if the given object is also a map entry and
     * the two entries' keys and values are equal. Note that key and
     * value are non-null, so equals() can be called safely on them.
     */
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof Map.Entry))
            return false;
        final Map.Entry<?,?> e = (Map.Entry<?,?>)o;
        return key.equals(e.getKey()) && value.equals(e.getValue());
    }

    /**
     * Returns the hash code value for this map entry. The hash code
     * is {@code key.hashCode() ^ value.hashCode()}. Note that key and
     * value are non-null, so hashCode() can be called safely on them.
     */
    @Override
    public int hashCode() {
        return key.hashCode() ^ value.hashCode();
    }

    /**
     * Returns a String representation of this map entry.  This
     * implementation returns the string representation of this
     * entry's key followed by the equals character ("{@code =}")
     * followed by the string representation of this entry's value.
     *
     * @return a String representation of this map entry
     */
    @Override
    public String toString() {
        return key + "=" + value;
    }
}

