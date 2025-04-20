package com.work.library.domain.book.exception;

import com.work.library.domain.book.ErrorMessage;

public class InvalidAuthorException extends RuntimeException {
    public InvalidAuthorException(String message) {
        super(message);
    }

    public static InvalidAuthorException blank() {
        return new InvalidAuthorException(ErrorMessage.AUTHOR_BLANK);
    }
}
