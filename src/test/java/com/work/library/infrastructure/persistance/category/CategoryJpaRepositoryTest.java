package com.work.library.infrastructure.persistance.category;

import com.work.library.annotations.RepositoryTest;
import com.work.library.entity.category.CategoryEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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

    @Test
    void 카테고리_ID_LIST_로_카테고리_목록을_조회할_수_있다() {
        String 문학 = "문학";
        String IT = "IT";
        String 인문학 = "인문학";
        CategoryEntity category1 = new CategoryEntity(문학);
        CategoryEntity category2 = new CategoryEntity(IT);
        CategoryEntity category3 = new CategoryEntity(인문학);
        List<CategoryEntity> savedCategories = categoryRepository.saveAll(List.of(category1, category2, category3));

        List<CategoryEntity> result = categoryRepository.findAllByIdIn(savedCategories.stream().map(CategoryEntity::getId).toList());
        List<String> foundCategoryNameList = result.stream().map(CategoryEntity::getName).toList();

        assertFalse(result.isEmpty());
        assertTrue(foundCategoryNameList.contains("문학"));
        assertTrue(foundCategoryNameList.contains("IT"));
        assertTrue(foundCategoryNameList.contains("인문학"));
    }
}
