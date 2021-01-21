package com.waflo.cooltimediaplattform.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Entity
@EqualsAndHashCode(callSuper = false)
@Data
public class User extends Person {

    private String username;

    @Email
    @NotEmpty
    private String email;

    @NotEmpty
    private String password;        //hashed format


}
