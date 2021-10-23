package com.tamercapital.tamercapital.business.concretes;

import com.tamercapital.tamercapital.business.abstracts.ImageService;
import com.tamercapital.tamercapital.core.adapters.CloudinaryService;
import com.tamercapital.tamercapital.core.utilities.*;
import com.tamercapital.tamercapital.model.Dtos.CreateDtos.ImageCreateRequest;
import com.tamercapital.tamercapital.model.concretes.Image;
import com.tamercapital.tamercapital.repository.ImageRepository;
import org.springframework.beans.BeanUtils;
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
    public Result add(ImageCreateRequest imageCreateRequest, MultipartFile file) {
        if(cloudinaryService.uploadFile(file) == null){
            return  new ErrorResult("Resim Gonderilemedi");
        }else {
            Image image = new Image();
            String url = cloudinaryService.uploadFile(file);
            image.setImageUrl(url);
            image.setImageName(file.getContentType());
            image.setDeleteId(url.substring(61,81));


            this.imageRepository.save(image);
            return new SuccessDataResult<>("Resim Başarılı Bir Şekilde Kaydedildi");

        }
    }

    @Override
    public Result delete(String id) {
        Optional<Image> image = this.imageRepository.findById(id);
        if(!image.isPresent()){
            return  new ErrorResult("Resim Bulunamdı");
        }
        this.imageRepository.deleteById(id);
        return new SuccessResult("Resim Başarılı Bir Şekilde Silindi");
    }

    @Override
    public DataResult<Optional<Image>> findById(String id) {
        Optional<Image> image = this.imageRepository.findById(id);
        if(!image.isPresent()){
            return  new ErrorDataResult<>("Resim Bulunamadı");
        }
        return  new SuccessDataResult<>(this.imageRepository.findById(id));
    }

    @Override
    public Boolean isExists(int id) {
        return null;
    }
}
