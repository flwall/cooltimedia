package com.waflo.cooltimediaplattform.backend.controller;

import com.waflo.cooltimediaplattform.backend.model.File;
import com.waflo.cooltimediaplattform.backend.repository.FileContentStore;
import com.waflo.cooltimediaplattform.backend.service.FileService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
//@Secured("ROLE_USER")
public class FileContentController {

    private final FileService fileService;
    private final FileContentStore contentStore;

    public FileContentController(FileService fileService, FileContentStore contentStore){
        this.fileService = fileService;
        this.contentStore = contentStore;
    }


    @RequestMapping(value="/files/{fileName}", method = RequestMethod.GET)
    public ResponseEntity<?> getContent(@PathVariable("fileName") String fname) {

        Optional<File> f = fileService.findByFileName(fname);
        if (f.isPresent()&&checkAccess(f.get())) {
            InputStreamResource inputStreamResource = new InputStreamResource(contentStore.getContent(f.get()));
            HttpHeaders headers = new HttpHeaders();
            headers.setContentLength(f.get().getContentLength());
            headers.set("Content-Type", f.get().getMimeType());
            return new ResponseEntity<Object>(inputStreamResource, headers, HttpStatus.OK);
        }
        return null;
    }

    private boolean checkAccess(File file) {
        return true;
        //TODO: Check if authenticated User has access to this resource
    }
}
