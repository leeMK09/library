package com.work.library.application.exception;

public enum ErrorType {
    INVALID_PARAMETER("잘못된 요청값 입니다."),
    RESOURCE_NOT_FOUND("리소스를 찾을 수 없습니다.");

    private final String description;

    ErrorType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
