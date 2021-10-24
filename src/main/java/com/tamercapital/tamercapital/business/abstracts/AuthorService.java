package com.tamercapital.tamercapital.business.abstracts;

import com.tamercapital.tamercapital.model.Dtos.CreateDtos.AuthorCreateRequest;
import com.tamercapital.tamercapital.model.Dtos.UpdateDtos.AuthorUpdateRequest;
import com.tamercapital.tamercapital.model.Dtos.ViewDtos.AuthorViewRequest;
import com.tamercapital.tamercapital.model.concretes.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    Author add(AuthorCreateRequest authorCreateRequest);

    void delete(String id);

    Author update(String id, AuthorUpdateRequest authorUpdateRequest);

    List<AuthorViewRequest> getAll();

    Optional<Author> findById(String id);

}
