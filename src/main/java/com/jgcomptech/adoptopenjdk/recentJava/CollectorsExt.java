package com.jgcomptech.adoptopenjdk.recentJava;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

/**
 * Extends the Java 8 implementations of the Collectors class to add methods that were added in Java 9 and Java 10.
 */
public final class CollectorsExt {
    private static final Set<Collector.Characteristics> CH_NOID = Collections.emptySet();
    private static final Set<Collector.Characteristics> CH_UNORDERED_NOID
            = Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.UNORDERED));

    private CollectorsExt() { }

    /**
     * Simple implementation class for {@code Collector}.
     *
     * @param <T> the type of elements to be collected
     * @param <R> the type of the result
     */
    static class CollectorImpl<T, A, R> implements Collector<T, A, R> {
        private final Supplier<A> supplier;
        private final BiConsumer<A, T> accumulator;
        private final BinaryOperator<A> combiner;
        private final Function<A, R> finisher;
        private final Set<Characteristics> characteristics;

        CollectorImpl(final Supplier<A> supplier,
                      final BiConsumer<A, T> accumulator,
                      final BinaryOperator<A> combiner,
                      final Function<A,R> finisher,
                      final Set<Characteristics> characteristics) {
            this.supplier = supplier;
            this.accumulator = accumulator;
            this.combiner = combiner;
            this.finisher = finisher;
            this.characteristics = characteristics;
        }

        CollectorImpl(final Supplier<A> supplier,
                      final BiConsumer<A, T> accumulator,
                      final BinaryOperator<A> combiner,
                      final Set<Characteristics> characteristics) {
            this(supplier, accumulator, combiner, castingIdentity(), characteristics);
        }

        @Override
        public BiConsumer<A, T> accumulator() {
            return accumulator;
        }

        @Override
        public Supplier<A> supplier() {
            return supplier;
        }

        @Override
        public BinaryOperator<A> combiner() {
            return combiner;
        }

        @Override
        public Function<A, R> finisher() {
            return finisher;
        }

        @Override
        public Set<Characteristics> characteristics() {
            return characteristics;
        }
    }

    @SuppressWarnings("unchecked")
    private static <I, R> Function<I, R> castingIdentity() {
        return i -> (R) i;
    }

    /**
     * Adapts a {@code Collector} accepting elements of type {@code U} to one
     * accepting elements of type {@code T} by applying a flat mapping function
     * to each input element before accumulation.  The flat mapping function
     * maps an input element to a {@link Stream stream} covering zero or more
     * output elements that are then accumulated downstream.  Each mapped stream
     * is {@link java.util.stream.BaseStream#close() closed} after its contents
     * have been placed downstream.  (If a mapped stream is {@code null}
     * an empty stream is used, instead.)
     *
     * @apiNote
     * The {@code flatMapping()} collectors are most useful when used in a
     * multi-level reduction, such as downstream of a {@code groupingBy} or
     * {@code partitioningBy}.  For example, given a stream of
     * {@code Order}, to accumulate the set of line items for each customer:
     * <pre>{@code
     * Map<String, Set<LineItem>> itemsByCustomerName
     *   = orders.stream().collect(
     *     groupingBy(Order::getCustomerName,
     *                flatMapping(order -> order.getLineItems().stream(),
     *                            toSet())));
     * }</pre>
     *
     * @param <T> the type of the input elements
     * @param <U> type of elements accepted by downstream collector
     * @param <A> intermediate accumulation type of the downstream collector
     * @param <R> result type of collector
     * @param mapper a function to be applied to the input elements, which
     * returns a stream of results
     * @param downstream a collector which will receive the elements of the
     * stream returned by mapper
     * @return a collector which applies the mapping function to the input
     * elements and provides the flat mapped results to the downstream collector
     * @since Java 9
     */
    public static <T, U, A, R>
    Collector<T, ?, R> flatMapping(final Function<? super T, ? extends Stream<? extends U>> mapper,
                                   final Collector<? super U, A, R> downstream) {
        final BiConsumer<A, ? super U> downstreamAccumulator = downstream.accumulator();
        return new CollectorImpl<>(downstream.supplier(),
                (r, t) -> {
                    try (final Stream<? extends U> result = mapper.apply(t)) {
                        if (result != null)
                            result.sequential().forEach(u -> downstreamAccumulator.accept(r, u));
                    }
                },
                downstream.combiner(), downstream.finisher(),
                downstream.characteristics());
    }

    /**
     * Adapts a {@code Collector} to one accepting elements of the same type
     * {@code T} by applying the predicate to each input element and only
     * accumulating if the predicate returns {@code true}.
     *
     * @apiNote
     * The {@code filtering()} collectors are most useful when used in a
     * multi-level reduction, such as downstream of a {@code groupingBy} or
     * {@code partitioningBy}.  For example, given a stream of
     * {@code Employee}, to accumulate the employees in each department that have a
     * salary above a certain threshold:
     * <pre>{@code
     * Map<Department, Set<Employee>> wellPaidEmployeesByDepartment
     *   = employees.stream().collect(
     *     groupingBy(Employee::getDepartment,
     *                filtering(e -> e.getSalary() > 2000,
     *                          toSet())));
     * }</pre>
     * A filtering collector differs from a stream's {@code filter()} operation.
     * In this example, suppose there are no employees whose salary is above the
     * threshold in some department.  Using a filtering collector as shown above
     * would result in a mapping from that department to an empty {@code Set}.
     * If a stream {@code filter()} operation were done instead, there would be
     * no mapping for that department at all.
     *
     * @param <T> the type of the input elements
     * @param <A> intermediate accumulation type of the downstream collector
     * @param <R> result type of collector
     * @param predicate a predicate to be applied to the input elements
     * @param downstream a collector which will accept values that match the
     * predicate
     * @return a collector which applies the predicate to the input elements
     * and provides matching elements to the downstream collector
     * @since Java 9
     */
    public static <T, A, R>
    Collector<T, ?, R> filtering(final Predicate<? super T> predicate,
                                 final Collector<? super T, A, R> downstream) {
        final BiConsumer<A, ? super T> downstreamAccumulator = downstream.accumulator();
        return new CollectorImpl<>(downstream.supplier(),
                (r, t) -> {
                    if (predicate.test(t)) {
                        downstreamAccumulator.accept(r, t);
                    }
                },
                downstream.combiner(), downstream.finisher(),
                downstream.characteristics());
    }

    /**
     * Returns a {@code Collector} that accumulates the input elements into an
     * <a href="../List.html#unmodifiable">unmodifiable List</a> in encounter
     * order. The returned Collector disallows null values and will throw
     * {@code NullPointerException} if it is presented with a null value.
     *
     * @param <T> the type of the input elements
     * @return a {@code Collector} that accumulates the input elements into an
     * <a href="../List.html#unmodifiable">unmodifiable List</a> in encounter order
     * @since Java 10
     */
    @SuppressWarnings("unchecked")
    public static <T>
    Collector<T, ?, List<T>> toUnmodifiableList() {
        return new CollectorImpl<>((Supplier<List<T>>) ArrayList::new, List::add,
                (left, right) -> { left.addAll(right); return left; },
                list -> (List<T>)ListExt.of(list.toArray()),
                CH_NOID);
    }

    /**
     * Returns a {@code Collector} that accumulates the input elements into an
     * <a href="../Set.html#unmodifiable">unmodifiable Set</a>. The returned
     * Collector disallows null values and will throw {@code NullPointerException}
     * if it is presented with a null value. If the input contains duplicate elements,
     * an arbitrary element of the duplicates is preserved.
     *
     * <p>This is an {@link Collector.Characteristics#UNORDERED unordered}
     * Collector.
     *
     * @param <T> the type of the input elements
     * @return a {@code Collector} that accumulates the input elements into an
     * <a href="../Set.html#unmodifiable">unmodifiable Set</a>
     * @since Java 10
     */
    @SuppressWarnings("unchecked")
    public static <T>
    Collector<T, ?, Set<T>> toUnmodifiableSet() {
        return new CollectorImpl<>((Supplier<Set<T>>) HashSet::new, Set::add,
                (left, right) -> {
                    if (left.size() < right.size()) {
                        right.addAll(left);
                        return right;
                    }
                    left.addAll(right);
                    return left;
                },
                set -> (Set<T>) SetExt.of(set.toArray()),
                CH_UNORDERED_NOID);
    }

    /**
     * Returns a {@code Collector} that accumulates the input elements into an
     * <a href="../Map.html#unmodifiable">unmodifiable Map</a>,
     * whose keys and values are the result of applying the provided
     * mapping functions to the input elements.
     *
     * <p>If the mapped keys contain duplicates (according to
     * {@link Object#equals(Object)}), an {@code IllegalStateException} is
     * thrown when the collection operation is performed.  If the mapped keys
     * might have duplicates, use {@link #toUnmodifiableMap(Function, Function, BinaryOperator)}
     * to handle merging of the values.
     *
     * <p>The returned Collector disallows null keys and values. If either mapping function
     * returns null, {@code NullPointerException} will be thrown.
     *
     * @param <T> the type of the input elements
     * @param <K> the output type of the key mapping function
     * @param <U> the output type of the value mapping function
     * @param keyMapper a mapping function to produce keys, must be non-null
     * @param valueMapper a mapping function to produce values, must be non-null
     * @return a {@code Collector} that accumulates the input elements into an
     * <a href="../Map.html#unmodifiable">unmodifiable Map</a>, whose keys and values
     * are the result of applying the provided mapping functions to the input elements
     * @throws NullPointerException if either keyMapper or valueMapper is null
     *
     * @see #toUnmodifiableMap(Function, Function, BinaryOperator)
     * @since Java 10
     */
    @SuppressWarnings({"unchecked"})
    public static <T, K, U>
    Collector<T, ?, Map<K,U>> toUnmodifiableMap(final Function<? super T, ? extends K> keyMapper,
                                                final Function<? super T, ? extends U> valueMapper) {
        Objects.requireNonNull(keyMapper, "keyMapper");
        Objects.requireNonNull(valueMapper, "valueMapper");
        return Collectors.collectingAndThen(
                toMap(keyMapper, valueMapper),
                map -> (Map<K,U>)MapExt.ofEntries(map.entrySet().toArray(new Map.Entry[0])));
    }

    /**
     * Returns a {@code Collector} that accumulates the input elements into an
     * <a href="../Map.html#unmodifiable">unmodifiable Map</a>,
     * whose keys and values are the result of applying the provided
     * mapping functions to the input elements.
     *
     * <p>If the mapped
     * keys contain duplicates (according to {@link Object#equals(Object)}),
     * the value mapping function is applied to each equal element, and the
     * results are merged using the provided merging function.
     *
     * <p>The returned Collector disallows null keys and values. If either mapping function
     * returns null, {@code NullPointerException} will be thrown.
     *
     * @param <T> the type of the input elements
     * @param <K> the output type of the key mapping function
     * @param <U> the output type of the value mapping function
     * @param keyMapper a mapping function to produce keys, must be non-null
     * @param valueMapper a mapping function to produce values, must be non-null
     * @param mergeFunction a merge function, used to resolve collisions between
     *                      values associated with the same key, as supplied
     *                      to {@link Map#merge(Object, Object, BiFunction)},
     *                      must be non-null
     * @return a {@code Collector} that accumulates the input elements into an
     * <a href="../Map.html#unmodifiable">unmodifiable Map</a>, whose keys and values
     * are the result of applying the provided mapping functions to the input elements
     * @throws NullPointerException if the keyMapper, valueMapper, or mergeFunction is null
     *
     * @see #toUnmodifiableMap(Function, Function)
     * @since Java 10
     */
    @SuppressWarnings({"unchecked"})
    public static <T, K, U>
    Collector<T, ?, Map<K, U>> toUnmodifiableMap(final Function<? super T, ? extends K> keyMapper,
                                                final Function<? super T, ? extends U> valueMapper,
                                                final BinaryOperator<U> mergeFunction) {
        Objects.requireNonNull(keyMapper, "keyMapper");
        Objects.requireNonNull(valueMapper, "valueMapper");
        Objects.requireNonNull(mergeFunction, "mergeFunction");
        return Collectors.collectingAndThen(
                toMap(keyMapper, valueMapper, mergeFunction, HashMap::new),
                map -> (Map<K,U>)MapExt.ofEntries(map.entrySet().toArray(new Map.Entry[0])));
    }
}
