package com.work.library.domain.category;

import com.work.library.domain.ErrorMessage;
import com.work.library.domain.category.exception.InvalidCategoryException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {
    @Test
    void null_이름으로_Category를_생성하면_예외가_발생한다() {
        InvalidCategoryException exception = assertThrows(
                InvalidCategoryException.class,
                () -> new Category(null)
        );

        assertEquals(ErrorMessage.CATEGORY_NAME_BLANK, exception.getMessage());
    }

    @Test
    void 빈_이름으로_Category를_생성하면_예외가_발생한다() {
        InvalidCategoryException exception = assertThrows(
                InvalidCategoryException.class,
                () -> new Category(" ")
        );

        assertEquals(ErrorMessage.CATEGORY_NAME_BLANK, exception.getMessage());
    }

    @Test
    void 같은_이름의_카테고리가_존재한다면_둘은_같은_카테고리이다() {
        String name = "문학";
        Category category1 = new Category(name);
        Category category2 = new Category(name);

        assertEquals(category1, category2);
    }
}
