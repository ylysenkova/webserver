package com.lysenkova.entity;

public enum HttpMethod {
    GET("GET"), POST("POST");

    private final String name;

    HttpMethod(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static HttpMethod getByName(String name) {
        HttpMethod[] values = values();
        for (HttpMethod httpMethod : values) {
            if (httpMethod.getName().equalsIgnoreCase(name)) {
                return httpMethod;
            }
        }
        throw new IllegalArgumentException("No HTTP method for name: " + name);
    }
}
