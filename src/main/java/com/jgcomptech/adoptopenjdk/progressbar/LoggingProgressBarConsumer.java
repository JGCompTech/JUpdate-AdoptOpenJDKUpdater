package com.jgcomptech.adoptopenjdk.progressbar;

import org.jline.terminal.Terminal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static com.jgcomptech.adoptopenjdk.Consts.NEW_LINE;

public class LoggingProgressBarConsumer implements ProgressBarConsumer {
    private static final int consoleRightMargin = 2;
    private final Logger logger;
    @SuppressWarnings("resource") //Properly closed in close method.
    private final Terminal terminal = Util.getTerminal();

    LoggingProgressBarConsumer() {
        this(LoggerFactory.getLogger(LoggingProgressBarConsumer.class));
    }

    LoggingProgressBarConsumer(final Logger logger) {
        this.logger = logger;
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
        logger.info(NEW_LINE); // before update
        logger.info(rendered);
    }

    @Override
    public void close() {
        logger.info(NEW_LINE);
        if(terminal != null) {
            try {
                terminal.close();
            }
            catch (final IOException ignored) { /* noop */ }
        }
    }
}
