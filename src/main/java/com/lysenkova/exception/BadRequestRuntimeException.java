package com.lysenkova.exception;

public class BadRequestRuntimeException extends RuntimeException {
    public BadRequestRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
