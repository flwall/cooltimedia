package com.waflo.cooltimediaplattform.backend.beans;

import com.waflo.cooltimediaplattform.backend.jparepository.IRepository;
import com.waflo.cooltimediaplattform.backend.jparepository.JpaRepository;
import com.waflo.cooltimediaplattform.backend.model.Person;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
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

    public PersonController(EntityManager em){
        repo=new JpaRepository<>(em, Person.class);
    }
    private List<Person> persons;

    @PostConstruct
    public void load(){
        this.persons=repo.findAll().stream().filter(p->p.getOwner().isEmpty()).collect(Collectors.toList());
    }
    @Transactional
    public void delete(Person p){
        repo.delete(p);
        this.load();
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }
}
