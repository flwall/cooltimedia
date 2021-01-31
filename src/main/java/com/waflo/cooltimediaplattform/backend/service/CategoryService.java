package com.waflo.cooltimediaplattform.backend.service;

import com.waflo.cooltimediaplattform.backend.model.Category;
import com.waflo.cooltimediaplattform.backend.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Optional<Category> findById(long id) {
        return categoryRepository.findById(id);
    }

    public void save(Category item) {
        categoryRepository.save(item);
    }
    public void delete(Category item){
        categoryRepository.delete(item);
    }
}
