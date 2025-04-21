package com.work.library.application.service;

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
    }
}