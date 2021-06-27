package com.ncgroup.marketplaceserver.file.service;

import com.ncgroup.marketplaceserver.file.UnsupportedContentTypeException;
import com.ncgroup.marketplaceserver.file.storage.CloudStorage;
import org.springframework.web.multipart.MultipartFile;

public interface MediaService {
    public String upload(MultipartFile file) throws UnsupportedContentTypeException;
    public String confirmUpload(String filename);
    public void delete(String filename);
    public CloudStorage getCloudStorage();
}
