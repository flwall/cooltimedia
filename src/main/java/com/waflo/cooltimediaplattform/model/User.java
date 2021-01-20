package com.waflo.cooltimediaplattform.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;

@Entity
@EqualsAndHashCode(callSuper = false)
@Data
public class User extends Person {

    private String username;

    private String email;

    private String password;        //hashed format


}
