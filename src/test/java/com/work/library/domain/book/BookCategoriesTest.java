package com.work.library.domain.book;

import com.work.library.domain.ErrorMessage;
import com.work.library.domain.book.exception.BookCategoriesException;
import com.work.library.domain.category.Category;
import com.work.library.entity.book.BookCategoryMappingEntity;
import com.work.library.entity.book.BookEntity;
import com.work.library.entity.category.CategoryEntity;
import org.junit.jupiter.api.Nested;
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

    @Test
    void 도메인에_올바른_엔티티를_생성할_수_있다() {
        BookCategories bookCategories = new BookCategories(List.of(
                new Category("IT"),
                new Category("문학")
        ));

        List<BookCategoryMappingEntity> entity = bookCategories.toEntity(new BookEntity("JPA", "김영한"));

        assertNotNull(entity);
        assertEquals(2, entity.size());
        assertEquals("IT", entity.get(0).getCategory().getName());
        assertEquals("문학", entity.get(1).getCategory().getName());
        assertEquals("JPA", entity.get(0).getBook().getTitle());
        assertEquals("김영한", entity.get(0).getBook().getAuthor());
    }

    @Test
    void 엔티티_리스트를_통해_올바른_도메인을_생성할_수_있다() {
        List<CategoryEntity> entities = List.of(
                new CategoryEntity("IT"),
                new CategoryEntity("문학")
        );

        BookCategories bookCategories = BookCategories.fromEntities(entities);
        List<String> result = bookCategories.getNames();

        assertEquals(2, result.size());
        assertTrue(result.contains("IT"));
        assertTrue(result.contains("문학"));
    }
}
