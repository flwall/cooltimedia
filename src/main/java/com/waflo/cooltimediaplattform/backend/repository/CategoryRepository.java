package com.waflo.cooltimediaplattform.backend.repository;

import com.waflo.cooltimediaplattform.backend.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
