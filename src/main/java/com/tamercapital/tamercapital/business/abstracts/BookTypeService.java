package com.tamercapital.tamercapital.business.abstracts;

import com.tamercapital.tamercapital.model.Dtos.CreateDtos.BookTypeCreateRequest;
import com.tamercapital.tamercapital.model.Dtos.UpdateDtos.BookTypeUpdateRequest;
import com.tamercapital.tamercapital.model.Dtos.ViewDtos.BookTypeViewRequest;
import com.tamercapital.tamercapital.model.concretes.BookType;

import java.util.List;
import java.util.Optional;

public interface BookTypeService {

    BookType add(BookTypeCreateRequest bookTypeCreateRequest);

    void update(String id,BookTypeUpdateRequest bookTypeUpdateRequest);

    void delete(String id);

    Optional<BookType> findById(String id);

    List<BookTypeViewRequest> getAll();
}
