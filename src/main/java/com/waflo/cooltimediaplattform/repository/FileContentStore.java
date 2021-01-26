package com.waflo.cooltimediaplattform.repository;

import com.waflo.cooltimediaplattform.model.File;
import org.springframework.content.commons.repository.ContentStore;
import org.springframework.content.rest.StoreRestResource;

//eventually multiple Content Stores for better separation
@StoreRestResource
public interface FileContentStore extends ContentStore<File,String> {

}
