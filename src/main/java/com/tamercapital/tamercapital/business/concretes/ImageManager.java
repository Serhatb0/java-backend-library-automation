package com.tamercapital.tamercapital.business.concretes;

import com.tamercapital.tamercapital.business.abstracts.ImageService;
import com.tamercapital.tamercapital.core.adapters.CloudinaryService;
import com.tamercapital.tamercapital.exception.EntityNotFoundException;
import com.tamercapital.tamercapital.model.Dtos.CreateDtos.ImageCreateRequest;
import com.tamercapital.tamercapital.model.concretes.Image;
import com.tamercapital.tamercapital.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class ImageManager implements ImageService {

    private  final ImageRepository imageRepository;
    private final CloudinaryService cloudinaryService;

    @Autowired
    public ImageManager(ImageRepository imageRepository, CloudinaryService cloudinaryService) {
        this.imageRepository = imageRepository;
        this.cloudinaryService = cloudinaryService;
    }

    @Override
    public Image add(ImageCreateRequest imageCreateRequest, MultipartFile file) {
        if(cloudinaryService.uploadFile(file) == null){
            throw new EntityNotFoundException("Resim Gönderilemedi");
        }else {
            Image image = new Image();
            String url = cloudinaryService.uploadFile(file);
            image.setImageUrl(url);
            image.setImageName(file.getContentType());
            image.setDeleteId(url.substring(61,81));


           return this.imageRepository.save(image);


        }

    }

    @Override
    public void delete(String id) {
        Optional<Image> image = this.imageRepository.findById(id);
        if(!image.isPresent()){
            throw new EntityNotFoundException("Resim Bulunamadı");
        }
        this.imageRepository.deleteById(id);
    }

    @Override
    public Optional<Image> findById(String id) {
        Optional<Image> image = this.imageRepository.findById(id);
        if(!image.isPresent()){
            throw new EntityNotFoundException("Resim Bulunamadı");
        }
        return  this.imageRepository.findById(id);
    }


}
