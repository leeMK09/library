package com.work.library.domain.book;

import com.work.library.domain.book.event.BookCategoriesChangedEvent;
import com.work.library.domain.book.exception.BookException;
import com.work.library.entity.book.BookEntity;

import java.time.LocalDateTime;

public class Book {
    private Long id;

    private final String title;

    private final Author author;

    private BookCategories categories;

    private BookStatus status;

    public Book(
            Long id,
            String title,
            Author author,
            BookStatus status,
            BookCategories categories
    ) {
        validateRequired(title, author, categories);

        this.id = id;
        this.title = title;
        this.author = author;
        this.status = status;
        this.categories = categories;
    }

    public Book(String title, Author author, BookCategories categories) {
        this(
                null,
                title,
                author,
                BookStatus.AVAILABLE,
                categories
        );
    }

    public Book(Long id, String title, Author author,  BookCategories categories) {
        this(
                id,
                title,
                author,
                BookStatus.AVAILABLE,
                categories
        );
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

    public boolean isDamaged() {
        return this.status == BookStatus.DAMAGED;
    }

    public BookCategoriesChangedEvent changeCategories(BookCategories newBookCategories) {
        if (newBookCategories == null) {
            throw BookException.emptyCategories();
        }
        this.categories = newBookCategories;

        return new BookCategoriesChangedEvent(
                this,
                newBookCategories,
                LocalDateTime.now()
        );
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
