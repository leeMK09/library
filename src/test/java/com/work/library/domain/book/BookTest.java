package com.work.library.domain.book;

import com.work.library.domain.ErrorMessage;
import com.work.library.domain.book.exception.BookException;
import com.work.library.domain.category.Category;
import com.work.library.entity.book.BookEntity;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {
    private final BookCategories anyThingCategories = new BookCategories(
        List.of(new Category("문학"))
    );

    @Test
    void null_제목으로_Book을_생성하면_예외가_발생한다() {
        BookException exception = assertThrows(
                BookException.class,
                () -> new Book(null, new Author("김영한"), anyThingCategories)
        );

        assertEquals(ErrorMessage.TITLE_BLANK, exception.getMessage());
    }

    @Test
    void 빈_제목으로_Book을_생성하면_예외가_발생한다() {
        BookException exception = assertThrows(
                BookException.class,
                () -> new Book(" ", new Author("김영한"), anyThingCategories)
        );

        assertEquals(ErrorMessage.TITLE_BLANK, exception.getMessage());
    }

    @Test
    void 빈_저자로_Book을_생성하면_예외가_발생한다() {
        BookException exception = assertThrows(
                BookException.class,
                () -> new Book("JPA", null, anyThingCategories)
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
        Book book = new Book(title, author, anyThingCategories);

        assertNotNull(book);
        assertEquals(title, book.getTitle());
        assertEquals(author.value(), book.getAuthor());
        assertTrue(book.getCategories().size() > 0);
    }

    @Test
    void 책은_훼손될_수_있다() {
        String title = "JPA";
        Author author = new Author("김영한");
        Book book = new Book(title, author, anyThingCategories);

        book.damaged();
        BookStatus status = book.getStatus();

        assertEquals(BookStatus.DAMAGED, status);
    }

    @Test
    void 책은_카테고리를_변경할_수_있다() {
        String title = "JPA";
        Author author = new Author("김영한");
        BookCategories bookCategories = new BookCategories(List.of(new Category("예전카테고리")));
        Book book = new Book(title, author, bookCategories);

        BookCategories newBookCategories = new BookCategories(List.of(new Category("새로운카테고리")));
        book.changeCategories(newBookCategories);
        BookCategories changedCategories = book.getCategories();

        assertTrue(
                changedCategories.getNames().contains("새로운카테고리")
        );
        assertFalse(
                changedCategories.getNames().contains("예전카테고리")
        );
    }

    @Test
    void 변경하려는_카테고리가_없다면_예외가_발생한다() {
        String title = "JPA";
        Author author = new Author("김영한");
        BookCategories bookCategories = new BookCategories(List.of(new Category("예전카테고리")));
        Book book = new Book(title, author, bookCategories);

        BookException exception = assertThrows(BookException.class, () -> book.changeCategories(null));
        assertEquals(ErrorMessage.BOOK_CATEGORIES_BLANK, exception.getMessage());
    }

    @Test
    void 책은_자신이_훼손되었는지_판단할_수_있다() {
        Book damagedBook = new Book("JPA", new Author("김영한"), anyThingCategories);
        Book normalBook = new Book("JPA", new Author("김영한"), anyThingCategories);
        damagedBook.damaged();

        boolean damaged = damagedBook.isDamaged();
        boolean unDamaged = normalBook.isDamaged();

        assertTrue(damaged);
        assertFalse(unDamaged);
    }

    @Test
    void 책을_대여할_수_있다() {
        Book book = new Book("JPA", new Author("김영한"), anyThingCategories);
        book.rental();

        assertEquals(BookStatus.RENTED, book.getStatus());
    }

    @Test
    void 책은_자신이_대여중인지_판단할_수_있다() {
        Book rentedBook = new Book("JPA", new Author("김영한"), anyThingCategories);
        Book normalBook = new Book("JPA", new Author("김영한"), anyThingCategories);
        rentedBook.rental();

        boolean rented = rentedBook.isRented();
        boolean unRented = normalBook.isRented();

        assertTrue(rented);
        assertFalse(unRented);
    }

    @Test
    void 도메인에_올바른_엔티티를_생성할_수_있다() {
        Book book = new Book("JPA", new Author("김영한"), anyThingCategories);

        BookEntity entity = book.toEntity();

        assertEquals("JPA", entity.getTitle());
        assertEquals("김영한", entity.getAuthor());
    }

    @Test
    void 이미_영속화된_도메인이라면_ID를_포함한_엔티티를_생성할_수_있다() {
        Book book = new Book(1L, "JPA", new Author("김영한"), BookStatus.AVAILABLE, anyThingCategories);

        BookEntity entity = book.toRegisteredEntity();

        assertNotNull(entity.getId());
        assertEquals(1L, entity.getId());
    }
}
