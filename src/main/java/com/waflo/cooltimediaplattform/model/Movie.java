package com.waflo.cooltimediaplattform.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import java.time.LocalDate;


@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Movie extends OnDemand {

    String title;
    String summary;

    @OneToOne
    File stream;


    LocalDate publishDate;

    @OneToOne
    private File thumbnail;


    }
