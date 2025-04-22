package com.work.library.application;

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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookUpdateApplicationTest {
    @Mock
    private BookQueryService bookQueryService;

    @Mock
    private BookCommandService bookCommandService;

    @InjectMocks
    private BookUpdateApplication bookUpdateApplication;

    @Test
    void 도서는_훼손될_수_있다() {
        Long bookId = 1L;
        Book book = new Book(
                bookId,
                "JPA",
                new Author("김영한"),
                BookStatus.AVAILABLE,
                new BookCategories(List.of(new Category("IT")))
        );

        when(bookQueryService.getById(book.getId())).thenReturn(book);
        bookUpdateApplication.damage(bookId);

        assertEquals(
            BookStatus.DAMAGED,
            book.getStatus()
        );
    }
}
