package com.waflo.cooltimediaplattform.service;

import com.waflo.cooltimediaplattform.model.File;
import com.waflo.cooltimediaplattform.repository.FileRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FileService {
    private final FileRepository fileRepository;

    public FileService(FileRepository repo) {
        this.fileRepository = repo;
    }


    public Optional<File> findById(long id) {

        return fileRepository.findById(id);
    }

    public void add(File f) {
        fileRepository.save(f);

    }
}
