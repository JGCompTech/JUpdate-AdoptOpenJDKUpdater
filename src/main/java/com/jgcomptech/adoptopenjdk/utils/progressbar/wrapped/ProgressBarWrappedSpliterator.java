package com.jgcomptech.adoptopenjdk.utils.progressbar.wrapped;

import com.jgcomptech.adoptopenjdk.utils.progressbar.ProgressBar;

import java.util.*;
import java.util.function.Consumer;

/**
 * @author Tongfei Chen
 */
public class ProgressBarWrappedSpliterator<T> implements Spliterator<T>, AutoCloseable {

    private final Spliterator<T> underlying;
    private final ProgressBar pb;
    private final Set<Spliterator<T>> openChildren;

    public ProgressBarWrappedSpliterator(final Spliterator<T> underlying, final ProgressBar pb) {
        this(underlying, pb, Collections.synchronizedSet(new HashSet<>())); // has to be synchronized
    }

    private ProgressBarWrappedSpliterator(final Spliterator<T> underlying, final ProgressBar pb, final Set<Spliterator<T>> openChildren) {
        this.underlying = underlying;
        this.pb = pb;
        this.openChildren = openChildren;
        this.openChildren.add(this);
    }

    public ProgressBar getProgressBar() {
        return pb;
    }

    @Override
    public void close() {
        pb.close();
    }

    private void registerChild(final Spliterator<T> child) {
        openChildren.add(child);
    }

    private void removeThis() {
        openChildren.remove(this);
        if (openChildren.isEmpty()) close();
        // only closes the progressbar if no spliterator is working anymore
    }

    @Override
    public boolean tryAdvance(final Consumer<? super T> action) {
        final boolean r = underlying.tryAdvance(action);
        if (r) pb.step();
        else removeThis();
        return r;
    }

    @Override
    public Spliterator<T> trySplit() {
        final Spliterator<T> u = underlying.trySplit();
        if (u != null) {
            final Spliterator<T> child = new ProgressBarWrappedSpliterator<>(u, pb, openChildren);
            registerChild(child);
            return child;
        }
        return null;
    }

    @Override
    public long estimateSize() {
        return underlying.estimateSize();
    }

    @Override
    public int characteristics() {
        return underlying.characteristics();
    }

    @Override // if not overridden, may return null since that is the default Spliterator implementation
    public Comparator<? super T> getComparator() {
        return underlying.getComparator();
    }
}
