package com.work.library.application;

import com.work.library.application.exception.BookApplicationException;
import com.work.library.application.service.BookCommandService;
import com.work.library.application.service.BookQueryService;
import com.work.library.domain.book.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BookUpdateApplication {
    private final BookQueryService bookQueryService;

    private final BookCommandService bookCommandService;

    public BookUpdateApplication(
            BookQueryService bookQueryService,
            BookCommandService bookCommandService
    ) {
        this.bookQueryService = bookQueryService;
        this.bookCommandService = bookCommandService;
    }

    public Long damage(Long bookId) {
        Book book = bookQueryService.getById(bookId);
        book.damaged();
        Long savedId = bookCommandService.save(book);
        return savedId;
    }

    public Long rental(Long bookId) {
        Book book = bookQueryService.getById(bookId);

        if (book.isRented()) {
            log.error("[BookUpdateApplication] 해당 도서는 이미 대여중 입니다. found book Id : {}", book.getId());
            throw BookApplicationException.rentedBooks();
        }

        Book rentedBook = bookCommandService.rental(book);

        return rentedBook.getId();
    }
}
