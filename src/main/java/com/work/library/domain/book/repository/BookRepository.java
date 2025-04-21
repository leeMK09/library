package com.work.library.domain.book.repository;

import com.work.library.domain.book.Author;
import com.work.library.domain.book.Book;
import com.work.library.domain.book.BookCategories;
import com.work.library.domain.category.Category;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    Optional<Book> findById(Long id);

    Book save(Book book);

    List<Book> findAllByCategoryList(List<Category> categories);

    List<Book> searchByTitleOrAuthor(String title, Author author);

    Book remapCategoriesToBook(Book book, BookCategories newBookCategories);
}
