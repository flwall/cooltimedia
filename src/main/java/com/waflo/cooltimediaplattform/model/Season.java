package com.waflo.cooltimediaplattform.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Season extends Media {
@NotEmpty
    String name;
    @OneToMany
    List<Episode> episodes;

    @PastOrPresent
    LocalDate releaseDate;

}
