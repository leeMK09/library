package com.work.library.application;

import com.work.library.application.dto.command.ChangeBookCategoriesCommand;
import com.work.library.application.exception.BookApplicationException;
import com.work.library.application.exception.ErrorType;
import com.work.library.application.service.BookCommandService;
import com.work.library.application.service.BookQueryService;
import com.work.library.application.service.CategoryQueryService;
import com.work.library.domain.book.Book;
import com.work.library.domain.book.BookCategories;
import com.work.library.domain.book.event.BookCategoriesChangedEvent;
import com.work.library.domain.category.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookCategoriesUpdateApplicationTest {
    @Mock
    private BookQueryService bookQueryService;

    @Mock
    private BookCommandService bookCommandService;

    @Mock
    private CategoryQueryService categoryQueryService;

    @InjectMocks
    private BookCategoriesUpdateApplication bookCategoriesUpdateApplication;

    @Test
    void 카테고리_변경시_존재하지않는_카테고리로_변경할_경우_예외가_발생한다() {
        ChangeBookCategoriesCommand commend = new ChangeBookCategoriesCommand(
                1L,
                List.of(1L, 2L)
        );

        when(categoryQueryService.findAllByIdList(commend.newCategoryIdList()))
                .thenReturn(List.of());

        BookApplicationException exception = assertThrows(BookApplicationException.class, () -> {
            bookCategoriesUpdateApplication.changeBookCategories(commend);
        });
        assertEquals(
                ErrorType.INVALID_PARAMETER,
                exception.getType()
        );
    }

    @Test
    void 카테고리_변경시_카테고리_중_존재하지_않는_카테고리가_있다면_예외가_발생한다() {
        Category category1 = new Category(1L, "문학");
        Category category2 = new Category(2L, "IT");
        ChangeBookCategoriesCommand commend = new ChangeBookCategoriesCommand(
                1L,
                List.of(category1.getId(), category2.getId())
        );

        when(categoryQueryService.findAllByIdList(commend.newCategoryIdList()))
                .thenReturn(List.of(category1));

        BookApplicationException exception = assertThrows(BookApplicationException.class, () -> {
            bookCategoriesUpdateApplication.changeBookCategories(commend);
        });
        assertEquals(
                ErrorType.INVALID_PARAMETER,
                exception.getType()
        );
    }

    @Test
    void 도서의_카테고리를_변경할_수_있다() {
        Category category1 = new Category(1L, "문학");
        Category category2 = new Category(2L, "IT");
        ChangeBookCategoriesCommand commend = new ChangeBookCategoriesCommand(
                0L,
                List.of(category1.getId(), category2.getId())
        );
        Book book = mock(Book.class);

        when(bookQueryService.getById(book.getId())).thenReturn(book);
        when(categoryQueryService.findAllByIdList(List.of(category1.getId(), category2.getId())))
                .thenReturn(List.of(category1, category2));

        bookCategoriesUpdateApplication.changeBookCategories(commend);

        verify(bookCommandService, times(1)).changeBookCategories(any(), any());
    }
}
