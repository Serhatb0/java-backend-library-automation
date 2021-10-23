package com.tamercapital.tamercapital.validator.concretes;

import com.tamercapital.tamercapital.repository.BookRepository;
import com.tamercapital.tamercapital.validator.abstracts.UniqueBookName;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueBookNameValidator implements ConstraintValidator<UniqueBookName,String> {

    private  final BookRepository bookRepository;

    @Autowired
    public UniqueBookNameValidator(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        return !bookRepository.existsByName(name);
    }
}
