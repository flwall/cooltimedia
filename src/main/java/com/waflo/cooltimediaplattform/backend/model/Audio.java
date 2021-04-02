package com.waflo.cooltimediaplattform.backend.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Audio extends OnDemand {


    @NotEmpty
    private String audioUrl;


    @Override
    public String toString() {
        return getTitle();
    }
}
