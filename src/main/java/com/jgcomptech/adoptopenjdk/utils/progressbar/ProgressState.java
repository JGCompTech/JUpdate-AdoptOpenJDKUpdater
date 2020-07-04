package com.jgcomptech.adoptopenjdk.utils.progressbar;

import java.time.Instant;

/**
 * Encapsulates the internal states of a progress bar.
 * @author Tongfei Chen
 */
public class ProgressState {
    final String task;
    boolean indefinite;
    long current;
    long max;
    Instant startTime;
    String extraMessage = "";

    ProgressState(final String task, final long initialMax) {
        this.task = task;
        max = initialMax;
        if (initialMax < 0) indefinite = true;
    }

    synchronized void setAsDefinite() {
        indefinite = false;
    }

    synchronized void setAsIndefinite() {
        indefinite = true;
    }

    synchronized void maxHint(final long n) {
        max = n;
    }

    synchronized void stepBy(final long n) {
        current += n;
        if (current > max) max = current;
    }

    synchronized void stepTo(final long n) {
        current = n;
        if (current > max) max = current;
    }

    synchronized void setExtraMessage(final String msg) {
        extraMessage = msg;
    }

    String getTask() {
        return task;
    }

    synchronized String getExtraMessage() {
        return extraMessage;
    }

    synchronized long getCurrent() {
        return current;
    }

    synchronized long getMax() {
        return max;
    }

    // The progress, normalized to range [0, 1].
    synchronized double getNormalizedProgress() {
        if (max <= 0) return 0.0;
        return ((double)current) / max;
    }

}
