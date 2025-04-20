package com.work.library.domain.book;

import com.work.library.domain.book.exception.BookException;
import com.work.library.entity.book.BookEntity;

public class Book {
    private Long id;

    private final String title;

    private final Author author;

    private final BookCategories categories;

    private BookStatus status;

    public Book(String title, Author author, BookCategories categories) {
        validateRequired(title, author, categories);

        this.title = title;
        this.author = author;
        this.categories = categories;
        this.status = BookStatus.AVAILABLE;
    }

    public Book(Long id, String title, Author author, BookCategories categories) {
        validateRequired(title, author, categories);

        this.id = id;
        this.title = title;
        this.author = author;
        this.categories = categories;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author.value();
    }

    public BookCategories getCategories() {
        return categories;
    }

    public BookStatus getStatus() {
        return status;
    }

    public Long getId() {
        return id;
    }

    public void damaged() {
        this.status = BookStatus.DAMAGED;
    }

    public BookEntity toEntity() {
        BookEntity bookEntity = new BookEntity(title, author.value());
        return bookEntity;
    }

    public BookEntity toRegisteredEntity() {
        BookEntity bookEntity = new BookEntity(id, title, author.value(), status);
        return bookEntity;
    }

    private void validateRequired(String title, Author author, BookCategories categories) {
        if (title == null || title.isBlank()) {
            throw BookException.blankTitle();
        }

        if (author == null) {
            throw BookException.emptyAuthor();
        }

        if (categories == null) {
            throw BookException.emptyCategories();
        }
    }
}
