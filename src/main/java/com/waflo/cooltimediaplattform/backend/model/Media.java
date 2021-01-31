package com.waflo.cooltimediaplattform.backend.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.LinkedList;
import java.util.List;

@Entity
@Data
public class Media {

    @Id
    @GeneratedValue
    long Id;

    //list of use m-n
    @ManyToMany
    List<User> owner=new LinkedList<>();

}
