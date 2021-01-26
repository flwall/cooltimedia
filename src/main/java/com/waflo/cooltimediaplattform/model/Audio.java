package com.waflo.cooltimediaplattform.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Audio extends Media {

    @NotEmpty
    private String title;

    @ManyToOne
    private Person creator;

    @OneToOne
    private File audio;

    @PastOrPresent LocalDate creation;

}
