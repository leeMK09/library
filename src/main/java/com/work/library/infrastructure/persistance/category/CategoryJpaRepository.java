package com.work.library.infrastructure.persistance.category;

import com.work.library.entity.category.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryJpaRepository extends JpaRepository<CategoryEntity, Long> {
    List<CategoryEntity> findAllByIdIn(List<Long> idList);
}
