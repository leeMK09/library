package com.work.library.domain.book.exception;

import com.work.library.domain.ErrorMessage;

public class AuthorException extends RuntimeException {
    public AuthorException(String message) {
        super(message);
    }

    public static AuthorException blank() {
        return new AuthorException(ErrorMessage.AUTHOR_BLANK);
    }
}
