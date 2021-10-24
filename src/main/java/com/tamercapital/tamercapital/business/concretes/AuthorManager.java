package com.tamercapital.tamercapital.business.concretes;

import com.tamercapital.tamercapital.business.abstracts.AuthorService;
import com.tamercapital.tamercapital.exception.EntityNotFoundException;
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
    public Author add(AuthorCreateRequest authorCreateRequest) {
        Author author = new Author();
        BeanUtils.copyProperties(authorCreateRequest,author);
        return authorRepository.save(author);

    }

    @Override
    public void delete(String id) {
        Optional<Author> author = authorRepository.findById(id);
        authorRepository.deleteById(id);
    }

    @Override
    public Author update(String id, AuthorUpdateRequest authorUpdateRequest) {
        Optional<Author> optionalAuthor = this.authorRepository.findById(id);

        Author author = optionalAuthor.get();

        author.setFirstName(authorUpdateRequest.getFirstName());
        author.setLastName(authorUpdateRequest.getFirstName());

        return this.authorRepository.save(author);



    }

    @Override
    public List<AuthorViewRequest> getAll() {
        return this.authorRepository.findAll().stream().map(AuthorViewRequest::of)
                        .collect(Collectors.toList());

    }

    @Override
    public Optional<Author> findById(String id) {
        Optional<Author> author = this.authorRepository.findById(id);
        if(!author.isPresent()){
            throw  new EntityNotFoundException("Yazar Bulunamadı");

        }
        return  this.authorRepository.findById(id);
    }
}
