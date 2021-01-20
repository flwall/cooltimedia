package com.waflo.cooltimediaplattform.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Movie extends OnDemand {

    String title;
    String filePath;        //relative Path to movie file sth like ./movies/<title> (or ./<id>)


    LocalDate publishDate;


}
