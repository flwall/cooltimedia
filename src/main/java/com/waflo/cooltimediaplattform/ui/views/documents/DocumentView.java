package com.waflo.cooltimediaplattform.ui.views.documents;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.FileBuffer;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.NotFoundException;
import com.vaadin.flow.router.Route;
import com.waflo.cooltimediaplattform.backend.Utils;
import com.waflo.cooltimediaplattform.backend.model.Document;
import com.waflo.cooltimediaplattform.backend.service.CloudinaryUploadService;
import com.waflo.cooltimediaplattform.backend.service.DocumentService;
import com.waflo.cooltimediaplattform.ui.MainLayout;
import org.hibernate.mapping.Array;
import org.springframework.security.access.annotation.Secured;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Route(value = "document", layout = MainLayout.class)
@Secured("ROLE_USER")
public class DocumentView extends VerticalLayout implements HasUrlParameter<Long> {

    private Document doc;
    private final DocumentService documentService;
    private final CloudinaryUploadService uploadService;

    public DocumentView(DocumentService documentService, CloudinaryUploadService uploadService) {

        this.documentService = documentService;
        this.uploadService = uploadService;
    }


    @Override
    public void setParameter(BeforeEvent event, Long parameter) {
        doc = documentService.findById(parameter).orElseThrow(NotFoundException::new);

        initView();
    }

    private void initView() {

        var title = new H1(doc.getTitle());
        var backbtn = new Button("←", l -> UI.getCurrent().getPage().getHistory().back());
        var head = new HorizontalLayout(backbtn, title);


        var sum = new Text(doc.getSummary());
        var pDate = new Paragraph(doc.getPublishDate().format(formatter));
        var label = new Label("Veröffentlichungsdatum");
        pDate.setId("pDate");
        label.setFor(pDate);

        var left = new VerticalLayout(head, sum, label, pDate);

        if (doc.getAuthor() != null) {
            var author = new Paragraph(doc.getAuthor().getName());
            var authorLabel = new Label("Bearbeiter");
            author.setId("author");
            authorLabel.setFor(author);

            left.add(authorLabel, author);
        }


        var h = new H2("Kommentare");

        var delBtn = new Button("Löschen", l -> {
            try {
                uploadService.destroy("documents/" + new ArrayList<>(doc.getOwner()).get(0).getId() + "/" + Utils.toValidFileName(doc.getTitle()));
            } catch (IOException e) {
                e.printStackTrace();
            }


            backbtn.click();

        });
        var right = new VerticalLayout(h, delBtn);

        var content = new HorizontalLayout(left, right);

        var upload = new Button("Neue Version hochladen", u -> {

            var rec = new FileBuffer();

            var upl = new Upload(rec);
            upl.setAcceptedFileTypes("text/*", "application/pdf", "application/*");
            upl.addSucceededListener(l -> {
                try {
                    uploadService.uploadStream(rec.getInputStream(), "documents/"+new ArrayList<>(doc.getOwner()).get(0).getId()+"/"+Utils.toValidFileName(doc.getTitle()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            var dialog = new Dialog(upl);
            var ok = new Button("OK", b -> dialog.close());
            dialog.add(ok);
            dialog.open();
        });

        var download = new Anchor(doc.getDocumentUrl(), "Herunterladen");

        download.getElement().setAttribute("download", true);

        var btns = new Div(download, upload);
        var btnLayout = new HorizontalLayout(btns);
        btnLayout.setAlignItems(Alignment.CENTER);
        add(content, btnLayout);
    }

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");


}
