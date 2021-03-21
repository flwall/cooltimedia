package com.waflo.cooltimediaplattform.ui.views.documents;

import com.vaadin.flow.component.ComponentEventListener;
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
import com.waflo.cooltimediaplattform.backend.model.Rating;
import com.waflo.cooltimediaplattform.backend.security.UserSession;
import com.waflo.cooltimediaplattform.backend.service.DocumentService;
import com.waflo.cooltimediaplattform.backend.service.IUploadService;
import com.waflo.cooltimediaplattform.backend.service.RatingService;
import com.waflo.cooltimediaplattform.ui.MainLayout;
import com.waflo.cooltimediaplattform.ui.component.RatingComponent;
import com.waflo.cooltimediaplattform.ui.events.SaveEvent;
import org.springframework.security.access.annotation.Secured;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;

@Route(value = "document", layout = MainLayout.class)
public class DocumentView extends VerticalLayout implements HasUrlParameter<Long>, HasDynamicTitle {

    private Document doc;
    private final DocumentService documentService;
    private final IUploadService uploadService;
    private final UserSession userSession;
    private String title;

    private Button backBtn;
    private final RatingService ratingService;

    public DocumentView(DocumentService documentService, IUploadService uploadService, UserSession userSession, RatingService ratingService) {

        this.documentService = documentService;
        this.uploadService = uploadService;
        this.userSession = userSession;
        this.ratingService = ratingService;
    }


    @Override
    public void setParameter(BeforeEvent event, Long parameter) {
        doc = documentService.findById(parameter).orElseThrow(NotFoundException::new);

        initView();
        this.title = doc.getTitle();
    }

    private void initView() {

        var title = new H1(doc.getTitle());
        backBtn = new Button("←", l -> UI.getCurrent().getPage().getHistory().back());
        var head = new HorizontalLayout(backBtn, title);


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

        var right = renderRatings(doc.getRatings());

        var content = new HorizontalLayout(left, right);
        Anchor download = null;
        try {
            var ownerId = doc.getOwner().stream().findFirst().get().getId();
            download = new Anchor(uploadService.download("documents/" + ownerId + "/" + Utils.toValidFileName(doc.getTitle()), "pdf"), "Herunterladen");
        } catch (IOException e) {
            e.printStackTrace();
        }
        var finalDownload = download;
        var upload = new Button("Neue Version hochladen", u -> {

            var rec = new FileBuffer();

            var upl = new Upload(rec);
            upl.setAcceptedFileTypes("text/*", "application/pdf", "application/*");

            upl.addSucceededListener(l -> {
                try {
                    var ownerId = doc.getOwner().stream().findFirst().get().getId();
                    var url = uploadService.upload(rec.getInputStream(), "documents/" + ownerId + "/" + Utils.toValidFileName(doc.getTitle()), ResourceType.RAW);
                    doc.setDocumentUrl(url);
                    finalDownload.setHref(doc.getDocumentUrl());
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

    private VerticalLayout renderRatings(Collection<Rating> ratings) {
        var rating = new RatingComponent(ratings, (ComponentEventListener<SaveEvent>) event -> {
            if (event.getSource() instanceof RatingComponent) {
                var rating1 = ((RatingComponent) event.getSource()).getBean();
                rating1.setRatedMedia(doc);
                var u = userSession.getUser() == null ? userSession.getGuestUser() : userSession.getUser();
                rating1.setCreator(u);
                ratingService.save(rating1);
                ((RatingComponent) event.getSource()).addComment(rating1);
            }
        });

        var delBtn = new Button("Löschen", l -> {
            try {
                uploadService.destroy("documents/" + new ArrayList<>(doc.getOwner()).get(0).getId() + "/" + Utils.toValidFileName(doc.getTitle()));

            } catch (IOException e) {
                e.printStackTrace();
            }
            documentService.delete(doc);
            doc=null;
            backBtn.click();

        });

        return new VerticalLayout(rating, userSession.getUser() == null ? null : delBtn);
    }

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");


    @Override
    public String getPageTitle() {
        return title;
    }
}
