package com.jgcomptech.adoptopenjdk.utils.logging;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.ConsoleAppender;
import com.jgcomptech.adoptopenjdk.utils.Literals;

import static com.jgcomptech.adoptopenjdk.utils.StringUtils.isBlank;
import static com.jgcomptech.adoptopenjdk.utils.Utils.checkArgumentNotNullOrEmpty;

/**
 * Contains methods to manage a Logback logger instance.
 */
@SuppressWarnings("ClassHasNoToStringMethod")
public class LoggerConfig {
    private final Logger logger;

    /**
     * Creates a new instance of LoggerConfig with the specified logger object.
     * @param logger the logger object to set
     */
    public LoggerConfig(final Logger logger) {
        this.logger = logger;
        disable();
    }

    /**
     * Creates a new instance of LoggerConfig with the specified logger name.
     * @param className the name of the logger object to set
     */
    public LoggerConfig(final String className) {
        logger = LoggingManager.getContext().getLogger(className);
        disable();
    }

    /**
     * Enables the logger to use the specified log level.
     * @apiNote Additive is automatically disabled in this method
     * @param logLevel the log level to set
     * @return this instance
     */
    public LoggerConfig enable(final Level logLevel) {
        logger.setAdditive(false);
        logger.setLevel(logLevel);
        return this;
    }

    /**
     * Enables the logger to use the specified log level and adds
     * the {@link Appenders#LimitedConsoleAppender}.
     * @apiNote Additive is automatically disabled in this method
     * @param logLevel the log level to set
     * @return this instance
     */
    public LoggerConfig enableLimitedConsole(final Level logLevel) {
        logger.setAdditive(false);
        logger.setLevel(logLevel);
        if(!logger.isAttached(Appenders.LimitedConsoleAppender.getAppender())) {
            logger.addAppender(Appenders.LimitedConsoleAppender.getAppender());
        }
        return this;
    }

    /**
     * Enables the logger to use the specified log level and adds
     * the {@link Appenders#BasicConsoleAppender}.
     * @apiNote Additive is automatically disabled in this method
     * @param logLevel the log level to set
     * @return this instance
     */
    public LoggerConfig enableBasicConsole(final Level logLevel) {
        logger.setAdditive(false);
        logger.setLevel(logLevel);
        if(!logger.isAttached(Appenders.BasicConsoleAppender.getAppender())) {
            logger.addAppender(Appenders.BasicConsoleAppender.getAppender());
        }
        return this;
    }

    /**
     * Enables the logger to use the specified log level and adds
     * the {@link Appenders#ExtendedConsoleAppender}.
     * @apiNote Additive is automatically disabled in this method
     * @param logLevel the log level to set
     * @return this instance
     */
    public LoggerConfig enableExtendedConsole(final Level logLevel) {
        logger.setAdditive(false);
        logger.setLevel(logLevel);
        if(!logger.isAttached(Appenders.ExtendedConsoleAppender.getAppender())) {
            logger.addAppender(Appenders.ExtendedConsoleAppender.getAppender());
        }
        return this;
    }

    /**
     * Disables the logger by setting the log level to {@link Level#OFF}.
     * @apiNote Additive is automatically disabled in this method
     * @return this instance
     */
    public LoggerConfig disable() {
        logger.setAdditive(false);
        logger.setLevel(Level.OFF);
        return this;
    }

    /**
     * Adds the specified {@link Appender} to the logger.
     * @apiNote Additive is automatically disabled in this method
     * @param appender the appender to add
     * @return this instance
     */
    public LoggerConfig addAppender(final Appenders appender) {
        logger.setAdditive(false);
        if(!logger.isAttached(appender.getAppender())) {
            logger.addAppender(appender.getAppender());
        }
        return this;
    }

    /**
     * Adds the specified {@link Appender} to the logger.
     * @apiNote Additive is automatically disabled in this method
     * @param newAppender the appender to add
     * @return this instance
     */
    public LoggerConfig addAppender(final Appender<ILoggingEvent> newAppender) {
        logger.setAdditive(false);
        if(!logger.isAttached(newAppender)) {
            logger.addAppender(newAppender);
        }
        return this;
    }

    /**
     * Adds a new {@link ConsoleAppender} with the encoder from {@link Encoders#BasicEncoder}
     * and sets the name to "console".
     * @apiNote The start method is automatically called at the end of this method.
     * @return this instance
     */
    public LoggerConfig addNewConsoleAppender() {
        return addNewConsoleAppender("console", Encoders.BasicEncoder);
    }

    /**
     * Adds a new {@link ConsoleAppender} with the specified encoder and sets the name to "console".
     * @apiNote The start method is automatically run at the end of this method.
     * @param encoder the encoder to set
     * @return this instance
     */
    public LoggerConfig addNewConsoleAppender(final Encoders encoder) {
        return addNewConsoleAppender(encoder.getEncoder());
    }

    /**
     * Adds a new {@link ConsoleAppender} with the specified encoder and sets the name to "console".
     * @apiNote The start method is automatically run at the end of this method.
     * @param encoder the encoder to set
     * @return this instance
     */
    public LoggerConfig addNewConsoleAppender(final PatternLayoutEncoder encoder) {
        return addNewConsoleAppender("console", encoder);
    }

    /**
     * Adds a new {@link ConsoleAppender} with the specified name and encoder.'
     * @apiNote if the name value is null or empty the default is "console" and
     * if encoder is null then the encoder from
     * {@link Encoders#BasicEncoder} is used instead.
     * Also the start method is automatically run at the end of this method.
     * @param name the name to set
     * @param encoder the encoder to set
     * @return this instance
     */
    public LoggerConfig addNewConsoleAppender(final String name, final Encoders encoder) {
        return addNewConsoleAppender(name, encoder.getEncoder());
    }

    /**
     * Adds a new {@link ConsoleAppender} with the specified name and encoder.'
     * @apiNote if the name value is null or empty the default is "console" and
     * if encoder is null then the encoder from
     * {@link Encoders#BasicEncoder} is used instead.
     * Also the start method is automatically run at the end of this method.
     * @param name the name to set
     * @param encoder the encoder to set
     * @return this instance
     */
    public LoggerConfig addNewConsoleAppender(final String name, final PatternLayoutEncoder encoder) {
        checkArgumentNotNullOrEmpty(name, Literals.cannotBeNullOrEmpty("name"));
        final ConsoleAppender<ILoggingEvent> logConsoleAppender = new ConsoleAppender<>();
        logConsoleAppender.setContext(LoggingManager.getContext());
        if (isBlank(name)) {
            logConsoleAppender.setName("console");
        } else {
            logConsoleAppender.setName(name);
        }
        if (encoder == null) {
            logConsoleAppender.setEncoder(Encoders.BasicEncoder.getEncoder());
        } else {
            logConsoleAppender.setEncoder(encoder);
        }
        logConsoleAppender.start();
        return addAppender(logConsoleAppender);
    }

    /**
     * Returns the name of the logger.
     * @return the name of the logger
     */
    public String getName() {
        return logger.getName();
    }

    /**
     * Removes the specified appender.
     * @param name the name of the appender to remove
     * @return true if no errors occurred
     */
    public boolean removeAppender(final String name) {
        return logger.detachAppender(name);
    }

    /**
     * Removes the specified appender.
     * @param appender the appender to remove
     * @return true if no errors occurred
     */
    public boolean removeAppender(final Appender<ILoggingEvent> appender) {
        return logger.detachAppender(appender);
    }

    /**
     * Checks if the logger has the specified appender.
     * @param name the name of the appender to lookup
     * @return true if exists
     */
    public boolean hasAppender(final String name) {
        return logger.getAppender(name) != null;
    }

    /**
     * Checks if the logger has the specified appender.
     * @param appender the appender to lookup
     * @return true if exists
     */
    public boolean hasAppender(final Appender<ILoggingEvent> appender) {
        return logger.isAttached(appender);
    }

    /**
     * Returns the specified appender.
     * @param name the name of the appender to lookup
     * @return the appender instance
     */
    public Appender<ILoggingEvent> getAppender(final String name) {
        return logger.getAppender(name);
    }

    /**
     * Returns the stored Logback logger instance.
     * @return the stored Logback logger instance
     */
    public Logger getLogger() {
        return logger;
    }
}
