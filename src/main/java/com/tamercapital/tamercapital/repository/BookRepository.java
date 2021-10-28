package com.tamercapital.tamercapital.repository;

import com.tamercapital.tamercapital.model.concretes.Author;
import com.tamercapital.tamercapital.model.concretes.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends MongoRepository<Book,String> {

    boolean existsByName(String bookName);


    @Query("{author : ?0}")
    List<Book> getBookByAuthor(String authorId);


    @Query("{$or :[{author: ?0},{name: ?1}]}")
    List<Book> getBooksByAuthorOrName(String author, String name);





    // Search
    @Query("{ name : { $regex : ?0 } }")
    List<Book> findByBookNameKeyword(String keyword);





}
