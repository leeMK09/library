package com.work.library.domain.book;

import com.work.library.domain.ErrorMessage;
import com.work.library.domain.book.exception.BookException;
import com.work.library.domain.category.Category;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {
    private final BookCategories categoriesFixture = new BookCategories(
        List.of(new Category("액션"))
    );

    @Test
    void null_제목으로_Book을_생성하면_예외가_발생한다() {
        BookException exception = assertThrows(
                BookException.class,
                () -> new Book(null, new Author("김영한"), categoriesFixture)
        );

        assertEquals(ErrorMessage.TITLE_BLANK, exception.getMessage());
    }

    @Test
    void 빈_제목으로_Book을_생성하면_예외가_발생한다() {
        BookException exception = assertThrows(
                BookException.class,
                () -> new Book(" ", new Author("김영한"), categoriesFixture)
        );

        assertEquals(ErrorMessage.TITLE_BLANK, exception.getMessage());
    }

    @Test
    void 빈_저자로_Book을_생성하면_예외가_발생한다() {
        BookException exception = assertThrows(
                BookException.class,
                () -> new Book("JPA", null, categoriesFixture)
        );

        assertEquals(ErrorMessage.AUTHOR_EMPTY, exception.getMessage());
    }

    @Test
    void null_카테고리_리스트로_Book을_생성하면_예외가_발생한다() {
        String title = "JPA";
        Author author = new Author("김영한");
        BookException exception = assertThrows(
                BookException.class,
                () -> new Book(title, author, null)
        );

        assertEquals(ErrorMessage.BOOK_CATEGORIES_BLANK, exception.getMessage());
    }

    @Test
    void 유효한_지은이_제목_카테고리_리스트로_Book을_생성하면_정상적으로_생성된다() {
        String title = "JPA";
        Author author = new Author("김영한");
        Book book = new Book(title, author, categoriesFixture);

        assertNotNull(book);
        assertEquals(title, book.getTitle());
        assertEquals(author.value(), book.getAuthor());
        assertTrue(book.getCategories().size() > 0);
    }

    @Test
    void 책은_훼손될_수_있다() {
        String title = "JPA";
        Author author = new Author("김영한");
        Book book = new Book(title, author, categoriesFixture);

        book.damaged();
        BookStatus status = book.getStatus();

        assertEquals(BookStatus.DAMAGED, status);
    }
}
