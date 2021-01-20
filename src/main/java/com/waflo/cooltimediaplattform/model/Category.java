package com.waflo.cooltimediaplattform.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Category {
    @Id
    @GeneratedValue
    long Id;

    private String name;

    @Lob
    private Byte[] image;

    //optional parentCategory

    @OneToMany
    private List<OnDemand> onDemands;
}
