package com.waflo.cooltimediaplattform.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Rating {

    @Id
    @GeneratedValue
    long Id;

    private int rating;         ///0-5
    @ManyToOne
    @JoinColumn(name = "fk_creator")
    private User creator;

    @ManyToOne
    @JoinColumn(name = "fk_media")
    private Media ratedMedia;


}
