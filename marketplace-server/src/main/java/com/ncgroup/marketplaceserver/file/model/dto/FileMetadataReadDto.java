package com.ncgroup.marketplaceserver.file.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FileMetadataReadDto {
    private String filename;
    private String resourceUrl;
}
