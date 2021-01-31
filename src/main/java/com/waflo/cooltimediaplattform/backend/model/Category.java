package com.waflo.cooltimediaplattform.backend.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Data
public class Category {
    @Id
    @GeneratedValue
    long Id;

    @NotEmpty
    private String name;

    @OneToOne
    private File image;

    @ManyToOne
    Category parentCategory;

    @OneToMany
    private List<OnDemand> onDemands;
}
