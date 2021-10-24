package com.tamercapital.tamercapital.controller;


import com.tamercapital.tamercapital.business.abstracts.BookService;
import com.tamercapital.tamercapital.model.Dtos.CreateDtos.BookCreateRequest;
import com.tamercapital.tamercapital.model.Dtos.UpdateDtos.BookUpdateRequest;
import com.tamercapital.tamercapital.model.Dtos.ViewDtos.BookViewRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/book")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<?> findById(@PathVariable String id){
        return  ResponseEntity.ok(this.bookService.findById(id));
    }

    @PutMapping("/book/{bookId}")
    public ResponseEntity<?> update(@Valid @PathVariable("bookId") String id,@RequestBody BookUpdateRequest bookUpdateRequest){
        return  ResponseEntity.ok(this.bookService.update(id,bookUpdateRequest));
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @GetMapping()
    public ResponseEntity<?> getAll(){
        return  ResponseEntity.ok(this.bookService.getAll());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public  ResponseEntity<?> add(@Valid @RequestBody BookCreateRequest bookCreateRequest){
        return  ResponseEntity.ok(this.bookService.add(bookCreateRequest));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/book/{bookId}")
    public  ResponseEntity<?> delete(@PathVariable("bookId") String id){
        this.bookService.delete(id);
        return ResponseEntity.ok().build();
    }
}
