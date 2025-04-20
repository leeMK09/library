package com.work.library.domain.book;

import com.work.library.domain.ErrorMessage;
import com.work.library.domain.book.exception.InvalidBookException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {
    @Test
    void null_제목으로_Book을_생성하면_예외가_발생한다() {
        InvalidBookException exception = assertThrows(
                InvalidBookException.class,
                () -> new Book(null, new Author("김영한"))
        );

        assertEquals(ErrorMessage.TITLE_BLANK, exception.getMessage());
    }

    @Test
    void 빈_제목으로_Book을_생성하면_예외가_발생한다() {
        InvalidBookException exception = assertThrows(
                InvalidBookException.class,
                () -> new Book(" ", new Author("김영한"))
        );

        assertEquals(ErrorMessage.TITLE_BLANK, exception.getMessage());
    }

    @Test
    void 빈_저자로_Book을_생성하면_예외가_발생한다() {
        InvalidBookException exception = assertThrows(
                InvalidBookException.class,
                () -> new Book("JPA", null)
        );

        assertEquals(ErrorMessage.AUTHOR_EMPTY, exception.getMessage());
    }

    @Test
    void 유효한_지은이_와_제목으로_Book을_생성하면_정상적으로_생성된다() {
        String title = "JPA";
        Author author = new Author("김영한");
        Book book = new Book(title, author);

        assertNotNull(book);
        assertEquals(title, book.getTitle());
        assertEquals(author.value(), book.getAuthor());
    }
}
