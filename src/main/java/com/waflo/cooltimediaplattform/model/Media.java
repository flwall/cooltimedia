package com.waflo.cooltimediaplattform.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Media {

    @Id
    @GeneratedValue
    long Id;

    //list of use m-n
    @ManyToMany
    List<User> owner;

}
