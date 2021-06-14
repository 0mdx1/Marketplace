package com.ncgroup.marketplaceserver.file.storage;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Map;

@Component
public class S3Storage implements FileStorage{

    private AmazonS3 amazonS3;

    @Autowired
    public S3Storage(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    @Override
    public void upload(String path, String filename, InputStream is, Map<String,String> metadata) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
            metadata.forEach(objectMetadata::addUserMetadata);
        try {
            amazonS3.putObject(path, filename, is,objectMetadata);
        } catch (AmazonServiceException e) {
            throw new IllegalStateException("Failed to upload the file", e);
        }
    }

    @Override
    public void delete(String path, String filename) {
        try {
            amazonS3.deleteObject(path, filename);
        } catch (AmazonServiceException e) {
            throw new IllegalStateException("Failed to upload the file", e);
        }
    }
}
