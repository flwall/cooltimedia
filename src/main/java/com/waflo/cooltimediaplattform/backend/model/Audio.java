package com.waflo.cooltimediaplattform.backend.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Audio extends OnDemand {

    @NotEmpty
    private String title;

    @NotEmpty
    private String audioUrl;

    @PastOrPresent LocalDate publishDate;

    @Override
    public String toString(){
        return title;
    }
}
