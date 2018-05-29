package com.lysenkova.entity;

public enum StatusCode {
    OK("200", "OK"), NOT_FOUND("404", "NOT_FOUND"), BAD_REQUEST("400", "BAD_REQUEST");

    private final String code;
    private final String name;

    StatusCode(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

}
