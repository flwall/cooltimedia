package com.waflo.cooltimediaplattform.backend.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Series extends Media {
@NotEmpty
    private String name;

private String summary;
    @OneToMany
    List<Season> seasons;

@PastOrPresent
    LocalDate creationDate;
    @OneToOne
    @NotNull
    private File thumbnail;


}
