package com.waflo.cooltimediaplattform.model;

import lombok.Data;
import org.springframework.content.commons.annotations.ContentId;
import org.springframework.content.commons.annotations.ContentLength;
import org.springframework.content.commons.annotations.MimeType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Data
public class File {

    @Id
    @GeneratedValue
    long id;

    private String name;
    private LocalDate created;

    @ContentId
    private String contentId;
    @ContentLength
    private long contentLength;

    @MimeType
    private String mimeType = "text/plain";

}
