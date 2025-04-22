package com.work.library.api.base;

public enum ResponseType {
    SUCCESS("요청 성공");

    private final String description;

    ResponseType(String description) {
        this.description = description;
    }
}
