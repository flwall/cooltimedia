package com.waflo.cooltimediaplattform.backend.model;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Data
public class Media {

    @Id
    @GeneratedValue
    long Id;

    //list of use m-n
    @ManyToMany(fetch = FetchType.EAGER)
    Set<User> owner = new LinkedHashSet<>();

}
