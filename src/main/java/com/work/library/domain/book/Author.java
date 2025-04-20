package com.work.library.domain.book;

import com.work.library.domain.book.exception.InvalidAuthorException;

public record Author(
    String value
) {
    public Author {
        if (value == null || value.isBlank()) {
            throw InvalidAuthorException.blank();
        }
    }
}
