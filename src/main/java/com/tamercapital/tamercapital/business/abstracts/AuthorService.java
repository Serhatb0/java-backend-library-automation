package com.tamercapital.tamercapital.business.abstracts;

import com.tamercapital.tamercapital.core.utilities.DataResult;
import com.tamercapital.tamercapital.core.utilities.Result;
import com.tamercapital.tamercapital.model.Dtos.CreateDtos.AuthorCreateRequest;
import com.tamercapital.tamercapital.model.Dtos.UpdateDtos.AuthorUpdateRequest;
import com.tamercapital.tamercapital.model.Dtos.ViewDtos.AuthorViewRequest;
import com.tamercapital.tamercapital.model.concretes.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    Result add(AuthorCreateRequest authorCreateRequest);

    Result delete(String id);

    Result update(String id, AuthorUpdateRequest authorUpdateRequest);

    DataResult<List<AuthorViewRequest>> getAll();

    DataResult<Optional<Author>> findById(String id);

}
