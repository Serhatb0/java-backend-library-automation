package com.tamercapital.tamercapital.repository;

import com.tamercapital.tamercapital.model.concretes.Image;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ImageRepository extends MongoRepository<Image ,String> {


}
