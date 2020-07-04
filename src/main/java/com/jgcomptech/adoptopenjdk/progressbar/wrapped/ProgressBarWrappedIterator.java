package com.jgcomptech.adoptopenjdk.progressbar.wrapped;

import com.jgcomptech.adoptopenjdk.progressbar.ProgressBar;

import java.util.Iterator;

/**
 * @author Tongfei Chen
 */
public class ProgressBarWrappedIterator<T> implements Iterator<T>, AutoCloseable {

    private final Iterator<? extends T> underlying;
    private final ProgressBar pb;

    public ProgressBarWrappedIterator(final Iterator<? extends T> underlying, final ProgressBar pb) {
        this.underlying = underlying;
        this.pb = pb;
    }

    public ProgressBar getProgressBar() {
        return pb;
    }

    @Override
    public boolean hasNext() {
        final boolean r = underlying.hasNext();
        if (!r) pb.close();
        return r;
    }

    @Override
    public T next() {
        final T r = underlying.next();
        pb.step();
        return r;
    }

    @Override
    public void remove() {
        underlying.remove();
    }

    @Override
    public void close() {
        pb.close();
    }
}
