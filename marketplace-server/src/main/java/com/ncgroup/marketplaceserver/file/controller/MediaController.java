package com.ncgroup.marketplaceserver.file.controller;

import com.ncgroup.marketplaceserver.file.model.dto.FileMetadataReadDto;
import com.ncgroup.marketplaceserver.file.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(
        path = "/api/media",
        produces = "application/json"
)
public class MediaController {

    private MediaService mediaService;

    @Autowired
    public MediaController(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    @PostMapping(
        path = "/",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<FileMetadataReadDto> upload(@RequestParam("file") MultipartFile file){
        String filename = mediaService.upload(file);
        return new ResponseEntity<>(
                new FileMetadataReadDto(
                        filename,
                        mediaService.getCloudStorage().getResourceUrl(filename)
                ),
                HttpStatus.OK
        );
    }
}
