package com.waflo.cooltimediaplattform.backend.beans;

import com.waflo.cooltimediaplattform.backend.jparepository.IRepository;
import com.waflo.cooltimediaplattform.backend.jparepository.JpaRepository;
import com.waflo.cooltimediaplattform.backend.model.Category;
import org.apache.commons.compress.utils.Sets;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.LinkedList;
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
    @Transactional
    public void onRowEdit(RowEditEvent<Category> event) {
        repo.update(event.getObject());
        FacesMessage msg = new FacesMessage("Product Edited", String.valueOf(event.getObject().getId()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent<Category> event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", String.valueOf(event.getObject().getId()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        if (newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    public List<Category> parentCategories(Category cat){
        var l= new LinkedList<>(categories);
        l.remove(cat);
        return l;
    }
    @Transactional
    public void onAddNew() {
        // Add one new product to the table:
        Category cat=new Category();
        cat.setName("name");
        cat=repo.save(cat);

        this.categories.add(cat);
        FacesMessage msg = new FacesMessage("New Product added", String.valueOf(cat.getId()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
