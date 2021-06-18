package com.ncgroup.marketplaceserver.file.helper;

public class FileHelper {
    public static String getFilename(String filepath){
        return filepath.substring(filepath.lastIndexOf("/")+1);
    }

    public static String getPath(String filepath){
        return filepath.substring(0,filepath.lastIndexOf("/"));
    }
}
