package com.waflo.cooltimediaplattform.backend.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true, exclude = {"ratings", "actors"})
@Entity
@Data
public class OnDemand extends Media {

    @ManyToOne
    private Person author;

    @ManyToMany
    private Set<Person> actors;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "ratedMedia")
    private Set<Rating> ratings;

    @ManyToOne
    private Category category;

}
