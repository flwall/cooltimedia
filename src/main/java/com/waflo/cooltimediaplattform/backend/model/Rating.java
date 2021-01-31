package com.waflo.cooltimediaplattform.backend.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@Data
public class Rating {

    @Id
    @GeneratedValue
    long Id;

    @Min(0)
    @Max(5)
    private int rating;         ///0-5
    @ManyToOne
    @JoinColumn(name = "fk_creator")
    private User creator;

    @ManyToOne
    @JoinColumn(name = "fk_media")
    private Media ratedMedia;


}
