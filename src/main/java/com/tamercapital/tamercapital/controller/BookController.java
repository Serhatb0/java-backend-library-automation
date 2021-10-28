package com.tamercapital.tamercapital.controller;


import com.tamercapital.tamercapital.business.abstracts.BookService;
import com.tamercapital.tamercapital.model.Dtos.CreateDtos.BookCreateRequest;
import com.tamercapital.tamercapital.model.Dtos.UpdateDtos.BookUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/book")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }




    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id){
        return  ResponseEntity.ok(this.bookService.findById(id));
    }

    @GetMapping("/author/{authorId}")
    public  ResponseEntity<?> getBookByAuthor(@PathVariable String authorId){
        return  ResponseEntity.ok(this.bookService.getBookByAuthor(authorId));
    }

    @GetMapping("/findByBookNameKeyword")
    public  ResponseEntity<?> findByBookNameKeyword(@RequestParam String keyword){
        return  ResponseEntity.ok(this.bookService.findByBookNameKeyword(keyword));
    }
    @GetMapping("/getBooksByAuthorOrName")
    public  ResponseEntity<?> getBooksByAuthorOrName(@RequestParam String author , @RequestParam String name){
        return  ResponseEntity.ok(this.bookService.getBooksByAuthorOrName(author,name));
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @PutMapping("/{bookId}")
    public ResponseEntity<?> update(@Valid @PathVariable("bookId") String id,@RequestBody BookUpdateRequest bookUpdateRequest){
        return  ResponseEntity.ok(this.bookService.update(id,bookUpdateRequest));

    }


    @GetMapping()
    public ResponseEntity<?> getAll(){
        return  ResponseEntity.ok(this.bookService.getAll());
    }

    @PostMapping("/add")
    public  ResponseEntity<?> add(@Valid @RequestBody BookCreateRequest bookCreateRequest){
        return  ResponseEntity.ok(this.bookService.add(bookCreateRequest));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{bookId}")
    public  ResponseEntity<?> delete(@PathVariable("bookId") String id){
        this.bookService.delete(id);
        return ResponseEntity.ok().build();
    }
}
