package com.jgcomptech.adoptopenjdk.utils.progressbar;

import org.jline.terminal.Terminal;

import java.io.IOException;
import java.io.PrintStream;

/**
 * Progress bar consumer that prints the progress bar state to console.
 * By default {@link System#err} is used as {@link PrintStream}.
 * @author Tongfei Chen
 * @author Alex Peelman
 */
@SuppressWarnings("UseOfSystemOutOrSystemErr")
public class ConsoleProgressBarConsumer implements ProgressBarConsumer {

    private static final int consoleRightMargin = 2;
    private final PrintStream out;
    @SuppressWarnings("resource") //Properly closed in close method.
    private final Terminal terminal = Util.getTerminal();

    ConsoleProgressBarConsumer() {
        this(System.err);
    }

    ConsoleProgressBarConsumer(final PrintStream out) {
        this.out = out;
    }

    /**
     * Returns the maximum length allowed for the rendered form of a progress bar.
     */
    @Override
    public int getMaxProgressLength() {
        return Util.getTerminalWidth(terminal) - consoleRightMargin;
    }

    /**
     * Accepts a rendered form of a progress bar, e.g., prints to a specified stream.
     * @param rendered Rendered form of a progress bar, a string
     */
    @Override
    public void accept(final String rendered) {
        out.print("\r"); // before update
        out.print(rendered);
    }

    @Override
    public void close() {
        out.println();
        out.flush();
        if(terminal != null) {
            try {
                terminal.close();
            }
            catch (final IOException ignored) { /* noop */ }
        }
    }
}
