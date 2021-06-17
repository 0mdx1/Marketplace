package com.ncgroup.marketplaceserver.file.storage;

import java.io.InputStream;
import java.util.Map;

public interface CloudStorage {
    void upload(String filepath, InputStream is, Map<String,String> metadata);
    void delete(String filepath);
    void copy(String filepathFrom, String filepathTo);
    String getResourceUrl(String filepath);
}
