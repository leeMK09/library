package com.work.library.application.service;

import com.work.library.application.exception.BookApplicationException;
import com.work.library.domain.book.Book;
import com.work.library.domain.book.BookCategories;
import com.work.library.domain.book.RentalHistory;
import com.work.library.domain.book.event.BookCategoriesChangedEvent;
import com.work.library.domain.book.repository.BookRepository;
import com.work.library.domain.book.repository.RentalHistoryRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class BookCommandService {
    private final BookRepository bookRepository;

    private final RentalHistoryRepository rentalHistoryRepository;

    private final RentalPolicy rentalPolicy;

    private final ApplicationEventPublisher eventPublisher;

    public BookCommandService(
            BookRepository bookRepository,
            RentalHistoryRepository rentalHistoryRepository,
            RentalPolicy rentalPolicy,
            ApplicationEventPublisher eventPublisher
    ) {
        this.bookRepository = bookRepository;
        this.rentalHistoryRepository = rentalHistoryRepository;
        this.rentalPolicy = rentalPolicy;
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

    @Transactional
    public Book rental(Book book) {
        if (book.isDamaged()) {
            log.error("[BookCommandService] 책이 훼손되어 대여할 수 없습니다. found book id : {}", book.getId());
            throw BookApplicationException.damagedBooks();
        }
        book.rental();
        LocalDateTime rentedAt = rentalPolicy.getRentedAt();
        LocalDateTime expiredAt = rentalPolicy.getExpiredAt(rentedAt);

        RentalHistory rentalHistory = new RentalHistory(book, rentedAt, expiredAt);

        rentalHistoryRepository.save(rentalHistory);
        bookRepository.update(book);
        return book;
    }
}
