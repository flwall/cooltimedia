package com.waflo.cooltimediaplattform.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Entity
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

    @OneToOne
    private File profile_pic;

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
