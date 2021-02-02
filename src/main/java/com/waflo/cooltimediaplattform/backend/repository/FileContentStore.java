package com.waflo.cooltimediaplattform.backend.repository;

import com.waflo.cooltimediaplattform.backend.model.File;
import org.springframework.content.commons.repository.ContentStore;

//eventually multiple Content Stores for better separation
//@StoreRestResource
public interface FileContentStore extends ContentStore<File, String> {

}
