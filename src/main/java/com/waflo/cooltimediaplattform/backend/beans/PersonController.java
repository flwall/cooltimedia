package com.waflo.cooltimediaplattform.backend.beans;

import com.waflo.cooltimediaplattform.backend.jparepository.IRepository;
import com.waflo.cooltimediaplattform.backend.jparepository.JpaRepository;
import com.waflo.cooltimediaplattform.backend.model.Person;
import org.primefaces.PrimeFaces;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@ManagedBean
@Component("personController")
@ViewScoped
public class PersonController {
    private IRepository<Person, Long> repo;
    private Person selectedPerson;

    public PersonController(EntityManager em){
        repo=new JpaRepository<>(em, Person.class);
    }
    private List<Person> persons;

    //@PostConstruct
    public void load(){
        this.persons=repo.findAll().stream().filter(p->p.getOwner().isEmpty()).collect(Collectors.toList());
    }
    @Transactional
    public void delete(Person p){
        repo.delete(p);
        this.load();
    }
    public void openNew() {
        this.selectedPerson = new Person();
    }
    @Transactional
    public void savePerson() {
        if (this.selectedPerson.getId() == 0) {
            selectedPerson=repo.save(selectedPerson);
            this.persons.add(selectedPerson);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Person hinzugefügt"));
        }
        else {
            repo.update(selectedPerson);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Person aktualisiert"));
        }

        PrimeFaces.current().executeScript("PF('managePersonDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-persons");
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public Person getSelectedPerson() {
        return selectedPerson;
    }

    public void setSelectedPerson(Person selectedPerson) {
        this.selectedPerson = selectedPerson;
    }
}
