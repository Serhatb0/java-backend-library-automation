package com.tamercapital.tamercapital.business.abstracts;

import com.tamercapital.tamercapital.core.utilities.DataResult;
import com.tamercapital.tamercapital.core.utilities.Result;
import com.tamercapital.tamercapital.model.Dtos.CreateDtos.ImageCreateRequest;
import com.tamercapital.tamercapital.model.concretes.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface ImageService {

    Result add(ImageCreateRequest imageCreateRequest, MultipartFile file);

    Result delete(String id);

    DataResult<Optional<Image>> findById(String id);

    Boolean isExists(int id);


}
