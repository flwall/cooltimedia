package com.waflo.cooltimediaplattform.ui.component.series;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.FileBuffer;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.waflo.cooltimediaplattform.model.*;
import com.waflo.cooltimediaplattform.repository.FileContentStore;
import com.waflo.cooltimediaplattform.security.UserSession;
import com.waflo.cooltimediaplattform.service.*;
import com.waflo.cooltimediaplattform.ui.component.AbstractForm;
import com.waflo.cooltimediaplattform.ui.events.CancelEvent;
import com.waflo.cooltimediaplattform.ui.events.SaveEvent;
import com.waflo.cooltimediaplattform.ui.events.ValidationFailedEvent;

import java.time.LocalDate;

@SpringComponent
@UIScope
public class SeriesForm extends AbstractForm<Series> {
    TextField title = new TextField("Titel");
    TextArea summary = new TextArea("Zusammenfassung");

    DatePicker publishDate = new DatePicker("Veröffentlicht am: ");
    Label thumbnailLabel = new Label("Thumbnail-Bild: ");
    Upload thumbnail;

    private final SeriesService seriesService;
    private final UserSession userSession;


    public SeriesForm(PersonService personService, CategoryService categoryService, FileContentStore store, FileService fileService, SeriesService seriesService, UserSession userSession) {
        this.seriesService = seriesService;
        this.userSession = userSession;
        addClassName("series-form");

        binder = new BeanValidationBinder<>(Series.class);

        binder.bindInstanceFields(this);



        //add listener
        var r = new FileBuffer();

        thumbnail = new Upload(r);
        thumbnail.setId("upload-thumbnail");
        thumbnail.setAcceptedFileTypes("image/*");
        thumbnail.addAllFinishedListener(l -> {

            var f = new File();
            f.setMimeType(r.getFileData().getMimeType());
            f.setName(r.getFileName());
            f.setCreated(LocalDate.now());
            store.setContent(f, r.getInputStream());
            fileService.save(f);
            entity.setThumbnail(f);

        });
        thumbnailLabel.setFor(thumbnail);



        add(title, summary, publishDate, thumbnailLabel, thumbnail, createButtonsLayout());

    }


    @Override
    protected void onSaveClick(ClickEvent<Button> buttonClickEvent) {
        try {
            entity.getOwner().add(userSession.getUser());
            binder.writeBean(entity);
            seriesService.save(entity);
            fireEvent(new SaveEvent(this, false));
        } catch (ValidationException e) {
            fireEvent(new ValidationFailedEvent(this));
        }
    }

    @Override
    protected void onCancelClick(ClickEvent<Button> buttonClickEvent) {
        fireEvent(new CancelEvent(this, false));
    }
}
