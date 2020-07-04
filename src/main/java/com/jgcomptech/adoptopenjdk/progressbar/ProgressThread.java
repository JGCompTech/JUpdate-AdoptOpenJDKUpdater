package com.jgcomptech.adoptopenjdk.progressbar;

/**
 * @author Tongfei Chen
 * @since 0.5.0
 */
class ProgressThread implements Runnable {

    private final ProgressState progress;
    private final ProgressBarRenderer renderer;
    private final long updateInterval;
    private final ProgressBarConsumer consumer;

    ProgressThread(
            final ProgressState progress,
            final ProgressBarRenderer renderer,
            final long updateInterval,
            final ProgressBarConsumer consumer
    ) {
        this.progress = progress;
        this.renderer = renderer;
        this.updateInterval = updateInterval;
        this.consumer = consumer;
    }

    private void refresh() {
        final String rendered = renderer.render(progress, consumer.getMaxProgressLength());
        consumer.accept(rendered);
    }

    void closeConsumer() {
        consumer.close();
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                refresh();
                Thread.sleep(updateInterval);
            }
        } catch (final InterruptedException ignored) {
            refresh();
            // force refreshing after being interrupted
        }
    }

}
