package com.work.library.domain.book.repository;

import com.work.library.domain.book.Book;
import com.work.library.domain.category.Category;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    Optional<Book> findById(Long id);

    Book save(Book book);

    Book update(Book book);

    Optional<Book> searchByTitleAndAuthor(String title, String author);

    List<Book> searchAllByTitleOrAuthor(String title, String author);

    List<Book> findAllByCategories(List<Category> categories);

    void deleteAllMappingsByBook(Book book);
}
