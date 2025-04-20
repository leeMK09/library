package com.work.library.domain.book.repository;

import com.work.library.domain.book.Book;
import com.work.library.domain.book.BookCategories;

public interface BookCategoriesRepository {
    BookCategories save(Book book, BookCategories bookCategories);
}
