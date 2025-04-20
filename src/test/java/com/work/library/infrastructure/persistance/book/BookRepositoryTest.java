package com.work.library.infrastructure.persistance.book;

import com.work.library.annotations.RepositoryTest;
import com.work.library.domain.book.BookStatus;
import com.work.library.entity.book.BookEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

@RepositoryTest
class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @Test
    void 도서를_저장하고_조회할_수_있다() {
        String author = "김영한";
        String title = "JPA";
        BookEntity bookEntity = new BookEntity(author, title);

        BookEntity savedBookEntity = bookRepository.save(bookEntity);
        BookEntity result = bookRepository.findById(savedBookEntity.getId()).orElseThrow();

        assertEquals(author, result.getAuthor());
        assertEquals(title, result.getTitle());
    }

    @Test
    void 도서는_기본적으로_AVAILABLE_상태이다() {
        String author = "김영한";
        String title = "JPA";
        BookEntity bookEntity = new BookEntity(author, title);

        BookEntity savedBookEntity = bookRepository.save(bookEntity);
        BookEntity result = bookRepository.findById(savedBookEntity.getId()).orElseThrow();

        assertEquals(BookStatus.AVAILABLE.name(), result.getStatus().name());
    }
}
