package com.waflo.cooltimediaplattform.repository;

import com.waflo.cooltimediaplattform.model.File;
import org.springframework.content.commons.repository.ContentStore;

//eventually multiple Content Stores for better separation
public interface FileContentStore extends ContentStore<File,String> {

}
