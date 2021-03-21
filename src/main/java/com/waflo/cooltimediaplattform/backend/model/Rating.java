package com.waflo.cooltimediaplattform.backend.model;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;

@Entity
@Data
public class Rating {

    @Id
    @GeneratedValue
    long Id;

    @Min(0)
    @Max(5)
    private int rating;         ///0-5
    private String comment;
    @ManyToOne
    @JoinColumn(name = "fk_creator")
    private User creator;

    @ManyToOne
    private OnDemand ratedMedia;

    private LocalDateTime createdAt;

}
