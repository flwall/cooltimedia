package com.waflo.cooltimediaplattform.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@Entity
@Data
public class Media {

    @Id
    @GeneratedValue
    long Id;


}
