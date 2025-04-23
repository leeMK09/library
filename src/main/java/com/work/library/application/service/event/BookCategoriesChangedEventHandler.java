package com.work.library.application.service.event;

import com.work.library.domain.book.event.BookCategoriesChangedEvent;
import com.work.library.domain.book.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BookCategoriesChangedEventHandler {
    private final BookRepository bookRepository;

    public BookCategoriesChangedEventHandler(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @EventListener
    public void handleEvent(BookCategoriesChangedEvent event) {
        log.info("[BookCategoriesChangedEventHandler] Received event: {}, eventTime: {}", event, event.timestamp());
        bookRepository.deleteAllMappingsByBook(event.book());
        bookRepository.save(event.book());
    }
}
