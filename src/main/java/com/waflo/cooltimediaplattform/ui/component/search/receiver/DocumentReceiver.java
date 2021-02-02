package com.waflo.cooltimediaplattform.ui.component.search.receiver;

import com.waflo.cooltimediaplattform.backend.model.Document;
import com.waflo.cooltimediaplattform.backend.security.UserSession;
import com.waflo.cooltimediaplattform.backend.service.DocumentService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DocumentReceiver implements IReceiver<Document> {
    private final DocumentService documentService;
    private final UserSession session;

    public DocumentReceiver(DocumentService documentService, UserSession session) {
        this.documentService = documentService;
        this.session = session;
    }

    @Override
    public List<Document> search(String value) {
        return documentService.findAllByUser(session.getUser().getId()).stream().filter(d -> d.getTitle().equalsIgnoreCase(value)).collect(Collectors.toList());
    }

    @Override
    public Optional<Document> findByValue(String value) {
        return search(value).stream().findFirst();
    }
}
