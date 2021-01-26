package com.waflo.cooltimediaplattform.repository;

import com.waflo.cooltimediaplattform.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {
}
