package com.waflo.cooltimediaplattform.model;

import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.content.commons.annotations.ContentId;
import org.springframework.content.commons.annotations.ContentLength;
import org.springframework.content.commons.annotations.MimeType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Entity
@Data
@Accessors(chain = true)
public class File {

    @Id
    @GeneratedValue
    long id;

    @NotEmpty
    private String name;

    @PastOrPresent
    private LocalDate created;

    @ContentId
    private String contentId;
    @ContentLength
    private long contentLength;

    @MimeType
    private String mimeType = "text/plain";

}
