package com.work.library.application;

import com.work.library.application.service.CategoryQueryService;
import com.work.library.domain.category.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategorySearchApplicationTest {

    @Mock
    private CategoryQueryService categoryQueryService;

    @InjectMocks
    private CategorySearchApplication categorySearchApplication;

    @Test
    void 모든_카테고리를_조회할_수_있다() {
        List<Category> categories = List.of(
                new Category("문학"),
                new Category("IT"),
                new Category("인문학")
        );

        when(categoryQueryService.findAll()).thenReturn(categories);
        categorySearchApplication.findAll();

        verify(categoryQueryService, times(1)).findAll();
    }
}
