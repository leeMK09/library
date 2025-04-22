package com.work.library.infrastructure.persistance.category;

import com.work.library.annotations.RepositoryTest;
import com.work.library.domain.category.Category;
import com.work.library.domain.category.repository.CategoryRepository;
import com.work.library.entity.category.CategoryEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RepositoryTest
@Import(CategoryRepositoryImpl.class)
public class CategoryRepositoryTest {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryJpaRepository categoryJpaRepository;

    @Test
    void 카테고리를_저장할_수_있다() {
        String name = "문학";
        Category category = new Category(name);

        Category result = categoryRepository.save(category);

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
        List<CategoryEntity> savedCategories = categoryJpaRepository.saveAll(List.of(category1, category2, category3));

        List<Category> foundCategories = categoryRepository.findAllByIds(savedCategories.stream().map(CategoryEntity::getId).toList());
        List<String> foundCategoryNames = foundCategories.stream().map(Category::getName).toList();

        assertFalse(foundCategories.isEmpty());
        assertTrue(foundCategoryNames.contains("문학"));
        assertTrue(foundCategoryNames.contains("IT"));
        assertTrue(foundCategoryNames.contains("인문학"));
    }

    @Test
    void 모든_카테고리를_조회할_수_있다() {
        CategoryEntity 문학 = new CategoryEntity("문학");
        CategoryEntity IT = new CategoryEntity("IT");
        CategoryEntity 인문학 = new CategoryEntity("인문학");
        categoryJpaRepository.saveAll(List.of(문학, IT, 인문학));

        List<Category> foundCategories = categoryRepository.findAll();
        List<String> nameList = foundCategories.stream().map(Category::getName).toList();

        assertFalse(foundCategories.isEmpty());
        assertTrue(nameList.contains(문학.getName()));
        assertTrue(nameList.contains(IT.getName()));
        assertTrue(nameList.contains(인문학.getName()));
    }
}
