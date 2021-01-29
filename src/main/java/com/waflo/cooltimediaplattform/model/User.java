package com.waflo.cooltimediaplattform.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Entity
@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User extends Person {

    @NotEmpty
    private String username;

    @Email
    @NotEmpty
    private String email;


}
