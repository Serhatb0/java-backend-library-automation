package com.tamercapital.tamercapital.business.concretes;

import com.tamercapital.tamercapital.business.abstracts.AuthorService;
import com.tamercapital.tamercapital.business.abstracts.BookService;
import com.tamercapital.tamercapital.business.abstracts.BookTypeService;
import com.tamercapital.tamercapital.business.abstracts.ImageService;
import com.tamercapital.tamercapital.core.utilities.*;
import com.tamercapital.tamercapital.model.Dtos.CreateDtos.BookCreateRequest;
import com.tamercapital.tamercapital.model.Dtos.UpdateDtos.BookUpdateRequest;
import com.tamercapital.tamercapital.model.Dtos.ViewDtos.BookViewRequest;
import com.tamercapital.tamercapital.model.concretes.Author;
import com.tamercapital.tamercapital.model.concretes.Book;
import com.tamercapital.tamercapital.model.concretes.BookType;
import com.tamercapital.tamercapital.model.concretes.Image;
import com.tamercapital.tamercapital.repository.BookRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookManager implements BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final ImageService imageService;
    private final BookTypeService bookTypeService;

    @Autowired
    public BookManager(BookRepository bookRepository, AuthorService authorService, ImageService imageService, BookTypeService bookTypeService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.imageService = imageService;
        this.bookTypeService = bookTypeService;
    }

    @Override
    public Result add(BookCreateRequest bookCreateRequest) {
        DataResult<Optional<Author>> author = this.authorService.findById(bookCreateRequest.getAuthorId());
        DataResult<Optional<Image>> image = this.imageService.findById(bookCreateRequest.getImageId());
        DataResult<Optional<BookType>> bookType = this.bookTypeService.findAllById(bookCreateRequest.getTypeId());

        Book book = new Book();
        BeanUtils.copyProperties(bookCreateRequest,book);
        book.setAuthor(author.getData().get());
        book.setImage(image.getData().get());
        book.setBookType(bookType.getData().get());
         this.bookRepository.save(book);
         return  new SuccessDataResult<>(book,"Kitap Başarılı Bir Şekilde Eklendi");

    }

    @Override
    public Result update(String id, BookUpdateRequest bookUpdateRequest) {
        DataResult<Optional<Author>> author = this.authorService.findById(bookUpdateRequest.getAuthorId());
        DataResult<Optional<Image>> image = this.imageService.findById(bookUpdateRequest.getImageId());
        DataResult<Optional<BookType>> bookType = this.bookTypeService.findAllById(bookUpdateRequest.getTypeId());
        Optional<Book> optionalBook = this.bookRepository.findById(id);
        if(!optionalBook.isPresent()){
            return  new ErrorResult("Kitap Bulunamadı");
        }

        Book book = optionalBook.get();

        book.setName(bookUpdateRequest.getName());
        book.setInternationalStandardBookNumber(bookUpdateRequest.getInternationalStandardBookNumber());
        book.setPageCount(bookUpdateRequest.getPageCount());
        book.setAuthor(author.getData().get());
        book.setImage(image.getData().get());
        book.setBookType(bookType.getData().get());
        this.bookRepository.save(book);
        return  new SuccessDataResult<>(book,"Kitap Güncellendi");


    }

    @Override
    public Result delete(String id) {
        Optional<Book> book = this.bookRepository.findById(id);
        if(!book.isPresent()){
            return  new ErrorResult("Kitap Bulunamadı");
        }
        this.bookRepository.deleteById(id);
        return  new SuccessResult("Kitap Başarıyla Silindi");
    }

    @Override
    public DataResult<List<BookViewRequest>> getAll() {
        return new SuccessDataResult<List<BookViewRequest>>(
                this.bookRepository.findAll().stream().map(BookViewRequest::of)
                        .collect(Collectors.toList()));

    }

    @Override
    public DataResult<Optional<Book>> findById(String id) {
        if(this.bookRepository.findById(id) == null){
            return  new ErrorDataResult<>("Kitap Bulunamadı");
        }
        return  new SuccessDataResult<>(this.bookRepository.findById(id),"Kitap Bulundu");
    }


}
