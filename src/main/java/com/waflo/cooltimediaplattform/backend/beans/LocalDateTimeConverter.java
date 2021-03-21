package com.waflo.cooltimediaplattform.backend.beans;

import org.springframework.stereotype.Component;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.DateTimeConverter;
import javax.faces.convert.FacesConverter;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@FacesConverter(forClass = LocalDateTime.class)
@Component
public class LocalDateTimeConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return LocalDateTime.parse(value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {

        LocalDateTime dateValue = (LocalDateTime) value;

        return dateValue.format(DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm"));
    }

}
