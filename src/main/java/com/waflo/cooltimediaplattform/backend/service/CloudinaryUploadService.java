package com.waflo.cooltimediaplattform.backend.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Service
public class CloudinaryUploadService {
    private final Cloudinary cloudinaryConfig;

    public CloudinaryUploadService(Cloudinary cloudinaryConfig) {
        this.cloudinaryConfig = cloudinaryConfig;
    }

    public String uploadStream(InputStream stream, String publicID) throws IOException {
        var f=new File("./tmp/temp");
        FileUtils.copyInputStreamToFile(stream, f);
        final Map upload = cloudinaryConfig.uploader().upload("./temp", ObjectUtils.asMap("public_id", publicID, "resource_type", "auto", "overwrite", true));
        FileUtils.deleteQuietly(f);
        return upload.get("url").toString();
    }
    public boolean destroy(String publicID) throws IOException {
       var res= cloudinaryConfig.uploader().destroy(publicID, ObjectUtils.emptyMap());
       return res.get("result").equals("ok");
    }
}
