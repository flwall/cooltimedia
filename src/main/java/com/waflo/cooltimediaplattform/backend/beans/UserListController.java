package com.waflo.cooltimediaplattform.backend.beans;

import com.waflo.cooltimediaplattform.backend.jparepository.IRepository;
import com.waflo.cooltimediaplattform.backend.jparepository.JpaRepository;
import com.waflo.cooltimediaplattform.backend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.view.ViewScoped;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Component(value = "userController")
@ManagedBean
@ViewScoped
public class UserListController {
    @Autowired
    EntityManager em;

    private IRepository<User, Long> repo;

    private List<User> users;

    public UserListController(EntityManager em) {
        repo = new JpaRepository<>(em, User.class);
    }

    @PostConstruct()
    public void load() {
        users = repo.findAll();
    }

    @Transactional
    public void delete(User u) {
        repo.delete(u);
        users = repo.findAll();
    }

    public List<User> getUsers() {
        users=repo.findAll();
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

}
