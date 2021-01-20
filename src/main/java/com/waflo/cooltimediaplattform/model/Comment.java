package com.waflo.cooltimediaplattform.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class Comment {
    @Id
    @GeneratedValue
    long Id;


    private String title, comment;

    @ManyToOne
    @JoinColumn(name = "fk_creator")
    private User creator;

    @ManyToOne
    @JoinColumn(name = "fk_media")
    private Media referencedMedia;

    private LocalDate createdAt;
}
