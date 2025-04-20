package com.work.library.domain.book;

import com.work.library.domain.book.exception.BookException;

public class Book {
    private final String title;

    private final Author author;

    public Book(String title, Author author) {
        if (title == null || title.isBlank()) {
            throw BookException.blankTitle();
        }

        if (author == null) {
            throw BookException.emptyAuthor();
        }

        this.title = title;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author.value();
    }
}
