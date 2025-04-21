package com.work.library.application.service.event;

import com.work.library.domain.book.event.BookCategoriesChangedEvent;
import com.work.library.domain.book.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookCategoriesChangedEventHandlerTest {
    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookCategoriesChangedEventHandler bookCategoriesChangedEventHandler;

    @Test
    void 도서와_카테고리를_재할당할_수_있다() {
        BookCategoriesChangedEvent event = mock(BookCategoriesChangedEvent.class);

        bookCategoriesChangedEventHandler.handleEvent(event);

        verify(bookRepository, times(1)).remapCategoriesToBook(
                event.book(),
                event.newBookCategories()
        );
    }
}
