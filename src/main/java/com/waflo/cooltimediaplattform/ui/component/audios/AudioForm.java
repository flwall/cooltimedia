package com.waflo.cooltimediaplattform.ui.component.audios;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.FileBuffer;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.waflo.cooltimediaplattform.Constants;
import com.waflo.cooltimediaplattform.backend.Utils;
import com.waflo.cooltimediaplattform.backend.model.Audio;
import com.waflo.cooltimediaplattform.backend.model.Category;
import com.waflo.cooltimediaplattform.backend.model.Person;
import com.waflo.cooltimediaplattform.backend.security.UserSession;
import com.waflo.cooltimediaplattform.backend.service.*;
import com.waflo.cooltimediaplattform.ui.component.AbstractForm;
import com.waflo.cooltimediaplattform.ui.events.CancelEvent;
import com.waflo.cooltimediaplattform.ui.events.SaveEvent;
import com.waflo.cooltimediaplattform.ui.events.ValidationFailedEvent;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

@SpringComponent
@UIScope
public class AudioForm extends AbstractForm<Audio> {
    private final ComboBox<Person> author = new ComboBox<>("Author");
    TextField title = new TextField("Titel");
    Upload audio;

    DatePicker publishDate = new DatePicker("Veröffentlicht am: ");
    Label audioLabel = new Label("Audio: ");


    ComboBox<Category> category = new ComboBox<>("Kategorie");
    private final AudioService audioService;
    private final UserSession userSession;
private final CloudinaryUploadService uploadService;

    public AudioForm(PersonService personService, CategoryService categoryService, AudioService audioService, UserSession userSession, CloudinaryUploadService uploadService) {
        this.audioService = audioService;
        this.userSession = userSession;
        this.uploadService = uploadService;
        addClassName("audio-form");

        binder = new BeanValidationBinder<>(Audio.class);

        binder.bindInstanceFields(this);


        var rec = new FileBuffer();
        audio = new Upload(rec);
        audio.setAcceptedFileTypes("audio/*");

        audio.setId("audio-upload");
        audioLabel.setFor(audio);

        audio.addAllFinishedListener(l -> {
            if (rec.getFileData() == null) return;
            var f = new File(Constants.tmpDir +rec.getFileName());
            try {
                FileUtils.copyInputStreamToFile(rec.getInputStream(), f);
            } catch (IOException e) {
                e.printStackTrace();
            }
            entity.setAudioUrl(rec.getFileName());
        });


        author.setItemLabelGenerator(Person::getName);
        author.setItems(personService.findAllByUser(userSession.getUser().getId()));
        category.setItemLabelGenerator(Category::getName);
        category.setItems(categoryService.findAllByUser(userSession.getUser().getId()));


        add(title, audioLabel, audio, publishDate, author, category, createButtonsLayout());

    }


    @Override
    protected void onSaveClick(ClickEvent<Button> buttonClickEvent) {
        try {
            entity.getOwner().add(userSession.getUser());
            binder.writeBean(entity);
            if(entity.getAudioUrl()!=null){
                var f=new File(entity.getAudioUrl());
                entity.setAudioUrl(uploadService.uploadStream(f, "audios/" + userSession.getUser().getId() + "/" + Utils.toValidFileName(entity.getTitle())));
                FileUtils.deleteQuietly(f);
            }

            audioService.save(entity);
            fireEvent(new SaveEvent(this, false));
        } catch (ValidationException e) {
            fireEvent(new ValidationFailedEvent(this));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCancelClick(ClickEvent<Button> buttonClickEvent) {
        fireEvent(new CancelEvent(this, false));
    }
}
