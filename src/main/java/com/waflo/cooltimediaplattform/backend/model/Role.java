package com.waflo.cooltimediaplattform.backend.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

@Entity
@Data
public class Role {
    public static final Role ROLE_USER = new Role("USER"), ROLE_ADMIN = new Role("ADMIN");

    @Id
    @GeneratedValue
    long Id;

    @NotEmpty
    private String roleName;

    public Role() {
    }

    public Role(String roleName) {
        this.roleName = roleName;
    }

}
