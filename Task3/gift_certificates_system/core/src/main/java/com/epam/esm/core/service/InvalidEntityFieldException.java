package com.epam.esm.core.service;

public class InvalidEntityFieldException extends Exception{
    public InvalidEntityFieldException() {
        super();
    }

    public InvalidEntityFieldException(String message) {
        super(message);
    }

    public InvalidEntityFieldException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidEntityFieldException(Throwable cause) {
        super(cause);
    }

    protected InvalidEntityFieldException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
