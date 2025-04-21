package com.work.library.application.exception;

public class BookApplicationException extends ApplicationException {
    private static final String NOT_FOUND_CATEGORY = "요청한 카테고리 중 존재하지 않는 항목이 있습니다.";

    public BookApplicationException(ErrorType type, String message) {
        super(type, message);
    }

    public static BookApplicationException invalidParameter() {
        return new BookApplicationException(
                ErrorType.INVALID_PARAMETER,
                NOT_FOUND_CATEGORY
        );
    }
}
