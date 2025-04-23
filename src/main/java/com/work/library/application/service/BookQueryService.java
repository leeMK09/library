package com.work.library.application.service;

import com.work.library.application.exception.BookApplicationException;
import com.work.library.domain.book.Book;
import com.work.library.domain.book.repository.BookRepository;
import com.work.library.domain.category.Category;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class BookQueryService {
    private final BookRepository bookRepository;

    public BookQueryService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAllByCategories(List<Category> categories) {
        List<Book> result = bookRepository.findAllByCategories(categories);
        return result;
    }

    public List<Book> searchByTitleOrAuthor(String title, String author) {
        List<Book> result = bookRepository.searchByTitleOrAuthor(title, author);
        return result;
    }

    public Optional<Book> searchByTitleAndAuthor(String title, String author) {
        Optional<Book> book = bookRepository.searchByTitleAndAuthor(title, author);
        return book;
    }

    public Book getById(Long id) {
        Optional<Book> book = bookRepository.findById(id);

        if (book.isEmpty()) {
            log.error("[BookQueryService] 요청한 도서는 존재하지 않습니다. requested book id : {}", id);
            throw BookApplicationException.notFoundBooks();
        }

        return book.get();
    }
}
