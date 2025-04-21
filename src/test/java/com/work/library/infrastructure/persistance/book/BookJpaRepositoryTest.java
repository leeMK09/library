package com.work.library.infrastructure.persistance.book;

import com.work.library.annotations.RepositoryTest;
import com.work.library.domain.book.BookStatus;
import com.work.library.entity.book.BookEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RepositoryTest
class BookJpaRepositoryTest {
    @Autowired
    private BookJpaRepository bookRepository;

    @Test
    void 도서를_저장하고_조회할_수_있다() {
        String author = "김영한";
        String title = "JPA";
        BookEntity bookEntity = new BookEntity(title, author);

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

    @Test
    void 지은이와_책_제목으로_도서를_검색할_수_있다() {
        String author = "김영한";
        String title = "JPA";
        BookEntity bookEntity = new BookEntity(title, author);
        bookRepository.save(bookEntity);

        List<BookEntity> result = bookRepository.searchByTitleOrAuthor(title, author);

        assertEquals(title, result.getFirst().getTitle());
        assertEquals(author, result.getFirst().getAuthor());
    }

    @Test
    void 지은이_혹은_책_제목_중_하나가_없더라도_일치하는_도서를_검색할_수_있다() {
        String author = "김영한";
        String title = "JPA";
        BookEntity bookEntity = new BookEntity(title, author);
        bookRepository.save(bookEntity);

        List<BookEntity> foundBookWithoutAuthor = bookRepository.searchByTitleOrAuthor(title, null);
        List<BookEntity> foundBookWithoutTitle = bookRepository.searchByTitleOrAuthor(null, author);

        assertEquals(title, foundBookWithoutAuthor.getFirst().getTitle());
        assertEquals(author, foundBookWithoutAuthor.getFirst().getAuthor());
        assertEquals(title, foundBookWithoutTitle.getFirst().getTitle());
        assertEquals(author, foundBookWithoutTitle.getFirst().getAuthor());
    }
}
