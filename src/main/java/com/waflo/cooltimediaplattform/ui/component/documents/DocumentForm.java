package com.waflo.cooltimediaplattform.ui.component.documents;

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
import com.waflo.cooltimediaplattform.backend.model.Document;
import com.waflo.cooltimediaplattform.backend.model.File;
import com.waflo.cooltimediaplattform.backend.model.Person;
import com.waflo.cooltimediaplattform.backend.repository.FileContentStore;
import com.waflo.cooltimediaplattform.backend.security.UserSession;
import com.waflo.cooltimediaplattform.backend.service.CategoryService;
import com.waflo.cooltimediaplattform.backend.service.DocumentService;
import com.waflo.cooltimediaplattform.backend.service.FileService;
import com.waflo.cooltimediaplattform.backend.service.PersonService;
import com.waflo.cooltimediaplattform.ui.component.AbstractForm;
import com.waflo.cooltimediaplattform.ui.events.CancelEvent;
import com.waflo.cooltimediaplattform.ui.events.SaveEvent;
import com.waflo.cooltimediaplattform.ui.events.ValidationFailedEvent;

import java.time.LocalDate;

@SpringComponent
@UIScope
public class DocumentForm extends AbstractForm<Document> {
    private final ComboBox<Person> author = new ComboBox<>("Author");
    TextField title = new TextField("Titel");
    TextArea summary = new TextArea("Zusammenfassung");
    Upload document;

    DatePicker publishDate = new DatePicker("Veröffentlicht am: ");
    Label documentLabel = new Label("Dokument: ");


    ComboBox<Category> category = new ComboBox<>("Kategorie");
    private final DocumentService documentService;
    private final UserSession userSession;


    public DocumentForm(PersonService personService, CategoryService categoryService, FileContentStore store, FileService fileService, DocumentService documentService, UserSession userSession) {
        this.documentService = documentService;
        this.userSession = userSession;
        addClassName("document-form");

        binder = new BeanValidationBinder<>(Document.class);

        binder.bindInstanceFields(this);


        var rec = new FileBuffer();
        document = new Upload(rec);
        document.setAcceptedFileTypes("application/pdf", "application/msword", "text/plain", "application/*");

        document.setId("document-upload");
        documentLabel.setFor(document);

        document.addAllFinishedListener(l -> {
            if (rec.getFileData() == null) return;
            var f = new File();
            f.setCreated(LocalDate.now());
            f.setName(rec.getFileName());       //evt. movie title?
            f.setMimeType(rec.getFileData().getMimeType());

            store.setContent(f, rec.getInputStream());
            fileService.save(f);
            entity.setDocument(f);
        });


        author.setItemLabelGenerator(Person::getName);
        author.setItems(personService.findAllByUser(userSession.getUser().getId()));
        category.setItemLabelGenerator(Category::getName);
        category.setItems(categoryService.findAllByUser(userSession.getUser().getId()));


        add(title, summary, documentLabel, document, publishDate, author, category, createButtonsLayout());

    }

    @Override
    protected void onSaveClick(ClickEvent<Button> buttonClickEvent) {
        try {
            entity.getOwner().add(userSession.getUser());
            binder.writeBean(entity);
            documentService.save(entity);
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
