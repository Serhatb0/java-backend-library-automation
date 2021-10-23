package com.tamercapital.tamercapital.business.abstracts;

import com.tamercapital.tamercapital.core.utilities.DataResult;
import com.tamercapital.tamercapital.core.utilities.Result;
import com.tamercapital.tamercapital.model.Dtos.CreateDtos.BookCreateRequest;
import com.tamercapital.tamercapital.model.Dtos.UpdateDtos.BookUpdateRequest;
import com.tamercapital.tamercapital.model.Dtos.ViewDtos.BookViewRequest;
import com.tamercapital.tamercapital.model.concretes.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    Result add(BookCreateRequest bookCreateRequest);

    Result update(String id,BookUpdateRequest bookUpdateRequest);

    Result delete(String id);

    DataResult<List<BookViewRequest>> getAll();

    DataResult<Optional<Book>> findById(String id);
}
