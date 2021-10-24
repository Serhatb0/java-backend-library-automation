package com.tamercapital.tamercapital.controller;


import com.tamercapital.tamercapital.business.abstracts.AuthorService;
import com.tamercapital.tamercapital.model.Dtos.CreateDtos.AuthorCreateRequest;
import com.tamercapital.tamercapital.model.Dtos.UpdateDtos.AuthorUpdateRequest;
import com.tamercapital.tamercapital.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/author")
public class AuthorController {

    private  final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/author")
    public  ResponseEntity<?> getAll(){
        return  ResponseEntity.ok(this.authorService.getAll());
    }

    @PutMapping("/author/{authorId}")
    public ResponseEntity<?> update(@Valid @PathVariable("authorId") String id, @RequestBody AuthorUpdateRequest authorUpdateRequest){
        return  ResponseEntity.ok(this.authorService.update(id,authorUpdateRequest));
    }

    @DeleteMapping("/author/{authorId}")
    public ResponseEntity<?> delete(@PathVariable("authorId") String id){
        this.authorService.delete(id);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/author/add")
    public ResponseEntity<?> add(@Valid @RequestBody AuthorCreateRequest authorCreateRequest){
        return  ResponseEntity.ok(this.authorService.add(authorCreateRequest));
    }
}
