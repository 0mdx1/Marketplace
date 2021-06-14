package com.ncgroup.marketplaceserver.file.service;

import com.ncgroup.marketplaceserver.file.storage.FileStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static org.apache.http.entity.ContentType.*;

@Service
public class MediaServiceImpl implements MediaService{

    private FileStorage fileStorage;
    @Value("${aws.bucket.name}")
    private String bucketName;
    @Value("${aws.bucket.region}")
    private String bucketRegion;
    private static final List<String> allowedTypes = Arrays.asList(
            IMAGE_PNG.getMimeType(),
            IMAGE_BMP.getMimeType(),
            IMAGE_GIF.getMimeType(),
            IMAGE_JPEG.getMimeType()
    );

    @Autowired
    public MediaServiceImpl(FileStorage fileStorage) {
        this.fileStorage = fileStorage;
    }

    @Override
    public String upload(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot upload empty file");
        }
        if (!allowedTypes.contains(file.getContentType())) {
            throw new IllegalStateException("File uploaded is not an image");
        }
        String fileName = String.format("%s_%s",UUID.randomUUID(),file.getOriginalFilename());
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));
        try {
            fileStorage.upload(bucketName, fileName, file.getInputStream(),metadata);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to upload file", e);
        }
        return fileName;
    }

    @Override
    public String getResourceUrl(String filename) {
        return String.format("https://%s.s3.%s.amazonaws.com/%s",bucketName,bucketRegion,filename);
    }
}
