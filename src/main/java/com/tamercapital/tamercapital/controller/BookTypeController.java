package com.tamercapital.tamercapital.controller;

import com.tamercapital.tamercapital.business.abstracts.BookTypeService;
import com.tamercapital.tamercapital.model.Dtos.CreateDtos.BookTypeCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/bookType")
public class BookTypeController {
    private  final BookTypeService bookTypeService;

    @Autowired
    public BookTypeController(BookTypeService bookTypeService) {
        this.bookTypeService = bookTypeService;
    }

    @GetMapping("/bookType/{id}")
    public ResponseEntity<?> findById(@PathVariable  String id){
       return  ResponseEntity.ok(this.bookTypeService.findById(id));
    }

    @PostMapping("/bookType/add")
    public  ResponseEntity<?> add(@Valid @RequestBody BookTypeCreateRequest bookTypeCreateRequest){
        return  ResponseEntity.ok(this.bookTypeService.add(bookTypeCreateRequest));
    }

}
