package com.tamercapital.tamercapital.repository;

import com.tamercapital.tamercapital.model.concretes.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends MongoRepository<Book,String> {

    boolean existsByName(String bookName);



}
