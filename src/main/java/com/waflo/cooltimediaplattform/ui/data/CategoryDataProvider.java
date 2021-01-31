package com.waflo.cooltimediaplattform.ui.data;

import com.vaadin.flow.component.crud.CrudFilter;
import com.vaadin.flow.data.provider.AbstractDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.waflo.cooltimediaplattform.backend.model.Category;
import com.waflo.cooltimediaplattform.backend.service.CategoryService;

import java.util.stream.Stream;

public class CategoryDataProvider extends AbstractDataProvider<Category, CrudFilter> {
    private CategoryService categoryService;

    public CategoryDataProvider(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public boolean isInMemory() {
        return false;
    }

    @Override
    public int size(Query<Category, CrudFilter> query) {
        return (int) fetch(query).count();
    }

    @Override
    public Stream<Category> fetch(Query<Category, CrudFilter> query) {
        return null;
    }

    public void persist(Category item) {
        categoryService.save(item);
    }

    public void delete(Category item) {
        categoryService.delete(item);
    }
}
