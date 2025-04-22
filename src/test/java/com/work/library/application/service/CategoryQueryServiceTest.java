package com.work.library.application.service;

import com.work.library.domain.category.Category;
import com.work.library.domain.category.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryQueryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryQueryService categoryQueryService;

    @Test
    void 카테고리_ID_LIST_로_카테고리_목록을_조회할_수_있다() {
        String 문학 = "문학";
        String IT = "IT";
        String 인문학 = "인문학";
        Category category1 = new Category(문학);
        Category category2 = new Category(IT);
        Category category3 = new Category(인문학);
        List<Long> categoryIdList = List.of(1L, 2L, 3L);

        when(categoryRepository.findAllByIds(categoryIdList))
                .thenReturn(List.of(category1, category2, category3));
        List<Category> result = categoryQueryService.findAllByIds(categoryIdList);

        assertFalse(result.isEmpty());
        assertTrue(result.contains(category1));
        assertTrue(result.contains(category2));
        assertTrue(result.contains(category3));
    }

    @Test
    void 모든_카테고리를_조회할_수_있다() {
        String 문학 = "문학";
        String IT = "IT";
        String 인문학 = "인문학";
        Category category1 = new Category(문학);
        Category category2 = new Category(IT);
        Category category3 = new Category(인문학);

        when(categoryRepository.findAll())
                .thenReturn(List.of(category1, category2, category3));
        categoryQueryService.findAll();

        verify(categoryRepository, times(1)).findAll();
    }
}
