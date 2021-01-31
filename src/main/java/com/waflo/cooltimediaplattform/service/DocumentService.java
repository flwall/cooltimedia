package com.waflo.cooltimediaplattform.service;

import com.waflo.cooltimediaplattform.model.Document;
import com.waflo.cooltimediaplattform.repository.DocumentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;

    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }


    public List<Document> findAll() {
        return documentRepository.findAll();
    }

    public Optional<Document> findById(long id) {
        return documentRepository.findById(id);
    }

    public Document save(Document entity) {
        return documentRepository.save(entity);
    }
}
