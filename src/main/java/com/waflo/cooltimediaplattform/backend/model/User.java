package com.waflo.cooltimediaplattform.backend.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "cooltimedia_users")
@Data
public class User {

    @Id
    @GeneratedValue
    private long Id;

    private String oauth_id;

    @NotEmpty
    private String username;

    @Email
    @NotEmpty
    private String email;


    private String profile_pic_url;

    public User() {
    }

    public User(String oauth_id, String username, String email) {
        this.oauth_id = oauth_id;
        this.username = username;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Username: " + username + "\nEmail: " + email;
    }
}
