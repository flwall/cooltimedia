package com.waflo.cooltimediaplattform.backend.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true, exclude = {"ratings", "actors"})
@Entity
@Data
public class OnDemand extends Media {

    @NotEmpty
    private String title;
    @PastOrPresent
    private LocalDate publishDate;

    @ManyToOne
    private Person author;

    @ManyToMany
    private Set<Person> actors;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "ratedMedia", cascade = CascadeType.REMOVE)
    private Set<Rating> ratings;

    @ManyToOne
    private Category category;

}
