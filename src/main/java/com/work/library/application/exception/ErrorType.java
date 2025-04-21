package com.work.library.application.exception;

public enum ErrorType {
    INVALID_PARAMETER("잘못된 요청값");

    private final String description;

    ErrorType(String description) {
        this.description = description;
    }
}
