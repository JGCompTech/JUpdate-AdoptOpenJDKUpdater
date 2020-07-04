package com.jgcomptech.adoptopenjdk.utils.logging;

import ch.qos.logback.classic.encoder.PatternLayoutEncoder;

public enum Encoders {
    /**
     * A {@link ch.qos.logback.classic.encoder.PatternLayoutEncoder} with the pattern "%msg%n".
     * @apiNote This encoder is automatically started after initialization.
     */
    LimitedEncoder(LoggingManager.createNewLogEncoder("%msg%n")),
    /**
     * A {@link PatternLayoutEncoder} with the pattern
     * "%-12d{YYYY-MM-dd HH:mm:ss} %level %logger{0} - %msg%n".
     * @apiNote This encoder is automatically started after initialization.
     */
    BasicEncoder(LoggingManager.createNewLogEncoder("%-12d{YYYY-MM-dd HH:mm:ss} %level %logger{0} - %msg%n")),
    /**
     * A {@link PatternLayoutEncoder} with the pattern
     * "%-12d{YYYY-MM-dd HH:mm:ss.SSS} [%thread] %level %logger{100} - %msg%n".
     * @apiNote This encoder is automatically started after initialization.
     */
    ExtendedEncoder(LoggingManager.createNewLogEncoder(
            "%-12d{YYYY-MM-dd HH:mm:ss.SSS} [%thread] %level %logger{100} - %msg%n"))
    ;

    private final PatternLayoutEncoder encoder;

    Encoders(final PatternLayoutEncoder encoder) {
        this.encoder = encoder;
    }

    public PatternLayoutEncoder getEncoder() {
        return encoder;
    }
}
