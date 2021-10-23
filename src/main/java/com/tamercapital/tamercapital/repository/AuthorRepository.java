package com.tamercapital.tamercapital.repository;

import com.tamercapital.tamercapital.model.concretes.Author;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AuthorRepository extends MongoRepository<Author,String> {


}
