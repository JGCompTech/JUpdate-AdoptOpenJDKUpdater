package com.jgcomptech.adoptopenjdk.progressbar.wrapped;

import com.jgcomptech.adoptopenjdk.progressbar.ProgressBarBuilder;

import java.util.Iterator;

/**
 * @author Tongfei Chen
 */
public class ProgressBarWrappedIterable<T> implements Iterable<T> {

    private final Iterable<T> underlying;
    private final ProgressBarBuilder pbb;

    public ProgressBarWrappedIterable(final Iterable<T> underlying, final ProgressBarBuilder pbb) {
        this.underlying = underlying;
        this.pbb = pbb;
    }

    public ProgressBarBuilder getProgressBarBuilder() {
        return pbb;
    }

    @Override
    public Iterator<T> iterator() {
        final Iterator<T> it = underlying.iterator();
        return new ProgressBarWrappedIterator<>(
                it,
                pbb.setInitialMax(underlying.spliterator().getExactSizeIfKnown()).build()
                // getExactSizeIfKnown return -1 if not known, then indefinite progress bar naturally
        );
    }
}
