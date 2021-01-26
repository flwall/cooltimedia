package com.waflo.cooltimediaplattform.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Document extends Media {

    @NotEmpty
    private String title;

    @OneToOne
    @NotNull
    private File document;

}
