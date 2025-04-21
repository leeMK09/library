package com.work.library.application.exception;

public class ApplicationException extends RuntimeException {
    private ErrorType type;

    public ApplicationException(ErrorType type, String message) {
        super(message);
        this.type = type;
    }

    public ErrorType getType() {
        return type;
    }
}
