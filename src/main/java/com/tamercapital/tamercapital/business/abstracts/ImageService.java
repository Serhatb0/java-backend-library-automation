package com.tamercapital.tamercapital.business.abstracts;

import com.tamercapital.tamercapital.model.Dtos.CreateDtos.ImageCreateRequest;
import com.tamercapital.tamercapital.model.concretes.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface ImageService {

    void add(ImageCreateRequest imageCreateRequest, MultipartFile file);

    void delete(String id);

    Optional<Image> findById(String id);




}
