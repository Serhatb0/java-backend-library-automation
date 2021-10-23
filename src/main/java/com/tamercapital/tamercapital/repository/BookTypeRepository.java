package com.tamercapital.tamercapital.repository;

import com.tamercapital.tamercapital.model.concretes.BookType;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookTypeRepository extends MongoRepository<BookType,String> {

}
