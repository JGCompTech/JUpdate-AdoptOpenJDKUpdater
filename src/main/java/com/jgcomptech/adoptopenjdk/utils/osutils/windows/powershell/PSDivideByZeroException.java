package com.jgcomptech.adoptopenjdk.utils.osutils.windows.powershell;

public class PSDivideByZeroException extends PowerShellException {
    public PSDivideByZeroException() { }

    public PSDivideByZeroException(final String message) {
        super(message);
    }

    public PSDivideByZeroException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public PSDivideByZeroException(final Throwable cause) {
        super(cause);
    }

    protected PSDivideByZeroException(final String message,
                                      final Throwable cause,
                                      final boolean enableSuppression,
                                      final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
