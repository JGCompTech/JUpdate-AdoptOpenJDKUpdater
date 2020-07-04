package com.jgcomptech.adoptopenjdk.utils.osutils.windows.powershell;

public class PowerShellException extends RuntimeException {
    public PowerShellException() { }

    public PowerShellException(final String message) {
        super(message);
    }

    public PowerShellException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public PowerShellException(final Throwable cause) {
        super(cause);
    }

    protected PowerShellException(final String message,
                                  final Throwable cause,
                                  final boolean enableSuppression,
                                  final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
