package com.lysenkova.entity;

public enum StatusCode {
    OK(200, "OK"),
    NOT_FOUND(404, "NOT_FOUND"),
    BAD_REQUEST(400, "BAD_REQUEST"),
    INTERNAL_SERVER_ERROR(500, "INTERNAL_SERVER_ERROR"),
    METHOD_NOT_SUPPORTED(415, "METHOD_NOT_SUPPORTED");

    private final int code;
    private final String message;

    StatusCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
