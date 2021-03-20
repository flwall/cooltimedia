package com.waflo.cooltimediaplattform.backend.beans;

import com.waflo.cooltimediaplattform.backend.jparepository.IRepository;
import com.waflo.cooltimediaplattform.backend.jparepository.JpaRepository;
import com.waflo.cooltimediaplattform.backend.model.Category;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component("categoriesController")
@ManagedBean
@ViewScoped
public class CategoriesController {

    private final IRepository<Category, Long> repo;
    private List<Category> categories;

    public CategoriesController(EntityManager em) {
        this.repo = new JpaRepository<>(em, Category.class);
    }
    @PostConstruct
    public void load(){
        this.categories=repo.findAll().stream().filter(c->c.getOwner().isEmpty()).collect(Collectors.toList());
    }
    @Transactional
    public void delete(Category c){
        repo.delete(c);
        this.load();
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
