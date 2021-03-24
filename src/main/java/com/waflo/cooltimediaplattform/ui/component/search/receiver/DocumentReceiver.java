package com.waflo.cooltimediaplattform.ui.component.search.receiver;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.waflo.cooltimediaplattform.backend.model.Document;
import com.waflo.cooltimediaplattform.backend.security.UserSession;
import com.waflo.cooltimediaplattform.backend.service.DocumentService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@SpringComponent
public class DocumentReceiver implements IReceiver<Document> {
    private final DocumentService documentService;
    private final UserSession session;

    public DocumentReceiver(DocumentService documentService, UserSession session) {
        this.documentService = documentService;
        this.session = session;
    }

    @Override
    public List<Document> search(String value) {
        return documentService.findAllByUser(session.getUser().getId()).stream().filter(d -> d.getTitle().toLowerCase().startsWith(value.toLowerCase())).collect(Collectors.toList());
    }

    @Override
    public Optional<Document> findByValue(String value) {
        return search(value).stream().findFirst();
    }
}
