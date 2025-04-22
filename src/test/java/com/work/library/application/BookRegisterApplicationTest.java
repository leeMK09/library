package com.work.library.application;

import com.work.library.application.dto.command.RegisterBookCommand;
import com.work.library.application.exception.BookApplicationException;
import com.work.library.application.exception.ErrorType;
import com.work.library.application.service.BookCommandService;
import com.work.library.application.service.BookQueryService;
import com.work.library.application.service.CategoryQueryService;
import com.work.library.domain.book.Author;
import com.work.library.domain.book.Book;
import com.work.library.domain.book.BookCategories;
import com.work.library.domain.category.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookRegisterApplicationTest {
    @Mock
    private BookQueryService bookQueryService;

    @Mock
    private BookCommandService bookCommandService;

    @Mock
    private CategoryQueryService categoryQueryService;

    @InjectMocks
    private BookRegisterApplication bookRegisterApplication;

    @Test
    void 생성하려는_도서의_카테고리가_존재하지_않는다면_예외가_발생한다() {
        RegisterBookCommand command = new RegisterBookCommand(
                "JPA",
                "김영한",
                List.of(1L, 2L)
        );

        when(categoryQueryService.findAllByIds(command.categoryIdList()))
                .thenReturn(List.of());

        BookApplicationException exception = assertThrows(BookApplicationException.class, () -> {
            bookRegisterApplication.save(command);
        });
        assertEquals(
                ErrorType.INVALID_PARAMETER,
                exception.getType()
        );
    }

    @Test
    void 생성하려는_도서의_카테고리_중_존재하지_않는_카테고리가_있다면_예외가_발생한다() {
        Category category1 = new Category(1L, "문학");
        Category category2 = new Category(2L, "IT");
        RegisterBookCommand command = new RegisterBookCommand(
                "JPA",
                "김영한",
                List.of(category1.getId(), category2.getId())
        );

        when(categoryQueryService.findAllByIds(command.categoryIdList()))
                .thenReturn(List.of(category1));

        BookApplicationException exception = assertThrows(BookApplicationException.class, () -> {
            bookRegisterApplication.save(command);
        });
        assertEquals(
                ErrorType.INVALID_PARAMETER,
                exception.getType()
        );
    }

    @Test
    void 요청한_지은이_와_제목으로_등록된_책이_이미_있다면_예외가_발생한다() {
        String title = "JPA";
        String author = "김영한";
        Category category = new Category(2L, "IT");
        RegisterBookCommand command = new RegisterBookCommand(
                title,
                author,
                List.of(category.getId())
        );
        Book book = new Book(
                1L,
                title,
                new Author(author),
                new BookCategories(List.of(category))
        );

        when(categoryQueryService.findAllByIds(command.categoryIdList()))
                .thenReturn(List.of(category));
        when(bookQueryService.searchByTitleAndAuthor(title, author))
                .thenReturn(Optional.of(book));

        BookApplicationException exception = assertThrows(BookApplicationException.class, () -> {
            bookRegisterApplication.save(command);
        });
        assertEquals(
                ErrorType.RESOURCE_DUPLICATED,
                exception.getType()
        );
    }

    @Test
    void 유효한_요청을_하면_도서를_생성한다() {
        Category category1 = new Category(1L, "문학");
        Category category2 = new Category(2L, "IT");
        RegisterBookCommand command = new RegisterBookCommand(
                "JPA",
                "김영한",
                List.of(category1.getId(), category2.getId())
        );

        when(categoryQueryService.findAllByIds(command.categoryIdList()))
                .thenReturn(List.of(category1, category2));
        bookRegisterApplication.save(command);

        verify(bookCommandService, times(1)).save(any());
    }
}
