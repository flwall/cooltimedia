package com.waflo.cooltimediaplattform.backend.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Category extends Media {

    @NotEmpty
    private String name;


    @ManyToOne
    Category parentCategory;

    @OneToMany(fetch = FetchType.EAGER)
    private List<OnDemand> onDemands;

    @Override
    public String toString() {
        return getName();
    }
}
