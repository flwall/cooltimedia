package com.waflo.cooltimediaplattform.service;

import com.waflo.cooltimediaplattform.model.Category;
import com.waflo.cooltimediaplattform.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Optional<Category> findById(long id) {
        return categoryRepository.findById(id);
    }
}
