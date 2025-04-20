package com.work.library.infrastructure.persistance.category;

import com.work.library.annotations.RepositoryTest;
import com.work.library.domain.category.Category;
import com.work.library.domain.category.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RepositoryTest
@Import(CategoryRepositoryImpl.class)
public class CategoryRepositoryTest {
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void 카테고리를_저장할_수_있다() {
        String name = "문학";
        Category category = new Category(name);

        Category result = categoryRepository.save(category);

        assertEquals(name, result.getName());
    }
}
