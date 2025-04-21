package com.work.library.application.service;

import com.work.library.application.exception.BookApplicationException;
import com.work.library.application.exception.ErrorType;
import com.work.library.domain.book.Author;
import com.work.library.domain.book.Book;
import com.work.library.domain.book.BookCategories;
import com.work.library.domain.book.repository.BookRepository;
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
class BookQueryServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookQueryService bookQueryService;

    @Test
    void 카테고리별_도서목록을_조회할_수_있다() {
        Category category = new Category("문학");
        List<Category> requestedCategories = List.of(category);
        Book book = new Book("JPA", new Author("김영한"), new BookCategories(requestedCategories));

        when(bookRepository.findAllByCategoryList(requestedCategories))
                .thenReturn(List.of(book));
        List<Book> bookList = bookQueryService.findAllByCategories(requestedCategories);
        Book result = bookList.getFirst();

        assertEquals(1, bookList.size());
        assertEquals("JPA", result.getTitle());
        assertEquals("김영한", result.getAuthor());
        assertTrue(
                result.getCategories().getNames().contains(
                        "문학"
                )
        );
        verify(bookRepository, times(1)).findAllByCategoryList(requestedCategories);
    }

    @Test
    void 지은이_책_제목을_통해_도서를_조회할_수_있다() {
        String title = "JPA";
        Author author = new Author("김영한");
        Category category = new Category("문학");
        BookCategories bookCategories = new BookCategories(List.of(category));
        Book book = new Book(title, author, bookCategories);

        when(bookRepository.searchByTitleOrAuthor(title, author))
                .thenReturn(List.of(book));

        bookQueryService.searchByTitleOrAuthor(title, author);

        verify(bookRepository, times(1)).searchByTitleOrAuthor(title, author);
    }

    @Test
    void 존재하지않는_도서를_조회할_경우_예외가_발생한다() {
        Long bookId = 1L;

        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        BookApplicationException exception = assertThrows(BookApplicationException.class, () -> {
            bookQueryService.getById(bookId);
        });
        assertEquals(
                ErrorType.INVALID_PARAMETER,
                exception.getType()
        );
    }
}
