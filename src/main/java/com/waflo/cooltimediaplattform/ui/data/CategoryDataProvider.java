package com.waflo.cooltimediaplattform.ui.data;

import com.vaadin.flow.component.crud.CrudFilter;
import com.vaadin.flow.data.provider.AbstractDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.waflo.cooltimediaplattform.backend.model.Category;
import com.waflo.cooltimediaplattform.backend.security.UserSession;
import com.waflo.cooltimediaplattform.backend.service.CategoryService;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.stream.Stream;

@SpringComponent
public class CategoryDataProvider extends AbstractDataProvider<Category, CrudFilter> {
    private final CategoryService categoryService;
    private final UserSession session;

    public CategoryDataProvider(CategoryService categoryService, UserSession session) {
        this.categoryService = categoryService;
        this.session = session;
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
        int offset = query.getOffset();
        int limit = query.getLimit();

        Stream<Category> stream = categoryService.findAllByUser(session.getUser().getId()).stream();       //find only for appropriate user

        if (query.getFilter().isPresent()) {
            stream = stream
                    .filter(predicate(query.getFilter().get()))
                    .sorted(comparator(query.getFilter().get()));
        }

        return stream.skip(offset).limit(limit);


    }

    private static Predicate<Category> predicate(CrudFilter filter) {
        // For RDBMS just generate a WHERE clause
        return filter.getConstraints().entrySet().stream()
                .map(constraint -> (Predicate<Category>) category -> {
                    try {
                        Object value = valueOf(constraint.getKey(), category);
                        return value != null && value.toString().toLowerCase()
                                .contains(constraint.getValue().toLowerCase());
                    } catch (Exception e) {
                        e.printStackTrace();
                        return false;
                    }
                })
                .reduce(Predicate::and)
                .orElse(e -> true);
    }

    private static Object valueOf(String fieldName, Category category) {
        try {
            Field field = Category.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(category);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private static Comparator<Category> comparator(CrudFilter filter) {
        // For RDBMS just generate an ORDER BY clause
        return filter.getSortOrders().entrySet().stream()
                .map(sortClause -> {
                    try {
                        Comparator<Category> comparator
                                = Comparator.comparing(category ->
                                (Comparable) valueOf(sortClause.getKey(), category));

                        if (sortClause.getValue() == SortDirection.DESCENDING) {
                            comparator = comparator.reversed();
                        }

                        return comparator;
                    } catch (Exception ex) {
                        return (Comparator<Category>) (o1, o2) -> 0;
                    }
                })
                .reduce(Comparator::thenComparing)
                .orElse((o1, o2) -> 0);
    }

    public void persist(Category item) {
        categoryService.save(item);
    }

    public void delete(Category item) {
        categoryService.delete(item);
    }
}
