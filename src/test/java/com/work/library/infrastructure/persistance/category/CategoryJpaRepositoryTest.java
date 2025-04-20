package com.work.library.infrastructure.persistance.category;

import com.work.library.annotations.RepositoryTest;
import com.work.library.entity.category.CategoryEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

@RepositoryTest
class CategoryJpaRepositoryTest {
    @Autowired
    private CategoryJpaRepository categoryRepository;

    @Test
    void 카테고리를_저장하고_조회할_수_있다() {
        String name = "문학";
        CategoryEntity categoryEntity = new CategoryEntity(name);

        CategoryEntity savedCategoryEntity = categoryRepository.save(categoryEntity);
        CategoryEntity result = categoryRepository.findById(savedCategoryEntity.getId()).orElseThrow();

        assertEquals(name, result.getName());
    }
}
