package com.work.library.application.service;

import com.work.library.domain.book.Book;
import com.work.library.domain.book.BookCategories;
import com.work.library.domain.book.event.BookCategoriesChangedEvent;
import com.work.library.domain.book.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class BookCommandService {
    private final BookRepository bookRepository;

    private final ApplicationEventPublisher eventPublisher;

    public BookCommandService(
            BookRepository bookRepository,
            ApplicationEventPublisher eventPublisher
    ) {
        this.bookRepository = bookRepository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public Long save(Book book) {
        Book savedBook = bookRepository.save(book);
        return savedBook.getId();
    }

    @Transactional
    public void changeBookCategories(Book book, BookCategories newBookCategories) {
        BookCategoriesChangedEvent event = book.changeCategories(newBookCategories);
        eventPublisher.publishEvent(event);
    }
}
