package com.ncgroup.marketplaceserver.file.service;

import com.ncgroup.marketplaceserver.file.UnsupportedContentTypeException;
import com.ncgroup.marketplaceserver.file.storage.CloudStorage;
import org.springframework.web.multipart.MultipartFile;

public interface MediaService {
    String upload(MultipartFile file) throws UnsupportedContentTypeException;

    String confirmUpload(String filename);

    void delete(String filename);

    CloudStorage getCloudStorage();
}
