package com.work.library.domain.book;

import com.work.library.domain.ErrorMessage;
import com.work.library.domain.book.exception.BookCategoriesException;
import com.work.library.domain.category.Category;
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

    @Test
    void 하나의_책에_같은_카테고리가_중복되었다면_예외가_발생한다() {
        String name = "문학";
        Category category1 = new Category(name);
        Category category2 = new Category(name);
        BookCategoriesException exception = assertThrows(
                BookCategoriesException.class,
                () -> new BookCategories(List.of(category1, category2))
        );

        assertEquals(ErrorMessage.DUPLICATED_BOOK_CATEGORIES_BLANK, exception.getMessage());
    }
}
