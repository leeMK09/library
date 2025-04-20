package com.work.library.domain.book.exception;

import com.work.library.domain.ErrorMessage;

public class BookException extends RuntimeException {
    public BookException(String message) {
        super(message);
    }

    public static BookException blankTitle() {
        return new BookException(ErrorMessage.TITLE_BLANK);
    }

    public static BookException emptyAuthor() {
        return new BookException(ErrorMessage.AUTHOR_EMPTY);
    }
}
