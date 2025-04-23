package com.work.library.application;

import com.work.library.application.exception.BookApplicationException;
import com.work.library.application.exception.ErrorType;
import com.work.library.application.service.BookCommandService;
import com.work.library.application.service.BookQueryService;
import com.work.library.domain.book.Author;
import com.work.library.domain.book.Book;
import com.work.library.domain.book.BookCategories;
import com.work.library.domain.book.BookStatus;
import com.work.library.domain.category.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookUpdateApplicationTest {
    @Mock
    private BookQueryService bookQueryService;

    @Mock
    private BookCommandService bookCommandService;

    @InjectMocks
    private BookUpdateApplication bookUpdateApplication;

    private Book book = new Book(
            1L,
            "JPA",
            new Author("김영한"),
            BookStatus.AVAILABLE,
            new BookCategories(List.of(new Category("IT")))
    );

    @Test
    void 도서는_훼손될_수_있다() {
        when(bookQueryService.getById(book.getId())).thenReturn(book);
        bookUpdateApplication.damage(book.getId());

        assertEquals(
            BookStatus.DAMAGED,
            book.getStatus()
        );
    }

    @Test
    void 도서가_대여중_이라면_훼손될_수_없다() {
        book.rental();
        when(bookQueryService.getById(book.getId())).thenReturn(book);

        BookApplicationException exception = assertThrows(BookApplicationException.class, () -> {
            bookUpdateApplication.damage(book.getId());
        });
        assertEquals(
                ErrorType.RESOURCE_NOT_FOUND,
                exception.getType()
        );
    }

    @Test
    void 도서_대여_요청시_이미_대여중이라면_예외가_발생한다() {
        book.rental();

        when(bookQueryService.getById(book.getId())).thenReturn(book);

        BookApplicationException exception = assertThrows(BookApplicationException.class, () -> {
            bookUpdateApplication.rental(book.getId());
        });
        assertEquals(
                ErrorType.INVALID_RESOURCE,
                exception.getType()
        );
    }

    @Test
    void 도서를_대여할_수_있다() {
        when(bookQueryService.getById(book.getId())).thenReturn(book);
        when(bookCommandService.rental(book)).thenReturn(book);

        bookUpdateApplication.rental(book.getId());

        verify(bookCommandService).rental(book);
    }
}
