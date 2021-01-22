package com.waflo.cooltimediaplattform.repository;

import com.waflo.cooltimediaplattform.model.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File,Long> {
}
