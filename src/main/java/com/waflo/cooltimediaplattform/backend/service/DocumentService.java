package com.waflo.cooltimediaplattform.backend.service;

import com.waflo.cooltimediaplattform.backend.model.Audio;
import com.waflo.cooltimediaplattform.backend.model.Document;
import com.waflo.cooltimediaplattform.backend.repository.DocumentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public void delete(Document doc) {
        documentRepository.delete(doc);
    }
    public List<Document> findAllByUser(long userId) {
        return documentRepository.findAll().stream().filter(a -> a.getOwner().stream().anyMatch(o -> o.getId() == userId)).collect(Collectors.toList());
    }
}
