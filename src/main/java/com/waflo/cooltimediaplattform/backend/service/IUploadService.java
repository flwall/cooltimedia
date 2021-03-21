package com.waflo.cooltimediaplattform.backend.service;

import com.waflo.cooltimediaplattform.backend.ResourceType;

import java.io.IOException;
import java.io.InputStream;

public interface IUploadService {
    /**
     * @param toUpload       is the stream which should be saved
     * @param filePath       is a unique URI to the resource, you can separate with /, must not contain the appropriate file endings
     * @param typeOfResource Decides wheter the resource is a image, movie (=audio), or raw (other types of resources)
     * @return the URI to the resource, in most cases equal to given filePath, except it's already used
     */
    String upload(InputStream toUpload, String filePath, ResourceType typeOfResource) throws IOException;

    /**
     * Renames a resources URI
     *
     * @param fromURI      the current URI of the resource
     * @param toURI        the URI to rename to
     * @param resourceType type of the Resource (Video, Image, Raw)
     * @return the URI which the resource was renamed to
     */
    String rename(String fromURI, String toURI, ResourceType resourceType) throws IOException;

    /**
     * @param URI the URI of the resource to download
     * @return the URI where the resource can be downloaded
     */
    String download(String URI, String format) throws IOException;

    /**
     * Destroys the given resource
     *
     * @param URI is the unique identifier of the resource to destroy
     * @return true on success, false otherwise
     */
    boolean destroy(String URI) throws IOException;


}
