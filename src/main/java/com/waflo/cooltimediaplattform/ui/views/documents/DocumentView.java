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
import com.vaadin.flow.router.*;
import com.waflo.cooltimediaplattform.backend.ResourceType;
import com.waflo.cooltimediaplattform.backend.Utils;
import com.waflo.cooltimediaplattform.backend.model.Document;
import com.waflo.cooltimediaplattform.backend.security.UserSession;
import com.waflo.cooltimediaplattform.backend.service.CloudinaryUploadService;
import com.waflo.cooltimediaplattform.backend.service.DocumentService;
import com.waflo.cooltimediaplattform.ui.MainLayout;
import org.springframework.security.access.annotation.Secured;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Route(value = "document", layout = MainLayout.class)
@Secured("ROLE_USER")
public class DocumentView extends VerticalLayout implements HasUrlParameter<Long>, HasDynamicTitle {

    private Document doc;
    private final DocumentService documentService;
    private final CloudinaryUploadService uploadService;
    private final UserSession userSession;
    private String title;

    public DocumentView(DocumentService documentService, CloudinaryUploadService uploadService, UserSession userSession) {

        this.documentService = documentService;
        this.uploadService = uploadService;
        this.userSession = userSession;
    }


    @Override
    public void setParameter(BeforeEvent event, Long parameter) {
        doc = documentService.findById(parameter).orElseThrow(NotFoundException::new);

        initView();
        this.title=doc.getTitle();
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
        var right = new VerticalLayout(h, userSession.getUser()==null?null:delBtn);

        var content = new HorizontalLayout(left, right);
        var download = new Anchor(uploadService.download("documents/"+doc.getAuthor().getId()+"/"+Utils.toValidFileName(doc.getTitle()), "raw"), "Herunterladen");
        var upload = new Button("Neue Version hochladen", u -> {

            var rec = new FileBuffer();

            var upl = new Upload(rec);
            upl.setAcceptedFileTypes("text/*", "application/pdf", "application/*");

            upl.addSucceededListener(l -> {
                try {

                    var url = uploadService.uploadStream(rec.getInputStream(), "documents/" + doc.getAuthor().getId() + "/" + Utils.toValidFileName(doc.getTitle()), ResourceType.RAW);
                    doc.setDocumentUrl(url);
                    download.setHref(doc.getDocumentUrl());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            var dialog = new Dialog(upl);
            var ok = new Button("OK", b -> dialog.close());
            dialog.add(ok);
            dialog.open();
        });


        download.getElement().setAttribute("download", true);

        var btns = new Div(download, upload);
        var btnLayout = new HorizontalLayout(btns);
        btnLayout.setAlignItems(Alignment.CENTER);
        add(content, btnLayout);
    }

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");


    @Override
    public String getPageTitle() {
        return title;
    }
}
