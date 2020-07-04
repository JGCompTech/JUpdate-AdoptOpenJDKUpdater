package com.jgcomptech.adoptopenjdk.utils.osutils.windows.powershell;

public class PSExpectedValueExpressionException extends PowerShellParserErrorException {
    public PSExpectedValueExpressionException() { }

    public PSExpectedValueExpressionException(final String message) {
        super(message);
    }

    public PSExpectedValueExpressionException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public PSExpectedValueExpressionException(final Throwable cause) {
        super(cause);
    }

    protected PSExpectedValueExpressionException(final String message,
                                                 final Throwable cause,
                                                 final boolean enableSuppression,
                                                 final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
