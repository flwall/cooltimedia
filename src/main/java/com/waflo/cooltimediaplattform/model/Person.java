package com.waflo.cooltimediaplattform.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;

@Data
@Entity
public class Person {
    @Id
    @GeneratedValue
    long Id;

    private String firstName, lastName;
    private LocalDate birthDate;

    @OneToOne
    private File image;

}
