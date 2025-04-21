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

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookCommandServiceTest {

    @Mock
    private BookRepository bookRepository;

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
}