package com.jgcomptech.adoptopenjdk.logging;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.ConsoleAppender;

public enum Loggers {
    Root(new LoggerConfig("root")),
    RootPackage(new LoggerConfig("root")),
    ;

    private LoggerConfig config;

    Loggers(final LoggerConfig config) {
        this.config = config;
    }

    public LoggerConfig getConfig() {
        return config;
    }

    /**
     * Sets the config of the logger.
     * @param config the config of the logger
     */
    public void setConfig(final LoggerConfig config) {
        this.config = config;
    }

    /**
     * Returns the name of the logger.
     * @return the name of the logger
     */
    public String getName() {
        return config.getName();
    }

    /**
     * Sets the name of the logger.
     * @param name the name of the logger
     */
    public Loggers setName(final String name) {
        config = new LoggerConfig(name);
        return this;
    }

    /**
     * Enables the logger to use the specified log level.
     * @apiNote Additive is automatically disabled in this method
     * @param logLevel the log level to set
     * @return this instance
     */
    public Loggers enable(final Level logLevel) {
        config.enable(logLevel);
        return this;
    }

    /**
     * Enables the logger to use the specified log level and adds
     * the {@link Appenders#LimitedConsoleAppender}.
     * @apiNote Additive is automatically disabled in this method
     * @param logLevel the log level to set
     * @return this instance
     */
    public Loggers enableLimitedConsole(final Level logLevel) {
        config.enableLimitedConsole(logLevel);
        return this;
    }

    /**
     * Enables the logger to use the specified log level and adds
     * the {@link Appenders#BasicConsoleAppender}.
     * @apiNote Additive is automatically disabled in this method
     * @param logLevel the log level to set
     * @return this instance
     */
    public Loggers enableBasicConsole(final Level logLevel) {
        config.enableBasicConsole(logLevel);
        return this;
    }

    /**
     * Enables the logger to use the specified log level and adds
     * the {@link Appenders#ExtendedConsoleAppender}.
     * @apiNote Additive is automatically disabled in this method
     * @param logLevel the log level to set
     * @return this instance
     */
    public Loggers enableExtendedConsole(final Level logLevel) {
        config.enableExtendedConsole(logLevel);
        return this;
    }

    /**
     * Disables the logger by setting the log level to {@link Level#OFF}.
     * @apiNote Additive is automatically disabled in this method
     * @return this instance
     */
    public Loggers disable() {
        config.disable();
        return this;
    }

    /**
     * Adds the specified {@link Appender} to the logger.
     * @apiNote Additive is automatically disabled in this method
     * @param appender the appender to add
     * @return this instance
     */
    public Loggers addAppender(final Appenders appender) {
        config.addAppender(appender);
        return this;
    }

    /**
     * Adds the specified {@link Appender} to the logger.
     * @apiNote Additive is automatically disabled in this method
     * @param newAppender the appender to add
     * @return this instance
     */
    public Loggers addAppender(final Appender<ILoggingEvent> newAppender) {
        config.addAppender(newAppender);
        return this;
    }

    /**
     * Adds a new {@link ConsoleAppender} with the encoder from {@link Encoders#BasicEncoder}
     * and sets the name to "console".
     * @apiNote The start method is automatically called at the end of this method.
     * @return this instance
     */
    public Loggers addNewConsoleAppender() {
        config.addNewConsoleAppender();
        return this;
    }

    /**
     * Adds a new {@link ConsoleAppender} with the specified encoder and sets the name to "console".
     * @apiNote The start method is automatically run at the end of this method.
     * @param encoder the encoder to set
     * @return this instance
     */
    public Loggers addNewConsoleAppender(final Encoders encoder) {
        config.addNewConsoleAppender(encoder);
        return this;
    }

    /**
     * Adds a new {@link ConsoleAppender} with the specified encoder and sets the name to "console".
     * @apiNote The start method is automatically run at the end of this method.
     * @param encoder the encoder to set
     * @return this instance
     */
    public Loggers addNewConsoleAppender(final PatternLayoutEncoder encoder) {
        config.addNewConsoleAppender(encoder);
        return this;
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
    public Loggers addNewConsoleAppender(final String name, final Encoders encoder) {
        config.addNewConsoleAppender(name, encoder);
        return this;
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
    public Loggers addNewConsoleAppender(final String name, final PatternLayoutEncoder encoder) {
        config.addNewConsoleAppender(name, encoder);
        return this;
    }

    /**
     * Removes the specified appender.
     * @param name the name of the appender to remove
     * @return true if no errors occurred
     */
    public boolean removeAppender(final String name) {
        return config.removeAppender(name);
    }

    /**
     * Removes the specified appender.
     * @param appender the appender to remove
     * @return true if no errors occurred
     */
    public boolean removeAppender(final Appender<ILoggingEvent> appender) {
        return config.removeAppender(appender);
    }

    /**
     * Checks if the logger has the specified appender.
     * @param name the name of the appender to lookup
     * @return true if exists
     */
    public boolean hasAppender(final String name) {
        return config.hasAppender(name);
    }

    /**
     * Checks if the logger has the specified appender.
     * @param appender the appender to lookup
     * @return true if exists
     */
    public boolean hasAppender(final Appender<ILoggingEvent> appender) {
        return config.hasAppender(appender);
    }

    /**
     * Returns the specified appender.
     * @param name the name of the appender to lookup
     * @return the appender instance
     */
    public Appender<ILoggingEvent> getAppender(final String name) {
        return config.getAppender(name);
    }

    /**
     * Returns the stored Logback logger instance.
     * @return the stored Logback logger instance
     */
    public Logger getLogger() {
        return config.getLogger();
    }
}
