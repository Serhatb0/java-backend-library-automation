package com.tamercapital.tamercapital.business.abstracts;

import com.tamercapital.tamercapital.model.Dtos.CreateDtos.BookCreateRequest;
import com.tamercapital.tamercapital.model.Dtos.UpdateDtos.BookUpdateRequest;
import com.tamercapital.tamercapital.model.Dtos.ViewDtos.BookViewRequest;
import com.tamercapital.tamercapital.model.concretes.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    Book add(BookCreateRequest bookCreateRequest);

    Book update(String id,BookUpdateRequest bookUpdateRequest);

    void delete(String id);

    List<BookViewRequest> getAll();

    Optional<Book> findById(String id);
}
