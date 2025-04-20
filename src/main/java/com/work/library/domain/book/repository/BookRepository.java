package com.work.library.domain.book.repository;

import com.work.library.domain.book.Book;

public interface BookRepository {
    Book save(Book book);
}
