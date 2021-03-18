package com.waflo.cooltimediaplattform.backend.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.waflo.cooltimediaplattform.backend.ResourceType;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class CloudinaryUploadService implements IUploadService {
    private final Cloudinary cloudinaryConfig;

    public CloudinaryUploadService(Cloudinary cloudinaryConfig) {
        this.cloudinaryConfig = cloudinaryConfig;
    }


    @Override
    public String upload(InputStream toUpload, String filePath, ResourceType typeOfResource) throws IOException {

        var upload = cloudinaryConfig.uploader().upload(IOUtils.toByteArray(toUpload), ObjectUtils.asMap("public_id", filePath, "resource_type", typeOfResource.toString(), "overwrite", true));

        return upload.get("url").toString();
    }

    public String rename(String fromPublicId, String toPublicId, ResourceType type) throws IOException {
        var res = cloudinaryConfig.uploader().rename(fromPublicId, toPublicId, ObjectUtils.asMap("resource_type", type.toString()));

        return res.get("url").toString();

    }

    public String download(String publicId) throws IOException{

        try {
            return cloudinaryConfig.privateDownload(publicId, "raw", ObjectUtils.asMap("attachment", true, "expires_at", LocalDateTime.now().plusDays(2).toEpochSecond(ZoneOffset.UTC), "resource_type", "raw"));
        } catch (Exception e) {
            return null;
        }

    }


    public boolean destroy(String publicID) throws IOException {
        var res = cloudinaryConfig.uploader().destroy(publicID, ObjectUtils.emptyMap());
        return res.get("result").equals("ok");
    }
}
