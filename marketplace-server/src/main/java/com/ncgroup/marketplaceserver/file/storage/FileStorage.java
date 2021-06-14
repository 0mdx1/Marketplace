package com.ncgroup.marketplaceserver.file.storage;

import java.io.InputStream;
import java.util.Map;

public interface FileStorage {
    void upload(String path, String filename, InputStream is, Map<String,String> metadata);
}
