package com.waflo.cooltimediaplattform.backend.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.waflo.cooltimediaplattform.backend.ResourceType;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Service
public class CloudinaryUploadService {
    private final Cloudinary cloudinaryConfig;

    public CloudinaryUploadService(Cloudinary cloudinaryConfig) {
        this.cloudinaryConfig = cloudinaryConfig;
    }

    public String uploadStream(InputStream stream, String publicID, ResourceType resourceType) throws IOException {
        final Map upload = cloudinaryConfig.uploader().upload(IOUtils.toByteArray(stream), ObjectUtils.asMap("public_id", publicID, "resource_type", resourceType.toString(), "overwrite", true));
        return upload.get("url").toString();
    }

    public String rename(String fromPublicId, String toPublicId, ResourceType type) throws IOException {
        var res = cloudinaryConfig.uploader().rename(fromPublicId, toPublicId, ObjectUtils.asMap("resource_type", type.toString()));
        return res.get("url").toString();

    }

    public boolean destroy(String publicID) throws IOException {
        var res = cloudinaryConfig.uploader().destroy(publicID, ObjectUtils.emptyMap());
        return res.get("result").equals("ok");
    }
}
