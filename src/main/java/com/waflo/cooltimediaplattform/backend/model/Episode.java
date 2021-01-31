package com.waflo.cooltimediaplattform.backend.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Episode extends Media {
@NotEmpty
    private String title;

    @OneToOne
            @NotNull
    File stream;

    @OneToMany
    private List<Rating> ratings;
    @OneToMany
    private List<Comment> comments;


}
