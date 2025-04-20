package com.work.library.domain.book;

import com.work.library.domain.book.exception.AuthorException;

public record Author(
    String value
) {
    public Author {
        if (value == null || value.isBlank()) {
            throw AuthorException.blank();
        }
    }
}
