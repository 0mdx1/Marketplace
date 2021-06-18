package com.ncgroup.marketplaceserver.file.storage;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Map;

@Component
public class S3Storage implements CloudStorage {

    @Value("${aws.bucket.name}")
    private String bucketName;

    @Value("${aws.bucket.region}")
    private String bucketRegion;

    private AmazonS3 amazonS3;

    @Autowired
    public S3Storage(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    @Override
    public void upload(String filepath, InputStream is, Map<String,String> metadata) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
            metadata.forEach(objectMetadata::addUserMetadata);
        try {
            amazonS3.putObject(bucketName, filepath, is,objectMetadata);
        } catch (AmazonServiceException e) {
            throw new IllegalStateException("Failed to upload the file", e);
        }
    }

    @Override
    public void delete(String filepath) {

        try {
            amazonS3.deleteObject(bucketName, filepath);
        } catch (AmazonServiceException e) {
            throw new IllegalStateException("Failed to upload the file", e);
        }
    }

    @Override
    public void copy(String filepathFrom, String filepathTo) {
        try {
            amazonS3.copyObject(
                    bucketName,
                    filepathFrom,
                    bucketName,
                    filepathTo
            );
        } catch (AmazonServiceException e) {
            throw new IllegalStateException("Failed to move the file", e);
        }
    }

    @Override
    public String getResourceUrl(String filepath) {
        if(filepath==null||filepath.isEmpty()){
            return "";
        }
        return String.format("https://%s.s3.%s.amazonaws.com/%s",bucketName,bucketRegion,filepath);
    }
}
