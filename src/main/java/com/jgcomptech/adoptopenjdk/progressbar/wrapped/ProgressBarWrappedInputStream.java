package com.jgcomptech.adoptopenjdk.progressbar.wrapped;

import com.jgcomptech.adoptopenjdk.progressbar.ProgressBar;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Any input stream whose progress is tracked by a progress bar.
 * @author Tongfei Chen
 */
public class ProgressBarWrappedInputStream extends FilterInputStream {

    private final ProgressBar pb;
    private long mark;

    public ProgressBarWrappedInputStream(final InputStream in, final ProgressBar pb) {
        super(in);
        this.pb = pb;
    }

    public ProgressBar getProgressBar() {
        return pb;
    }

    @Override
    public int read() throws IOException {
        final int r = in.read();
        if (r != -1) pb.step();
        return r;
    }

    @Override
    public int read(final byte[] b) throws IOException {
        final int r = in.read(b);
        if (r != -1) pb.stepBy(r);
        return r;
    }

    @Override
    public int read(final byte[] b, final int off, final int len) throws IOException {
        final int r = in.read(b, off, len);
        if (r != -1) pb.stepBy(r);
        return r;
    }

    @Override
    public long skip(final long n) throws IOException {
        final long r = in.skip(n);
        pb.stepBy(r);
        return r;
    }

    @Override
    public void mark(final int readLimit) {
        in.mark(readLimit);
        mark = pb.getCurrent();
    }

    @Override
    public void reset() throws IOException {
        in.reset();
        pb.stepTo(mark);
    }

    @Override
    public void close() throws IOException {
        in.close();
        pb.close();
    }

}
