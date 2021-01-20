package com.waflo.cooltimediaplattform.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Data
public class Episode extends Media {

    private String title;
    private String filePath;

    @OneToMany
    private List<Rating> ratings;
    @OneToMany
    private List<Comment> comments;


}
