package com.waflo.cooltimediaplattform.ui.views.documents;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.waflo.cooltimediaplattform.backend.model.Document;
import com.waflo.cooltimediaplattform.backend.security.UserSession;
import com.waflo.cooltimediaplattform.backend.service.DocumentService;
import com.waflo.cooltimediaplattform.ui.MainLayout;
import com.waflo.cooltimediaplattform.ui.component.CardView;
import com.waflo.cooltimediaplattform.ui.component.ListCardCommand;
import com.waflo.cooltimediaplattform.ui.component.documents.DocumentCardCommand;
import com.waflo.cooltimediaplattform.ui.component.documents.DocumentForm;
import com.waflo.cooltimediaplattform.ui.views.AbstractEntitiesView;
import org.springframework.security.access.annotation.Secured;

import java.util.stream.Collectors;

@Secured("ROLE_USER")
@Route(value = "documents", layout = MainLayout.class)
@PageTitle("Dokumente | CTM")
public class DocumentsView extends AbstractEntitiesView<Document> {

    private final DocumentService documentService;
    private final UserSession userSession;

    public DocumentsView(DocumentService documentService, UserSession userSession, DocumentForm form) {
        super(form);
        this.documentService = documentService;
        this.userSession = userSession;
        this.entities = documentService.findAllByUser(userSession.getUser().getId());


        initView();
    }


    ListCardCommand<DocumentCardCommand> documentList;

    private void initView() {
        add(new H1("Dokumente"));
        documentList = new ListCardCommand<>();
        initList();
        add(new CardView<>(documentList));

        add(new Button("Hinzufügen", c -> openEditor(new Document())));

        Div content = new Div(form);
        content.addClassName("documentform");
        content.setSizeFull();
        add(content);
        closeEditor();

    }

    @Override
    protected void initList() {
        documentList.setCommands(documentService.findAllByUser(userSession.getUser().getId()).stream().map(DocumentCardCommand::new).collect(Collectors.toList()));
    }
}
