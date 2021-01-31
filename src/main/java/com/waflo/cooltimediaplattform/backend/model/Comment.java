package com.waflo.cooltimediaplattform.backend.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Entity
@Data
public class Comment {
    @Id
    @GeneratedValue
    long Id;

@NotEmpty
    private String title;


    String comment;

    @ManyToOne
    @JoinColumn(name = "fk_creator")
    private User creator;

    @ManyToOne
    @JoinColumn(name = "fk_media")
    private Media referencedMedia;


    private LocalDate createdAt;
}
