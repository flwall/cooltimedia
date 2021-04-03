package com.waflo.cooltimediaplattform.backend.model;

import lombok.Data;
import org.apache.commons.compress.utils.Sets;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue
    private long Id;

    private String oauth_id;

    @NotEmpty
    private String username;
    LocalDateTime createdAt;
    @Email
    @NotEmpty
    private String email;

    private String profile_pic_url;

    public User() {
    }

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    public Set<Role> roles = Sets.newHashSet(Role.ROLE_USER);

    public User(String oauth_id, String username, String email) {
        this.oauth_id = oauth_id;
        this.username = username;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Username: " + username + "\nEmail: " + email;
    }

    public String getRole() {
        return roles.contains(Role.ROLE_ADMIN) ? "admin" : "user";
    }

    public void setRole(String s) {
        if (s.equalsIgnoreCase("admin"))
            roles.add(Role.ROLE_ADMIN);
        else
            roles.remove(Role.ROLE_ADMIN);
    }
}
