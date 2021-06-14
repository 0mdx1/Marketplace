package com.ncgroup.marketplaceserver.file.service;

import org.springframework.web.multipart.MultipartFile;

public interface MediaService {
    public String upload(MultipartFile file);
    public String getResourceUrl(String filename);
}
