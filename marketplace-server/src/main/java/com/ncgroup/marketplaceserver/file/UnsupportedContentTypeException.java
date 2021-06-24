package com.ncgroup.marketplaceserver.file;

import org.apache.http.entity.ContentType;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;

import java.util.Collections;
import java.util.List;

public class UnsupportedContentTypeException extends Exception {
    private final List<ContentType> supportedContentTypes;
    @Nullable
    private final ContentType contentType;

    public UnsupportedContentTypeException(String message) {
        super(message);
        this.supportedContentTypes = Collections.emptyList();
        this.contentType = null;
    }

    public UnsupportedContentTypeException(String message, List<ContentType> supportedContentTypes,ContentType contentType) {
        super(message);
        this.supportedContentTypes = Collections.unmodifiableList(supportedContentTypes);
        this.contentType = contentType;
    }

    public List<ContentType> getSupportedContentTypes() {
        return this.supportedContentTypes;
    }

    @Nullable
    public ContentType getContentType() {
        return contentType;
    }
}
