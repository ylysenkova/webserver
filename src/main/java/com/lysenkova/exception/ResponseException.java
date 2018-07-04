package com.lysenkova.exception;

import com.lysenkova.entity.StatusCode;

public class ResponseException extends RuntimeException {
    private StatusCode statusCode;

    public ResponseException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResponseException(String message, Throwable cause, StatusCode statusCode) {
        super(message, cause);
    }

    public StatusCode getStatusCode() {
        return statusCode;
    }
}
