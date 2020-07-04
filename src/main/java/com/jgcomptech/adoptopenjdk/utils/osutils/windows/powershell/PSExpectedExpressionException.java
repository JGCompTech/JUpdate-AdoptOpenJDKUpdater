package com.jgcomptech.adoptopenjdk.utils.osutils.windows.powershell;

public class PSExpectedExpressionException extends PowerShellParserErrorException {
    public PSExpectedExpressionException() { }

    public PSExpectedExpressionException(final String message) {
        super(message);
    }

    public PSExpectedExpressionException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public PSExpectedExpressionException(final Throwable cause) {
        super(cause);
    }

    protected PSExpectedExpressionException(final String message,
                                            final Throwable cause,
                                            final boolean enableSuppression,
                                            final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
