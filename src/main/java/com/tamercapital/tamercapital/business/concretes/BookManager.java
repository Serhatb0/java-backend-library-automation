package com.tamercapital.tamercapital.business.concretes;

import com.tamercapital.tamercapital.business.abstracts.AuthorService;
import com.tamercapital.tamercapital.business.abstracts.BookService;
import com.tamercapital.tamercapital.business.abstracts.BookTypeService;
import com.tamercapital.tamercapital.business.abstracts.ImageService;
import com.tamercapital.tamercapital.exception.EntityNotFoundException;
import com.tamercapital.tamercapital.model.BookFilter;
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
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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
    private final MongoTemplate mongoTemplate;

    @Autowired
    public BookManager(BookRepository bookRepository, AuthorService authorService, ImageService imageService, BookTypeService bookTypeService, MongoTemplate mongoTemplate) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.imageService = imageService;
        this.bookTypeService = bookTypeService;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Book add(BookCreateRequest bookCreateRequest) {
        Optional<Author> author = this.authorService.findById(bookCreateRequest.getAuthorId());
        Optional<Image> image = this.imageService.findById(bookCreateRequest.getImageId());
        Optional<BookType> bookType = this.bookTypeService.findById(bookCreateRequest.getTypeId());

        Book book = new Book();
        BeanUtils.copyProperties(bookCreateRequest,book);
        book.setAuthor(author.get());
        book.setImage(image.get());
        book.setBookType(bookType.get());
         return  this.bookRepository.save(book);

    }

    @Override
    public Book update(String id, BookUpdateRequest bookUpdateRequest) {
        Optional<Author> author = this.authorService.findById(bookUpdateRequest.getAuthorId());
        Optional<Image> image = this.imageService.findById(bookUpdateRequest.getImageId());
        Optional<BookType> bookType = this.bookTypeService.findById(bookUpdateRequest.getTypeId());
        Optional<Book> optionalBook = this.bookRepository.findById(id);

        Book book = optionalBook.get();

        book.setName(bookUpdateRequest.getName());
        book.setInternationalStandardBookNumber(bookUpdateRequest.getInternationalStandardBookNumber());
        book.setPageCount(bookUpdateRequest.getPageCount());
        book.setAuthor(author.get());
        book.setImage(image.get());
        book.setBookType(bookType.get());
        return this.bookRepository.save(book);



    }

    @Override
    public void delete(String id) {
        Optional<Book> optionalBook = this.bookRepository.findById(id);

        if(optionalBook.isPresent()){
            throw  new EntityNotFoundException("Kitap Bulunamadı");
        }
        this.bookRepository.deleteById(id);
    }

    @Override
    public List<BookViewRequest> getAll() {
        return this.bookRepository.findAll().stream().map(BookViewRequest::of)
                        .collect(Collectors.toList());

    }

    @Override
    public Optional<Book> findById(String id) {
        if(this.bookRepository.findById(id) == null){
            throw  new EntityNotFoundException("Kitap Bulunamadı");
        }
        return this.bookRepository.findById(id);
    }



    @Override
    public List<Book> findByBookName(BookFilter bookFilter) {
        Query query = new Query();
        Criteria criteria = new Criteria();

        criteria.orOperator(Criteria.where("author.id").is(bookFilter.getAuthorId()),
        Criteria.where("bookType.id").is(bookFilter.getBookTypeId()),Criteria.where("name").is(bookFilter.getName()));

        query.addCriteria(criteria);

        return  mongoTemplate.find(query,Book.class);
    }



}
