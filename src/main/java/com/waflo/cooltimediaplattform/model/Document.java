package com.waflo.cooltimediaplattform.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Document extends OnDemand {

    @NotEmpty
    private String title;

    String summary;

    @PastOrPresent
    LocalDate publishDate;

    @OneToOne
    @NotNull
    private File document;

}
