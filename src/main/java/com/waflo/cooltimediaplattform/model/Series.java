package com.waflo.cooltimediaplattform.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Series extends Media {

    private String name;

    @OneToMany
    List<Season> seasons;


    LocalDate creationDate;
    @Lob
    private Byte[] thumbnail;


}
