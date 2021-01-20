package com.waflo.cooltimediaplattform.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
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

    @Lob
    private Byte[] image;

}
