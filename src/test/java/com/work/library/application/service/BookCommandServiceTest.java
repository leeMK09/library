package com.work.library.application.service;

import com.work.library.application.exception.BookApplicationException;
import com.work.library.application.exception.ErrorType;
import com.work.library.domain.book.Author;
import com.work.library.domain.book.Book;
import com.work.library.domain.book.BookCategories;
import com.work.library.domain.book.RentalHistory;
import com.work.library.domain.book.event.BookCategoriesChangedEvent;
import com.work.library.domain.book.repository.BookRepository;
import com.work.library.domain.book.repository.RentalHistoryRepository;
import com.work.library.domain.category.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookCommandServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private RentalHistoryRepository rentalHistoryRepository;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @Mock
    private RentalPolicy rentalPolicy;

    @InjectMocks
    private BookCommandService bookCommandService;

    @Test
    void 도서를_저장할_수_있다() {
        String title = "JPA";
        String author = "김영한";
        Category 문학 = new Category(1L, "문학");
        Book book = new Book(title, new Author(author), new BookCategories(List.of(문학)));

        when(bookRepository.save(book)).thenReturn(book);

        bookCommandService.save(book);

        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void 도서의_카테고리를_변경할_수_있다() {
        Book book = mock(Book.class);
        BookCategories bookCategories = mock(BookCategories.class);
        BookCategoriesChangedEvent event = mock(BookCategoriesChangedEvent.class);

        when(book.changeCategories(bookCategories)).thenReturn(event);

        bookCommandService.changeBookCategories(book, bookCategories);

        verify(applicationEventPublisher, times(1)).publishEvent(event);
    }

    @Test
    void 훼손된_도서를_대여할_경우_예외가_발생한다() {
        Book book = new Book(
                "JPA",
                new Author("김영한"),
                new BookCategories(List.of(new Category("IT")))
        );
        book.damaged();

        BookApplicationException exception = assertThrows(BookApplicationException.class, () -> {
            bookCommandService.rental(book);
        });
        assertEquals(
                ErrorType.INVALID_RESOURCE,
                exception.getType()
        );
    }

    @Test
    void 도서를_대여할_수_있다() {
        Book book = mock(Book.class);
        LocalDateTime rentedAt = LocalDateTime.now();
        LocalDateTime expiredAt = rentedAt.plusDays(30);

        when(rentalPolicy.getRentedAt()).thenReturn(rentedAt);
        when(rentalPolicy.getExpiredAt(rentedAt)).thenReturn(expiredAt);

        bookCommandService.rental(book);

        verify(book, times(1)).rental();
    }

    @Test
    void 도서를_대여하면_대여이력을_저장한다() {
        Book book = mock(Book.class);
        LocalDateTime rentedAt = LocalDateTime.now();
        LocalDateTime expiredAt = rentedAt.plusDays(30);

        when(rentalPolicy.getRentedAt()).thenReturn(rentedAt);
        when(rentalPolicy.getExpiredAt(rentedAt)).thenReturn(expiredAt);

        bookCommandService.rental(book);

        verify(book, times(1)).rental();
        verify(rentalHistoryRepository, times(1)).save(any());
    }
}
