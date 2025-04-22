package com.work.library.application.exception;

public class BookApplicationException extends ApplicationException {
    private static final String NOT_FOUND_CATEGORY = "요청한 카테고리 중 존재하지 않는 항목이 있습니다.";

    private static final String NOT_FOUND_BOOK = "요청한 도서는 존재하지 않습니다.";

    private static final String EXIST_BOOK = "이미 등록된 도서입니다.";

    public BookApplicationException(ErrorType type, String message) {
        super(type, message);
    }

    public static BookApplicationException invalidParameterByCategory() {
        return new BookApplicationException(
                ErrorType.INVALID_PARAMETER,
                NOT_FOUND_CATEGORY
        );
    }

    public static BookApplicationException invalidParameterByBook() {
        return new BookApplicationException(
                ErrorType.INVALID_PARAMETER,
                NOT_FOUND_BOOK
        );
    }

    public static BookApplicationException duplicatedDateByBook() {
        return new BookApplicationException(
                ErrorType.RESOURCE_DUPLICATED,
                EXIST_BOOK
        );
    }
}
