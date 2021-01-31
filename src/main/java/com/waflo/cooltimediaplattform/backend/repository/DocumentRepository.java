package com.waflo.cooltimediaplattform.backend.repository;

import com.waflo.cooltimediaplattform.backend.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {
}
