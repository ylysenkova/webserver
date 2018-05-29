package com.lysenkova.exception;

public class FileNotFoundRuntimeException extends RuntimeException {
    public FileNotFoundRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
