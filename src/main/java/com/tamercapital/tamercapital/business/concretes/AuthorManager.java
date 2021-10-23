package com.tamercapital.tamercapital.business.concretes;

import com.tamercapital.tamercapital.business.abstracts.AuthorService;
import com.tamercapital.tamercapital.core.utilities.*;
import com.tamercapital.tamercapital.model.Dtos.CreateDtos.AuthorCreateRequest;
import com.tamercapital.tamercapital.model.Dtos.UpdateDtos.AuthorUpdateRequest;
import com.tamercapital.tamercapital.model.Dtos.ViewDtos.AuthorViewRequest;
import com.tamercapital.tamercapital.model.concretes.Author;
import com.tamercapital.tamercapital.repository.AuthorRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthorManager implements AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorManager(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Result add(AuthorCreateRequest authorCreateRequest) {
        Author author = new Author();
        BeanUtils.copyProperties(authorCreateRequest,author);
        authorRepository.save(author);
        return  new SuccessDataResult<>(author,"Yazar Başarıyla  Eklendi");

    }

    @Override
    public Result delete(String id) {
        Optional<Author> author = authorRepository.findById(id);
        authorRepository.deleteById(id);
        return  new SuccessDataResult<>(author,"Yazar Başarıyla Silindi");
    }

    @Override
    public Result update(String id, AuthorUpdateRequest authorUpdateRequest) {
        Optional<Author> optionalAuthor = this.authorRepository.findById(id);

        Author author = optionalAuthor.get();

        author.setFirstName(authorUpdateRequest.getFirstName());
        author.setLastName(authorUpdateRequest.getFirstName());

        this.authorRepository.save(author);
        return  new SuccessDataResult<>(author,"Yaza Güncellendi");


    }

    @Override
    public DataResult<List<AuthorViewRequest>> getAll() {
        return  new SuccessDataResult<List<AuthorViewRequest>>(
                this.authorRepository.findAll().stream().map(AuthorViewRequest::of)
                        .collect(Collectors.toList()));

    }

    @Override
    public DataResult<Optional<Author>> findById(String id) {
        Optional<Author> author = this.authorRepository.findById(id);
        if(!author.isPresent()){
            return  new ErrorDataResult<>("Yazar Bulunamadı");

        }
        return  new SuccessDataResult<>(this.authorRepository.findById(id),"");
    }
}
