package com.waflo.cooltimediaplattform.ui.component;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.FileBuffer;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.waflo.cooltimediaplattform.model.Category;
import com.waflo.cooltimediaplattform.model.File;
import com.waflo.cooltimediaplattform.model.Movie;
import com.waflo.cooltimediaplattform.model.Person;
import com.waflo.cooltimediaplattform.repository.FileContentStore;
import com.waflo.cooltimediaplattform.service.CategoryService;
import com.waflo.cooltimediaplattform.service.FileService;
import com.waflo.cooltimediaplattform.service.PersonService;

import java.time.LocalDate;

@SpringComponent
@UIScope
public class MovieForm extends FormLayout {
    TextField title = new TextField("Titel");
    TextArea summary = new TextArea("Zusammenfassung");
    Upload stream;
    DatePicker publishDate = new DatePicker("Veröffentlicht am: ");
    Label  streamLabel=new Label("Film-Datei: ");
    Label thumbnailLabel=new Label("Thumbnail-Bild: ");
    Upload thumbnail;

    ComboBox<Person> author = new ComboBox<>("Author");
    ComboBox<Category> category = new ComboBox<>("Kategorie");


    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    Binder<Movie> binder = new BeanValidationBinder<>(Movie.class);
    private Movie movie;


    public MovieForm(PersonService personService, CategoryService categoryService, FileContentStore store, FileService fileService) {
        addClassName("movie-form");

        //initBindings();
        binder.bindInstanceFields(this);


        var rec = new FileBuffer();
        stream = new Upload(rec);
        stream.setAcceptedFileTypes("video/*", "multipart/form-data");
        stream.setId("stream-upload");
        streamLabel.setFor(stream);

        stream.addAllFinishedListener(l -> {
            if(rec.getFileData()==null)return;
            var f = new File();
            f.setCreated(LocalDate.now());
            f.setName(rec.getFileName());       //evt. movie title?
            f.setMimeType(rec.getFileData().getMimeType());

            store.setContent(f, rec.getInputStream());
            fileService.add(f);
            movie.setStream(f);
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
            fileService.add(f);
            movie.setThumbnail(f);

        });
        thumbnailLabel.setFor(thumbnail);

        author.setItemLabelGenerator(Person::getName);
        author.setItems(personService.findAll());
        category.setItemLabelGenerator(Category::getName);
        category.setItems(categoryService.findAll());


        add(title, summary, streamLabel, stream, publishDate, thumbnailLabel, thumbnail, author, category, createButtonsLayout());

    }



    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
        binder.readBean(movie);
    }

    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(click -> validateAndSave());
        delete.addClickListener(click -> fireEvent(new DeleteEvent(this, movie)));
        close.addClickListener(click -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(evt -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {

        try {
            binder.writeBean(movie);
            fireEvent(new SaveEvent(this, movie));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    // Events
    public static abstract class ContactFormEvent extends ComponentEvent<MovieForm> {
        private Movie movie;

        protected ContactFormEvent(MovieForm source, Movie movie) {
            super(source, false);
            this.movie = movie;
        }

        public Movie getMovie() {
            return movie;
        }
    }

    public static class SaveEvent extends ContactFormEvent {
        SaveEvent(MovieForm source, Movie contact) {
            super(source, contact);
        }
    }

    public static class DeleteEvent extends ContactFormEvent {
        DeleteEvent(MovieForm source, Movie contact) {
            super(source, contact);
        }

    }

    public static class CloseEvent extends ContactFormEvent {
        CloseEvent(MovieForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

}
