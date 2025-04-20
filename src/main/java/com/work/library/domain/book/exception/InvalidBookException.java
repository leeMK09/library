package com.work.library.domain.book.exception;

import com.work.library.domain.ErrorMessage;

public class InvalidBookException extends RuntimeException {
    public InvalidBookException(String message) {
        super(message);
    }

    public static InvalidBookException blankTitle() {
        return new InvalidBookException(ErrorMessage.TITLE_BLANK);
    }

    public static InvalidBookException emptyAuthor() {
        return new InvalidBookException(ErrorMessage.AUTHOR_EMPTY);
    }
}
