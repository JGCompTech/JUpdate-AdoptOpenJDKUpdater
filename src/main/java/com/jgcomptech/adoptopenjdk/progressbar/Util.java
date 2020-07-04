package com.jgcomptech.adoptopenjdk.progressbar;

import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;

/**
 * @author Tongfei Chen
 */
final class Util {

    private static final int defaultTerminalWidth = 80;

    private Util() { }

    static String repeat(final char c, final int n) {
        if (n <= 0) return "";
        final char[] s = new char[n];
        for (int i = 0; i < n; i++) s[i] = c;
        return new String(s);
    }

    static String formatDuration(final Duration d) {
        final long s = d.getSeconds();
        return String.format("%d:%02d:%02d", s / 3600, (s % 3600) / 60, s % 60);
    }

    static long getInputStreamSize(final InputStream is) {
        try {
            if (is instanceof FileInputStream)
                return ((FileInputStream) is).getChannel().size();
        }
        catch (final IOException e) {
            return -1;
        }
        return -1;
    }

    static Terminal getTerminal() {
        try {
            // Issue #42
            // This property's purpose is to disallow (false)/warn (null)/allow (true) defaulting
            // to a dumb terminal when a supported terminal can not be correctly created.
            // see https://github.com/jline/jline3/issues/291
            return TerminalBuilder.builder().dumb(true).build();
        }
        catch (final IOException ignored) { }
        return null;
    }

    static int getTerminalWidth(final Terminal terminal) {
        if (terminal != null && terminal.getWidth() >= 10) // Workaround for issue #23 under IntelliJ
            return terminal.getWidth();
        return defaultTerminalWidth;
    }

    static int getTerminalWidth() {
        final Terminal terminal = getTerminal();
        final int width = getTerminalWidth(terminal);
        try {
            if (terminal != null)
                terminal.close();
        }
        catch (final IOException ignored) { /* noop */ }
        return width;
    }

}
