package com.waflo.cooltimediaplattform.backend.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;


@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Movie extends OnDemand {

    @NotEmpty
    String title;

    String summary;
    @NotEmpty
    private String streamUrl;

    @PastOrPresent
    LocalDate publishDate;

    private String thumbnailUrl;

    public String toString() {
        return title;
    }
}
