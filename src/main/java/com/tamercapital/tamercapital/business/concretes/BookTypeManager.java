package com.tamercapital.tamercapital.business.concretes;

import com.tamercapital.tamercapital.business.abstracts.BookTypeService;
import com.tamercapital.tamercapital.core.utilities.*;
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
    public Result add(BookTypeCreateRequest bookTypeCreateRequest) {
        BookType bookType = new BookType();

        BeanUtils.copyProperties(bookTypeCreateRequest,bookType);
        this.bookTypeRepository.save(bookType);
        return  new SuccessDataResult<>(bookType,"Kitap Tipi Başarılı Bir Şekilde Eklendi");
    }

    @Override
    public Result update(String id,BookTypeUpdateRequest bookTypeUpdateRequest) {
        Optional<BookType> optionalBookType = this.bookTypeRepository.findById(id);

        if(!optionalBookType.isPresent()){
            return  new ErrorResult("Kitap Tipi Veritabınında Mevcut Değil");
        }
        BookType bookType = optionalBookType.get();
        bookType.setTypeName(bookTypeUpdateRequest.getTypeName());
        return  new SuccessDataResult<>(bookType,"Kitap Tipi Güncellendi");

    }

    @Override
    public Result delete(String id) {
        Optional<BookType> bookType = this.bookTypeRepository.findById(id);
        if(!bookType.isPresent()){
            return  new ErrorResult("Kitap tipi Bulunamadı");
        }
        this.bookTypeRepository.deleteById(id);
        return  new SuccessDataResult<>(bookType,"Kitap Tipi Başarılı Bir Şekilde Silindi");

    }

    @Override
    public DataResult<Optional<BookType>> findAllById(String id) {
        Optional<BookType> bookType = this.bookTypeRepository.findById(id);
        if(!bookType.isPresent()){
            return new ErrorDataResult<>("Kitap Tipi Bulunamadı");

        }
        return new SuccessDataResult<>(this.bookTypeRepository.findById(id),"");

    }

    @Override
    public DataResult<List<BookTypeViewRequest>> getAll() {
        return new SuccessDataResult<List<BookTypeViewRequest>>(
                this.bookTypeRepository.findAll().stream().map(BookTypeViewRequest::of)
                        .collect(Collectors.toList()));
    }
}
