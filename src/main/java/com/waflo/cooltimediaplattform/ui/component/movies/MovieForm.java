package com.waflo.cooltimediaplattform.ui.component.movies;

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
import com.waflo.cooltimediaplattform.backend.model.Category;
import com.waflo.cooltimediaplattform.backend.model.File;
import com.waflo.cooltimediaplattform.backend.model.Movie;
import com.waflo.cooltimediaplattform.backend.model.Person;
import com.waflo.cooltimediaplattform.backend.repository.FileContentStore;
import com.waflo.cooltimediaplattform.backend.security.UserSession;
import com.waflo.cooltimediaplattform.backend.service.CategoryService;
import com.waflo.cooltimediaplattform.backend.service.FileService;
import com.waflo.cooltimediaplattform.backend.service.MovieService;
import com.waflo.cooltimediaplattform.backend.service.PersonService;
import com.waflo.cooltimediaplattform.ui.component.AbstractForm;
import com.waflo.cooltimediaplattform.ui.events.CancelEvent;
import com.waflo.cooltimediaplattform.ui.events.SaveEvent;
import com.waflo.cooltimediaplattform.ui.events.ValidationFailedEvent;

import java.time.LocalDate;

@SpringComponent
@UIScope
public class MovieForm extends AbstractForm<Movie> {
    TextField title = new TextField("Titel");
    TextArea summary = new TextArea("Zusammenfassung");
    Upload stream;
    DatePicker publishDate = new DatePicker("Veröffentlicht am: ");
    Label streamLabel = new Label("Film-Datei: ");
    Label thumbnailLabel = new Label("Thumbnail-Bild: ");
    Upload thumbnail;

    ComboBox<Person> author = new ComboBox<>("Author");
    ComboBox<Category> category = new ComboBox<>("Kategorie");
    private final MovieService movieService;
    private final UserSession userSession;


    public MovieForm(PersonService personService, CategoryService categoryService, FileContentStore store, FileService fileService, MovieService movieService, UserSession userSession) {
        this.movieService = movieService;
        this.userSession = userSession;
        addClassName("movie-form");

        binder = new BeanValidationBinder<>(Movie.class);

        binder.bindInstanceFields(this);


        var rec = new FileBuffer();
        stream = new Upload(rec);
        stream.setAcceptedFileTypes("video/*", "multipart/form-data");
        stream.setId("stream-upload");
        streamLabel.setFor(stream);

        stream.addAllFinishedListener(l -> {
            if (rec.getFileData() == null) return;
            var f = new File();
            f.setCreated(LocalDate.now());
            f.setName(rec.getFileName());       //evt. movie title?
            f.setMimeType(rec.getFileData().getMimeType());

            store.setContent(f, rec.getInputStream());
            fileService.save(f);
            entity.setStream(f);
        });
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

        author.setItemLabelGenerator(Person::getName);
        author.setItems(personService.findAll());
        category.setItemLabelGenerator(Category::getName);
        category.setItems(categoryService.findAll());


        add(title, summary);
        addFormItem(stream, streamLabel);
        add(publishDate);
        addFormItem(thumbnail, thumbnailLabel);
        add(author, category, createButtonsLayout());


    }


    @Override
    protected void onSaveClick(ClickEvent<Button> buttonClickEvent) {
        try {
            entity.getOwner().add(userSession.getUser());
            binder.writeBean(entity);
            movieService.add(entity);
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
