package com.ncgroup.marketplaceserver.file.helper;

public class FileHelper {
    public static String getKey(String filepath){
        return filepath.substring(filepath.indexOf("/")+1);
    }

    public static String getBucket(String filepath){
        return filepath.substring(0,filepath.indexOf("/"));
    }
}
