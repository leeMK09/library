package com.work.library.domain.book;

import com.work.library.domain.ErrorMessage;
import com.work.library.domain.book.exception.BookCategoriesException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookCategoriesTest {
    @Test
    void null_카테고리_리스트로_BookCategories를_생성하면_예외가_발생한다() {
        BookCategoriesException exception = assertThrows(
                BookCategoriesException.class,
                () -> new BookCategories(null)
        );

        assertEquals(ErrorMessage.BOOK_CATEGORIES_BLANK, exception.getMessage());
    }

    @Test
    void 빈_카테고리_리스트로_BookCategories를_생성하면_예외가_발생한다() {
        BookCategoriesException exception = assertThrows(
                BookCategoriesException.class,
                () -> new BookCategories(List.of())
        );

        assertEquals(ErrorMessage.BOOK_CATEGORIES_BLANK, exception.getMessage());
    }
}
