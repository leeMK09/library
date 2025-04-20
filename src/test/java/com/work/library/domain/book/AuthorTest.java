package com.work.library.domain.book;

import com.work.library.domain.book.exception.InvalidAuthorException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthorTest {
    @Test
    void null_이름으로_Author를_생성하면_예외가_발생한다() {
        InvalidAuthorException exception = assertThrows(InvalidAuthorException.class, () -> new Author(null));

        assertEquals(ErrorMessage.AUTHOR_BLANK, exception.getMessage());
    }

    @Test
    void 유효한_이름으로_Author를_생성하면_정상적으로_생성된다() {
        String name = "김영한";
        Author author = new Author(name);

        assertEquals(name, author.value());
    }
}
