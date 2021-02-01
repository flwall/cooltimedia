package com.waflo.cooltimediaplattform.ui.component.search.receiver;

import com.waflo.cooltimediaplattform.backend.model.Document;
import com.waflo.cooltimediaplattform.backend.service.DocumentService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DocumentReceiver implements IReceiver<Document> {
    private DocumentService documentService;

    public DocumentReceiver(DocumentService documentService) {
        this.documentService = documentService;
    }

    @Override
    public List<Document> search(String value) {
        return documentService.findAll().stream().filter(d -> d.getTitle().equalsIgnoreCase(value)).collect(Collectors.toList());
    }

    @Override
    public Optional<Document> findByValue(String value) {
        return search(value).stream().findFirst();
    }
}
