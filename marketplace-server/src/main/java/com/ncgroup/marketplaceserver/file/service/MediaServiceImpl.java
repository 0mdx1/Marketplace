package com.ncgroup.marketplaceserver.file.service;

import com.ncgroup.marketplaceserver.file.storage.CloudStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static com.ncgroup.marketplaceserver.file.helper.FileHelper.getPath;
import static org.apache.http.entity.ContentType.*;
import static org.springframework.util.StringUtils.getFilename;

@Service
public class MediaServiceImpl implements MediaService{

    private CloudStorage cloudStorage;
    private static final List<String> allowedTypes = Arrays.asList(
            IMAGE_PNG.getMimeType(),
            IMAGE_BMP.getMimeType(),
            IMAGE_GIF.getMimeType(),
            IMAGE_JPEG.getMimeType()
    );

    @Autowired
    public MediaServiceImpl(CloudStorage fileStorage) {
        this.cloudStorage = fileStorage;
    }

    @Override
    public String upload(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot upload empty file");
        }
        if (!allowedTypes.contains(file.getContentType())) {
            throw new IllegalStateException("File uploaded is not an image");
        }
        String filepath = String.format("tmp/%s_%s",UUID.randomUUID(),file.getOriginalFilename());
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));
        try {
            cloudStorage.upload(filepath, file.getInputStream(),metadata);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to get input stream", e);
        }
        return filepath;
    }

    @Override
    public void delete(String filepath) {
        this.cloudStorage.delete(filepath);
    }

    @Override
    public String confirmUpload(String filepath) {
        if(!getPath(filepath).equals("media")){
            String filename = getFilename(filepath);
            String newFilepath = String.format("media/%s",filename);
            this.cloudStorage.copy(filepath,newFilepath);
            this.cloudStorage.delete(filepath);
            return newFilepath;
        }
        return filepath;
    }

    @Override
    public CloudStorage getCloudStorage() {
        return this.cloudStorage;
    }
}
