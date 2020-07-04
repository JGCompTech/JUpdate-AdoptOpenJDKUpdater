package com.jgcomptech.adoptopenjdk.utils.logging;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;

public enum Appenders {
    /** A console extender that uses the {@link Encoders#LimitedEncoder}. */
    LimitedConsoleAppender(LoggingManager.createNewConsoleAppender(Encoders.LimitedEncoder)),
    /** A console extender that uses the {@link Encoders#BasicEncoder}. */
    BasicConsoleAppender(LoggingManager.createNewConsoleAppender(Encoders.BasicEncoder)),
    /** A console extender that uses the {@link Encoders#ExtendedEncoder}. */
    ExtendedConsoleAppender(LoggingManager.createNewConsoleAppender(Encoders.ExtendedEncoder)),
    ;

    private final ConsoleAppender<ILoggingEvent> appender;

    Appenders(final ConsoleAppender<ILoggingEvent> appender) {
        this.appender = appender;
    }

    public ConsoleAppender<ILoggingEvent> getAppender() {
        return appender;
    }
}
