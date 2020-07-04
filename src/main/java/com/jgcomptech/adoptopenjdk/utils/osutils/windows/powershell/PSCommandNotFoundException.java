package com.jgcomptech.adoptopenjdk.utils.osutils.windows.powershell;

public class PSCommandNotFoundException extends PowerShellParserErrorException {
    public PSCommandNotFoundException() { }

    public PSCommandNotFoundException(final String message) {
        super(message);
    }

    public PSCommandNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public PSCommandNotFoundException(final Throwable cause) {
        super(cause);
    }

    protected PSCommandNotFoundException(final String message,
                                         final Throwable cause,
                                         final boolean enableSuppression,
                                         final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
