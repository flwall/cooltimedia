package com.waflo.cooltimediaplattform.backend.beans;

import com.waflo.cooltimediaplattform.backend.jparepository.IRepository;
import com.waflo.cooltimediaplattform.backend.jparepository.JpaRepository;
import com.waflo.cooltimediaplattform.backend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import java.util.List;

@ViewScoped
@ManagedBean(name = "userController")
public class UserListController {
    private final IRepository<User, Long> repo=new JpaRepository<>();

    private List<User> users;

    public UserListController(){
        users=repo.findAll();
    }

    public void delete(User u){
        repo.delete(u);
    }
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

}
