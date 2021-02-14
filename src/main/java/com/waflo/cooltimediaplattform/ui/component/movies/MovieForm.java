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
import com.waflo.cooltimediaplattform.Constants;
import com.waflo.cooltimediaplattform.backend.Utils;
import com.waflo.cooltimediaplattform.backend.model.Category;
import com.waflo.cooltimediaplattform.backend.model.Movie;
import com.waflo.cooltimediaplattform.backend.model.Person;
import com.waflo.cooltimediaplattform.backend.security.UserSession;
import com.waflo.cooltimediaplattform.backend.service.CategoryService;
import com.waflo.cooltimediaplattform.backend.service.CloudinaryUploadService;
import com.waflo.cooltimediaplattform.backend.service.MovieService;
import com.waflo.cooltimediaplattform.backend.service.PersonService;
import com.waflo.cooltimediaplattform.ui.component.AbstractForm;
import com.waflo.cooltimediaplattform.ui.events.CancelEvent;
import com.waflo.cooltimediaplattform.ui.events.SaveEvent;
import com.waflo.cooltimediaplattform.ui.events.ValidationFailedEvent;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

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
    private final CloudinaryUploadService uploadService;

    public MovieForm(PersonService personService, CategoryService categoryService, MovieService movieService, UserSession userSession, CloudinaryUploadService uploadService) {
        this.movieService = movieService;
        this.userSession = userSession;
        this.uploadService = uploadService;
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

            var f = new File(Constants.tmpDir+rec.getFileName());
            try {
                FileUtils.copyInputStreamToFile(rec.getInputStream(), f);
            } catch (IOException e) {
                e.printStackTrace();
            }


            entity.setStreamUrl(rec.getFileName());
        });
        //add listener
        var r = new FileBuffer();

        thumbnail = new Upload(r);
        thumbnail.setId("upload-thumbnail");
        thumbnail.setAcceptedFileTypes("image/*");
        thumbnail.addAllFinishedListener(l -> {

            var f = new File(Constants.tmpDir+r.getFileName());
            try {
                FileUtils.copyInputStreamToFile(r.getInputStream(), f);
            } catch (IOException e) {
                e.printStackTrace();
            }
            entity.setThumbnailUrl(r.getFileName());

        });
        thumbnailLabel.setFor(thumbnail);

        author.setItemLabelGenerator(Person::getName);
        author.setItems(personService.findAllByUser(userSession.getUser().getId()));
        category.setItemLabelGenerator(Category::getName);
        category.setItems(categoryService.findAllByUser(userSession.getUser().getId()));


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
            if (entity.getStreamUrl() != null) {
             var f=new File(entity.getStreamUrl());
                entity.setStreamUrl(uploadService.uploadStream(f, "movies/" + userSession.getUser().getId() + "/" + Utils.toValidFileName(entity.getTitle())));
            FileUtils.deleteQuietly(f);
            }
            if (entity.getThumbnailUrl() != null) {
                var f=new File(entity.getThumbnailUrl());
                entity.setThumbnailUrl(uploadService.uploadStream(f, "thumbnails/" + userSession.getUser().getId() + "/" + Utils.toValidFileName(entity.getTitle())));
                FileUtils.deleteQuietly(f);
            }



            movieService.save(entity);
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
