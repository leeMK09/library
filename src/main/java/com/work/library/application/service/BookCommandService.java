package com.work.library.application.service;

import com.work.library.domain.book.Book;
import com.work.library.domain.book.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class BookCommandService {
    private final BookRepository bookRepository;

    public BookCommandService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Transactional
    public Long save(Book book) {
        Book savedBook = bookRepository.save(book);
        return savedBook.getId();
    }
}
