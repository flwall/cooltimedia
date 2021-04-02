package com.waflo.cooltimediaplattform.backend.beans;

import com.waflo.cooltimediaplattform.backend.model.User;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MediaDTO {

    private Long id;
    private MediaType type;
    private LocalDate createdAt;
    private int[] ratings;      //immer 1-5
    private String title;
    private User owner;


}
