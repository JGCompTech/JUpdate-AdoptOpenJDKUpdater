package com.jgcomptech.adoptopenjdk.utils.progressbar;

import java.text.DecimalFormat;
import java.time.Duration;
import java.time.Instant;

/**
 * Default progress bar renderer (see {@link ProgressBarRenderer}).
 * @author Tongfei Chen
 */
public class DefaultProgressBarRenderer implements ProgressBarRenderer {

    private final ProgressBarStyle style;
    private final String unitName;
    private final long unitSize;
    private final boolean isSpeedShown;
    private final DecimalFormat speedFormat;

    DefaultProgressBarRenderer(
            final ProgressBarStyle style,
            final String unitName,
            final long unitSize,
            final boolean isSpeedShown,
            final DecimalFormat speedFormat
    ) {
        this.style = style;
        this.unitName = unitName;
        this.unitSize = unitSize;
        this.isSpeedShown = isSpeedShown;
        this.speedFormat = speedFormat;
    }

    // Number of full blocks
    private int progressIntegralPart(final ProgressState progress, final int length) {
        return (int)(progress.getNormalizedProgress() * length);
    }

    private int progressFractionalPart(final ProgressState progress, final int length) {
        final double p = progress.getNormalizedProgress() * length;
        final double fraction = (p - Math.floor(p)) * style.fractionSymbols.length();
        return (int) Math.floor(fraction);
    }

    private String eta(final ProgressState progress, final Duration elapsed) {
        if (progress.max <= 0 || progress.indefinite) return "?";
        if (progress.current == 0) return "?";
        return Util.formatDuration(
                    elapsed.dividedBy(progress.current).multipliedBy(progress.max - progress.current)
            );
    }

    private String percentage(final ProgressState progress) {
        final String res;
        if (progress.max <= 0 || progress.indefinite) res = "? %";
        else res = (int) Math.floor(100.0 * progress.current / progress.max) + "%";
        return Util.repeat(' ', 4 - res.length()) + res;
    }

    @SuppressWarnings("HardcodedFileSeparator")
    private String ratio(final ProgressState progress) {
        final String m = progress.indefinite ? "?" : String.valueOf(progress.max / unitSize);
        final String c = String.valueOf(progress.current / unitSize);
        return Util.repeat(' ', m.length() - c.length()) + c + '/' + m + unitName;
    }

    @SuppressWarnings("HardcodedFileSeparator")
    private String speed(final ProgressState progress, final Duration elapsed) {
        if (elapsed.getSeconds() == 0) return '?' + unitName + "/s";
        final double speed = (double) progress.current / elapsed.getSeconds();
        final double speedWithUnit = speed / unitSize;
        return speedFormat.format(speedWithUnit) + unitName + "/s";
    }

    /**
     * Renders the current progress bar state as a string to be shown by a consumer.
     * @param progress The current progress bar state
     * @param maxLength The maximum length as dictated by the consumer
     * @return Rendered string to be consumed by the consumer
     */
    @SuppressWarnings("HardcodedFileSeparator")
    @Override
    public String render(final ProgressState progress, final int maxLength) {
        final Instant currTime = Instant.now();
        final Duration elapsed = Duration.between(progress.startTime, currTime);

        final String prefix = progress.task + ' ' + percentage(progress) + ' ' + style.leftBracket;
        final int maxSuffixLength = Math.max(maxLength - prefix.length(), 0);

        final String speedString = isSpeedShown ? speed(progress, elapsed) : "";
        String suffix = style.rightBracket + ' ' + ratio(progress) + " ("
                + Util.formatDuration(elapsed) + " / " + eta(progress, elapsed) + ") "
                + speedString + progress.extraMessage;
        // trim excessive suffix
        if (suffix.length() > maxSuffixLength)
            suffix = suffix.substring(0, maxSuffixLength);

        final int length = maxLength - prefix.length() - suffix.length();

        final StringBuilder sb = new StringBuilder();
        sb.append(prefix);

        // case of indefinite progress bars
        if (progress.indefinite) {
            final int pos = (int)(progress.current % length);
            sb.append(Util.repeat(style.space, pos));
            sb.append(style.block);
            sb.append(Util.repeat(style.space, length - pos - 1));
        }
        // case of definite progress bars
        else {
            sb.append(Util.repeat(style.block, progressIntegralPart(progress, length)));
            if (progress.current < progress.max) {
                sb.append(style.fractionSymbols.charAt(progressFractionalPart(progress, length)));
                sb.append(Util.repeat(style.space, length - progressIntegralPart(progress, length) - 1));
            }
        }

        sb.append(suffix);
        return sb.toString();
    }
}
