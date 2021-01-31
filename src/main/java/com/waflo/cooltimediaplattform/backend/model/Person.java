package com.waflo.cooltimediaplattform.backend.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Data
@Entity
public class Person {
    @Id
    @GeneratedValue
    long Id;

    @NotEmpty
    private String name;

    @PastOrPresent
    private LocalDate birthDate;

    @OneToOne
    private File image;

}
