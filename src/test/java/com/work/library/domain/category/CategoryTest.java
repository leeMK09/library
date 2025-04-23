package com.work.library.domain.category;

import com.work.library.domain.ErrorMessage;
import com.work.library.domain.category.exception.InvalidCategoryException;
import com.work.library.entity.category.CategoryEntity;
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

    @Test
    void 도메인에_올바른_엔티티를_생성할_수_있다() {
        Category category = new Category("IT");

        CategoryEntity entity = category.toEntity();

        assertNotNull(entity);
        assertEquals("IT", entity.getName());
    }

    @Test
    void 이미_영속화된_도메인이라면_ID를_포함한_엔티티를_생성할_수_있다() {
        Category category = new Category(1L, "IT");

        CategoryEntity entity = category.toRegisteredEntity();

        assertNotNull(entity.getId());
        assertEquals("IT", entity.getName());
    }
}
