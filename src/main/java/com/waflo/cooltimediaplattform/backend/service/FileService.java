package com.waflo.cooltimediaplattform.backend.service;

import com.waflo.cooltimediaplattform.backend.model.File;
import com.waflo.cooltimediaplattform.backend.repository.FileRepository;
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

    public File save(File f) {
        return fileRepository.save(f);

    }

    public void delete(File f) {
        fileRepository.delete(f);
    }

    public Optional<File> findByFileName(String fname) {
        return fileRepository.findAll().stream().filter(f -> f.getName().equals(fname)).findFirst();      //TODO: duplicate files would be a problem

    }


}
