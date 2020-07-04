package com.jgcomptech.adoptopenjdk.utils.progressbar;

import java.util.function.Consumer;

/**
 * Progress bar consumer that delegates the progress bar handling to a custom {@link Consumer}.
 * @author Alex Peelman
 * @since 0.8.0
 */
public class DelegatingProgressBarConsumer implements ProgressBarConsumer {

    private final int maxProgressLength;
    private final Consumer<? super String> consumer;

    public DelegatingProgressBarConsumer(final Consumer<? super String> consumer) {
        this(consumer, Util.getTerminalWidth());
    }

    public DelegatingProgressBarConsumer(final Consumer<? super String> consumer, final int maxProgressLength) {
        this.maxProgressLength = maxProgressLength;
        this.consumer = consumer;
    }

    /**
     * Returns the maximum length allowed for the rendered form of a progress bar.
     */
    @Override
    public int getMaxProgressLength() {
        return maxProgressLength;
    }

    /**
     * Accepts a rendered form of a progress bar, e.g., prints to a specified stream.
     * @param rendered Rendered form of a progress bar, a string
     */
    @Override
    public void accept(final String rendered) {
        consumer.accept(rendered);
    }

    @Override
    public void close() {
        //NOOP
    }
}
