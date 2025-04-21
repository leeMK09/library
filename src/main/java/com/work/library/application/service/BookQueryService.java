package com.work.library.application.service;

import com.work.library.domain.book.Author;
import com.work.library.domain.book.Book;
import com.work.library.domain.book.repository.BookRepository;
import com.work.library.domain.category.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookQueryService {
    private final BookRepository bookRepository;

    public BookQueryService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAllByCategories(List<Category> categories) {
        List<Book> result = bookRepository.findAllByCategoryList(categories);
        return result;
    }

    public List<Book> searchByTitleOrAuthor(String title, Author author) {
        List<Book> result = bookRepository.searchByTitleOrAuthor(title, author);
        return result;
    }
}
