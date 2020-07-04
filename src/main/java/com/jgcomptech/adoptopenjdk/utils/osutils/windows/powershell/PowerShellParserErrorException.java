package com.jgcomptech.adoptopenjdk.utils.osutils.windows.powershell;

public class PowerShellParserErrorException extends PowerShellException {
    public PowerShellParserErrorException() { }

    public PowerShellParserErrorException(final String message) {
        super(message);
    }

    public PowerShellParserErrorException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public PowerShellParserErrorException(final Throwable cause) {
        super(cause);
    }

    protected PowerShellParserErrorException(final String message,
                                             final Throwable cause,
                                             final boolean enableSuppression,
                                             final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
