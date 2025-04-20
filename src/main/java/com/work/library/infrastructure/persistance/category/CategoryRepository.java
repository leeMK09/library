package com.work.library.infrastructure.persistance.category;

import com.work.library.entity.category.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
}
