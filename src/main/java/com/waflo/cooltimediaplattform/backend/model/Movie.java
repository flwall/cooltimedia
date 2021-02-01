package com.waflo.cooltimediaplattform.backend.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Movie extends OnDemand {

    @NotEmpty
    String title;

    String summary;

    @OneToOne
    @NotNull
    File stream;

    @PastOrPresent
    LocalDate publishDate;

    @OneToOne
    @NotNull
    private File thumbnail;


    public String toString(){
        return title;
    }
}
