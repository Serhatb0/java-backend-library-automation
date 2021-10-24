package com.tamercapital.tamercapital.controller;

import com.tamercapital.tamercapital.business.abstracts.ImageService;
import com.tamercapital.tamercapital.core.adapters.CloudinaryService;
import com.tamercapital.tamercapital.model.Dtos.CreateDtos.ImageCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/image")
public class ImageController {

    private  final ImageService imageService;
    private  final CloudinaryService cloudinaryService;

    @Autowired
    public ImageController(ImageService imageService, CloudinaryService cloudinaryService) {
        this.imageService = imageService;
        this.cloudinaryService = cloudinaryService;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file, ImageCreateRequest imageCreateRequest) {
        this.imageService.add(imageCreateRequest, file);
        return ResponseEntity.ok().build();

    }
}
