package com.tamercapital.tamercapital.business.concretes;

import com.tamercapital.tamercapital.business.abstracts.BookTypeService;
import com.tamercapital.tamercapital.exception.EntityNotFoundException;
import com.tamercapital.tamercapital.model.Dtos.CreateDtos.BookTypeCreateRequest;
import com.tamercapital.tamercapital.model.Dtos.UpdateDtos.BookTypeUpdateRequest;
import com.tamercapital.tamercapital.model.Dtos.ViewDtos.BookTypeViewRequest;
import com.tamercapital.tamercapital.model.concretes.BookType;
import com.tamercapital.tamercapital.repository.BookTypeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookTypeManager implements BookTypeService {

    private final BookTypeRepository bookTypeRepository;

    @Autowired
    public BookTypeManager(BookTypeRepository bookTypeRepository) {
        this.bookTypeRepository = bookTypeRepository;
    }

    @Override
    public BookType add(BookTypeCreateRequest bookTypeCreateRequest) {
        BookType bookType = new BookType();

        BeanUtils.copyProperties(bookTypeCreateRequest, bookType);
        return this.bookTypeRepository.save(bookType);

    }

    @Override
    public void update(String id, BookTypeUpdateRequest bookTypeUpdateRequest) {
        Optional<BookType> optionalBookType = this.bookTypeRepository.findById(id);

        if (!optionalBookType.isPresent()) {
            throw new EntityNotFoundException("Kitap VeriTabında Mevcut Değil");
        }
        BookType bookType = optionalBookType.get();
        bookType.setTypeName(bookTypeUpdateRequest.getTypeName());


    }

    @Override
    public void delete(String id) {
        Optional<BookType> bookType = this.bookTypeRepository.findById(id);
        if (!bookType.isPresent()) {
            throw new EntityNotFoundException("Kitap Bulunamadı");
        }
        this.bookTypeRepository.deleteById(id);


    }

    @Override
    public Optional<BookType> findById(String id) {
        Optional<BookType> bookType = this.bookTypeRepository.findById(id);
        if (!bookType.isPresent()) {
            throw new EntityNotFoundException("Kitap Bulunamadı");

        }
        return this.bookTypeRepository.findById(id);

    }

    @Override
    public List<BookTypeViewRequest> getAll() {
        return this.bookTypeRepository.findAll().stream().map(BookTypeViewRequest::of)
                .collect(Collectors.toList());
    }
}
