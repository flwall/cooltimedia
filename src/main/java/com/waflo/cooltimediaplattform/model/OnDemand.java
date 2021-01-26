package com.waflo.cooltimediaplattform.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class OnDemand extends Media {

    @ManyToOne
    @JoinColumn(name = "fk_author")
    private Person author;

    @ManyToMany
    private Set<Person> actors;

    @OneToMany
    private List<Rating> ratings;

    @ManyToOne
    @JoinColumn(name = "fk_category")
    private Category category;


}
