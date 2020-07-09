package com.jgcomptech.adoptopenjdk.utils.progressbar;

import java.text.DecimalFormat;

/**
 * Builder class for {@link ProgressBar}s.
 * @author Tongfei Chen
 */
public class ProgressBarBuilder {
    private String task = "";
    private long initialMax = -1;
    private int updateIntervalMillis = 1000;
    private ProgressBarStyle style = ProgressBarStyle.COLORFUL_UNICODE_BLOCK;
    private ProgressBarConsumer consumer;
    private ProgressBarRenderer renderer;
    private String unitName = "";
    private long unitSize = 1;
    private boolean showSpeed;
    private DecimalFormat speedFormat;

    public ProgressBarBuilder setTaskName(final String task) {
        this.task = task;
        return this;
    }

    public ProgressBarBuilder setInitialMax(final long initialMax) {
        this.initialMax = initialMax;
        return this;
    }

    public ProgressBarBuilder setStyle(final ProgressBarStyle style) {
        this.style = style;
        return this;
    }

    public ProgressBarBuilder setUpdateIntervalMillis(final int updateIntervalMillis) {
        this.updateIntervalMillis = updateIntervalMillis;
        return this;
    }

    public ProgressBarBuilder setConsumer(final ProgressBarConsumer consumer) {
        this.consumer = consumer;
        return this;
    }

    public ProgressBarBuilder useFileProgressBarRenderer() {
        renderer = new FileProgressBarRenderer(style, unitName, unitSize, showSpeed, speedFormat);
        return this;
    }

    public ProgressBarBuilder useDefaultProgressBarRenderer() {
        renderer = new DefaultProgressBarRenderer(style, unitName, unitSize, showSpeed, speedFormat);
        return this;
    }

    public void setRenderer(final ProgressBarRenderer renderer) {
        this.renderer = renderer;
    }

    public ProgressBarBuilder setUnit(final String unitName, final long unitSize) {
        this.unitName = unitName;
        this.unitSize = unitSize;
        return this;
    }

    public ProgressBarBuilder showSpeed() {
        return showSpeed(new DecimalFormat("#.0"));
    }

    public ProgressBarBuilder showSpeed(final DecimalFormat speedFormat) {
        showSpeed = true;
        this.speedFormat = speedFormat;
        return this;
    }

    public ProgressBar build() {
        if(consumer == null)
            consumer = new ConsoleProgressBarConsumer();
        if(renderer == null) {
           renderer = new DefaultProgressBarRenderer(style, unitName, unitSize, showSpeed, speedFormat);
        }

        return new ProgressBar(
                task,
                initialMax,
                updateIntervalMillis,
                renderer,
                consumer
        );
    }
}
