package com.tamercapital.tamercapital.business.abstracts;

import com.tamercapital.tamercapital.core.utilities.DataResult;
import com.tamercapital.tamercapital.core.utilities.Result;
import com.tamercapital.tamercapital.model.Dtos.CreateDtos.BookTypeCreateRequest;
import com.tamercapital.tamercapital.model.Dtos.UpdateDtos.BookTypeUpdateRequest;
import com.tamercapital.tamercapital.model.Dtos.ViewDtos.BookTypeViewRequest;
import com.tamercapital.tamercapital.model.concretes.BookType;

import java.util.List;
import java.util.Optional;

public interface BookTypeService {

    Result add(BookTypeCreateRequest bookTypeCreateRequest);

    Result update(String id,BookTypeUpdateRequest bookTypeUpdateRequest);

    Result delete(String id);

    DataResult<Optional<BookType>> findAllById(String id);

    DataResult<List<BookTypeViewRequest>> getAll();
}
