package com.waflo.cooltimediaplattform.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Episode extends Media {

    private String title;

    @OneToOne
    File stream;

    @OneToMany
    private List<Rating> ratings;
    @OneToMany
    private List<Comment> comments;


}
