package com.waflo.cooltimediaplattform.repository;

import com.waflo.cooltimediaplattform.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
