package com.jgcomptech.adoptopenjdk.osutils.windows.powershell;

public class PSUnexpectedTokenException extends PowerShellParserErrorException {
    public PSUnexpectedTokenException() { }

    public PSUnexpectedTokenException(final String message) {
        super(message);
    }

    public PSUnexpectedTokenException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public PSUnexpectedTokenException(final Throwable cause) {
        super(cause);
    }

    protected PSUnexpectedTokenException(final String message,
                                         final Throwable cause,
                                         final boolean enableSuppression,
                                         final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
