package com.jgcomptech.adoptopenjdk.utils.osutils.windows.powershell;

public class PSMissingPropertyNameException extends PowerShellParserErrorException {
    public PSMissingPropertyNameException() { }

    public PSMissingPropertyNameException(final String message) {
        super(message);
    }

    public PSMissingPropertyNameException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public PSMissingPropertyNameException(final Throwable cause) {
        super(cause);
    }

    protected PSMissingPropertyNameException(final String message,
                                             final Throwable cause,
                                             final boolean enableSuppression,
                                             final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
