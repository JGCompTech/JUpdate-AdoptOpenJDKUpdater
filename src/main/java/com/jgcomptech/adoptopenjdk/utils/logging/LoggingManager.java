package com.jgcomptech.adoptopenjdk.utils.logging;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.FileAppender;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.TimeBasedFileNamingAndTriggeringPolicy;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;
import ch.qos.logback.core.util.FileSize;
import com.jgcomptech.adoptopenjdk.recentJava.ObjectsExt;
import com.jgcomptech.adoptopenjdk.utils.Buildable;
import com.jgcomptech.adoptopenjdk.utils.Literals;
import com.jgcomptech.adoptopenjdk.utils.Utils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.LoggerFactory;

import static com.jgcomptech.adoptopenjdk.utils.StringUtils.isBlank;
import static com.jgcomptech.adoptopenjdk.utils.Utils.checkArgumentNotNull;

/**
 * Contains methods to allow setting the base parameters for the logback implementation
 * that this library uses for all file and console logging with SLF4J.
 * @apiNote If this class is used a logback configuration file is not
 * required to be added to your project and if included that configuration
 * will be overwritten by the methods in this class.
 */
public final class LoggingManager {
    private static final LoggerContext logCtx = (LoggerContext) LoggerFactory.getILoggerFactory();

    /** A basic log file filename with name "logfile.log"*/
    public static final String LOG_FILE_NAME = "logfile.log";
    /** A basic log file filename with name "logfile-%d{yyyy-MM-dd_HH}.log"*/
    public static final String LOG_FILE_NAME_DATED = "logfile-%d{yyyy-MM-dd_HH}.log";

    /**
     * Returns the Logback logger context.
     * @return  the Logback logger context
     */
    public static LoggerContext getContext() {
        return logCtx;
    }

    /**
     * Enables the logger for the specified class to use the specified log level.
     * @param className the logger name
     * @param logLevel the log level to set
     */
    public static void enableSpecificClassLogging(final String className, final Level logLevel) {
        final Logger logger = logCtx.getLogger(className);
        logger.setAdditive(false);
        logger.setLevel(logLevel);
    }

    /**
     * Disables the logger for the specified class by setting the log level to {@link Level#OFF}.
     * @param className the logger name
     */
    public static void disableSpecificClassLogging(final String className) {
        final Logger logger = logCtx.getLogger(className);
        logger.setAdditive(false);
        logger.setLevel(Level.OFF);
    }

    /**
     * Adds the specified {@link Appender} to the logger for the specified class.
     * @param className the logger name
     * @param newAppender the appender to add
     */
    public static void addSpecificClassLoggingAppender(final String className,
                                                       final Appender<ILoggingEvent> newAppender) {
        final Logger logger = logCtx.getLogger(className);
        logger.setAdditive(false);
        logger.addAppender(newAppender);
    }

    /**
     * Creates a new {@link PatternLayoutEncoder} with the specified pattern.
     * @apiNote The start method is automatically called at the end of this method.
     * @param pattern the String pattern
     * @return a new PatternLayoutEncoder instance
     */
    public static PatternLayoutEncoder createNewLogEncoder(final String pattern) {
        final PatternLayoutEncoder logEncoder = new PatternLayoutEncoder();
        logEncoder.setContext(logCtx);
        logEncoder.setPattern(pattern);
        logEncoder.start();
        return logEncoder;
    }

    /**
     * Creates a new {@link ConsoleAppender} with the
     * {@link Encoders#BasicEncoder} and sets the name to "console".
     * @apiNote The start method is automatically called at the end of this method.
     * @return a new ConsoleAppender instance
     */
    public static ConsoleAppender<ILoggingEvent> createNewConsoleAppender() {
        return createNewConsoleAppender("console", Encoders.BasicEncoder);
    }

    /**
     * Creates a new {@link ConsoleAppender} with the specified encoder and sets the name to "console".
     * @apiNote The start method is automatically run at the end of this method.
     * @param encoder the encoder to set
     * @return a new ConsoleAppender instance
     */
    public static ConsoleAppender<ILoggingEvent> createNewConsoleAppender(final Encoders encoder) {
        return createNewConsoleAppender(encoder.getEncoder());
    }

    /**
     * Creates a new {@link ConsoleAppender} with the specified encoder and sets the name to "console".
     * @apiNote The start method is automatically run at the end of this method.
     * @param encoder the encoder to set
     * @return a new ConsoleAppender instance
     */
    public static ConsoleAppender<ILoggingEvent> createNewConsoleAppender(final PatternLayoutEncoder encoder) {
        return createNewConsoleAppender("console", encoder);
    }

    /**
     * Creates a new {@link ConsoleAppender} with the specified name and encoder.'
     * @apiNote if the name value is null or empty the default is "console" and
     * if encoder is null then the {@link Encoders#BasicEncoder} is used instead.
     * Also the start method is automatically run at the end of this method.
     * @param name the name to set
     * @param encoder the encoder to set
     * @return a new ConsoleAppender instance
     */
    public static ConsoleAppender<ILoggingEvent> createNewConsoleAppender(final String name,
                                                                          final Encoders encoder) {
        return createNewConsoleAppender(name, encoder.getEncoder());
    }

    /**
     * Creates a new {@link ConsoleAppender} with the specified name and encoder.'
     * @apiNote if the name value is null or empty the default is "console" and
     * if encoder is null then the {@link Encoders#BasicEncoder} is used instead.
     * Also the start method is automatically run at the end of this method.
     * @param name the name to set
     * @param encoder the encoder to set
     * @return a new ConsoleAppender instance
     */
    public static ConsoleAppender<ILoggingEvent> createNewConsoleAppender(final String name,
                                                                          final PatternLayoutEncoder encoder) {
        Utils.checkArgumentNotNullOrEmpty(name, Literals.cannotBeNullOrEmpty("name"));
        final ConsoleAppender<ILoggingEvent> logConsoleAppender = new ConsoleAppender<>();
        logConsoleAppender.setContext(logCtx);
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
        return logConsoleAppender;
    }

    /**
     * Gets a new RollingFileAppenderBuilder instance.
     * @return a new RollingFileAppenderBuilder instance
     */
    public static RollingFileAppenderBuilder getRollingFileAppenderBuilder() {
        return new RollingFileAppenderBuilder();
    }

    /**
     * This class contains methods to build a RollingFileAppender.
     */
    public static final class RollingFileAppenderBuilder implements Buildable<RollingFileAppender<ILoggingEvent>> {
        private String name;
        private TimeBasedRollingPolicy<ILoggingEvent> logFilePolicy;
        private PatternLayoutEncoder encoder;
        private String fileName;

        private RollingFileAppenderBuilder() { }

        /**
         * Sets the name of the appender.
         * @param name the name to set
         * @return this instance
         */
        public RollingFileAppenderBuilder setName(final String name) {
            this.name = name;
            return this;
        }

        /**
         * Sets the TimeBasedRollingPolicy.
         * @apiNote This must be set or the build method will throw an IllegalArgumentException.
         * @param logFilePolicy the policy to set
         * @return this instance
         */
        public RollingFileAppenderBuilder setLogFilePolicy(final TimeBasedRollingPolicy<ILoggingEvent> logFilePolicy) {
            this.logFilePolicy = logFilePolicy;
            return this;
        }

        /**
         * Sets the PatternLayoutEncoder.
         * @param encoder the encoder to set
         * @return this instance
         */
        public RollingFileAppenderBuilder setEncoder(final PatternLayoutEncoder encoder) {
            this.encoder = encoder;
            return this;
        }

        /**
         * Sets the filename.
         * @param fileName the filename to set
         * @return this instance
         */
        public RollingFileAppenderBuilder setFileName(final String fileName) {
            this.fileName = fileName;
            return this;
        }

        /**
         * Builds a new RollingFileAppender instance.
         * @apiNote If the name is not set the default is "logFile" and
         * if the encoder is not set the {@link Encoders#BasicEncoder} is used instead.
         * The start method on both the TimeBasedRollingPolicy
         * and the new RollingFileAppender are called automatically.
         * @return a new RollingFileAppender instance
         */
        @Override
        public RollingFileAppender<ILoggingEvent> build() {
            checkArgumentNotNull(logFilePolicy, "LogFilePolicy Must Be Set!");
            final RollingFileAppender<ILoggingEvent> logFileAppender = new RollingFileAppender<>();
            logFileAppender.setContext(logCtx);
            logFileAppender.setAppend(true);

            if (isBlank(name)) {
                logFileAppender.setName("logFile");
            } else {
                logFileAppender.setName(name);
            }
            logFileAppender.setEncoder(ObjectsExt.requireNonNullElse(encoder, Encoders.BasicEncoder.getEncoder()));
            if(fileName != null && !isBlank(fileName)) logFileAppender.setFile(fileName);

            logFilePolicy.setParent(logFileAppender);
            logFileAppender.setRollingPolicy(logFilePolicy);
            logFileAppender.start();
            return logFileAppender;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;

            if (!(o instanceof RollingFileAppenderBuilder)) return false;

            final RollingFileAppenderBuilder builder = (RollingFileAppenderBuilder) o;

            return new EqualsBuilder()
                    .append(name, builder.name)
                    .append(logFilePolicy, builder.logFilePolicy)
                    .append(encoder, builder.encoder)
                    .append(fileName, builder.fileName)
                    .isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder(17, 37)
                    .append(name)
                    .append(logFilePolicy)
                    .append(encoder)
                    .append(fileName)
                    .toHashCode();
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .append("name", name)
                    .append("logFilePolicy", logFilePolicy)
                    .append("encoder", encoder)
                    .append("fileName", fileName)
                    .toString();
        }
    }

    /**
     * Gets a new TimeBasedRollingPolicyBuilder instance.
     * @return a new TimeBasedRollingPolicyBuilder instance
     */
    public static TimeBasedRollingPolicyBuilder getTimeBasedRollingPolicyBuilder() {
        return new TimeBasedRollingPolicyBuilder();
    }

    /**
     * This class contains methods to build a TimeBasedRollingPolicy.
     */
    public static final class TimeBasedRollingPolicyBuilder implements Buildable<TimeBasedRollingPolicy<ILoggingEvent>> {
        private String fileNamePattern;
        private FileSize totalSizeCap;
        private int maxFileHistory;
        private boolean cleanHistoryOnStart;
        private TimeBasedFileNamingAndTriggeringPolicy<ILoggingEvent> timeBasedFileNamingAndTriggeringPolicy;
        private FileAppender<ILoggingEvent> parent;

        private TimeBasedRollingPolicyBuilder() {
            fileNamePattern = "logfile-%d{yyyy-MM-dd_HH}.log";
        }

        /**
         * Sets the fileName pattern.
         * @param fileNamePattern the pattern to set
         * @return this instance
         */
        public TimeBasedRollingPolicyBuilder setFileNamePattern(final String fileNamePattern) {
            this.fileNamePattern = fileNamePattern;
            return this;
        }

        /**
         * Sets the total size cap parsed from a string.
         * @param totalSizeCap the total size cap to set
         * @return this instance
         */
        public TimeBasedRollingPolicyBuilder setTotalSizeCap(final String totalSizeCap) {
            this.totalSizeCap = FileSize.valueOf(totalSizeCap);
            return this;
        }

        /**
         * Sets the total size cap parsed from a {@link FileSize} object.
         * @param totalSizeCap the total size cap to set
         * @return this instance
         */
        public TimeBasedRollingPolicyBuilder setTotalSizeCap(final FileSize totalSizeCap) {
            this.totalSizeCap = totalSizeCap;
            return this;
        }

        /**
         * Sets the the maximum number of archive files to keep.
         * @apiNote if this value is less than 0 then this setting is ignored.
         * @param maxFileHistory the value to set
         * @return this instance
         */
        public TimeBasedRollingPolicyBuilder setMaxFileHistory(final int maxFileHistory) {
            this.maxFileHistory = maxFileHistory;
            return this;
        }

        /**
         * Sets if archive removal should be attempted on application start up.
         * @param cleanHistoryOnStart the value to set
         * @return this instance
         */
        public TimeBasedRollingPolicyBuilder setCleanHistoryOnStart(final boolean cleanHistoryOnStart) {
            this.cleanHistoryOnStart = cleanHistoryOnStart;
            return this;
        }

        /**
         * Sets the TimeBasedFileNamingAndTriggeringPolicy.
         * @param timeBasedTriggering the policy to set
         * @return this instance
         */
        public TimeBasedRollingPolicyBuilder setTimeBasedFileNamingAndTriggeringPolicy(
                final TimeBasedFileNamingAndTriggeringPolicy<ILoggingEvent> timeBasedTriggering) {
            this.timeBasedFileNamingAndTriggeringPolicy = timeBasedTriggering;
            return this;
        }

        /**
         * Sets the parent FileAppender.
         * @apiNote This method is not necessary if passing this object to the
         * {@link RollingFileAppenderBuilder#setLogFilePolicy(TimeBasedRollingPolicy)}
         * method as this value is set automatically during the build method.
         * @param appender the parent appender to set
         * @return this instance
         */
        public TimeBasedRollingPolicyBuilder setParent(final FileAppender<ILoggingEvent> appender) {
            this.parent = appender;
            return this;
        }

        /**
         * Builds a new TimeBasedRollingPolicy instance.
         * @apiNote The start method is not called automatically to allow adding the
         * policy to a RollingFileAppender first.
         * @return a new TimeBasedRollingPolicy instance
         */
        @Override
        public TimeBasedRollingPolicy<ILoggingEvent> build() {
            final TimeBasedRollingPolicy<ILoggingEvent> logFilePolicy = new TimeBasedRollingPolicy<>();
            logFilePolicy.setContext(logCtx);
            logFilePolicy.setFileNamePattern(fileNamePattern);
            if(totalSizeCap != null) logFilePolicy.setTotalSizeCap(totalSizeCap);
            if(maxFileHistory != -1) logFilePolicy.setMaxHistory(maxFileHistory);
            logFilePolicy.setCleanHistoryOnStart(cleanHistoryOnStart);
            if(timeBasedFileNamingAndTriggeringPolicy != null) {
                logFilePolicy.setTimeBasedFileNamingAndTriggeringPolicy(timeBasedFileNamingAndTriggeringPolicy);
            }
            if(parent != null) logFilePolicy.setParent(parent);
            return logFilePolicy;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;

            if (!(o instanceof TimeBasedRollingPolicyBuilder)) return false;

            final TimeBasedRollingPolicyBuilder builder = (TimeBasedRollingPolicyBuilder) o;

            return new EqualsBuilder()
                    .append(maxFileHistory, builder.maxFileHistory)
                    .append(cleanHistoryOnStart, builder.cleanHistoryOnStart)
                    .append(fileNamePattern, builder.fileNamePattern)
                    .append(totalSizeCap, builder.totalSizeCap)
                    .append(timeBasedFileNamingAndTriggeringPolicy, builder.timeBasedFileNamingAndTriggeringPolicy)
                    .append(parent, builder.parent)
                    .isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder(17, 37)
                    .append(fileNamePattern)
                    .append(totalSizeCap)
                    .append(maxFileHistory)
                    .append(cleanHistoryOnStart)
                    .append(timeBasedFileNamingAndTriggeringPolicy)
                    .append(parent)
                    .toHashCode();
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .append("fileNamePattern", fileNamePattern)
                    .append("totalSizeCap", totalSizeCap)
                    .append("maxFileHistory", maxFileHistory)
                    .append("cleanHistoryOnStart", cleanHistoryOnStart)
                    .append("timeBasedFileNamingAndTriggeringPolicy", timeBasedFileNamingAndTriggeringPolicy)
                    .append("parent", parent)
                    .toString();
        }
    }

    private LoggingManager() { }
}
