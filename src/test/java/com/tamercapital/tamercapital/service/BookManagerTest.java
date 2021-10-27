package com.tamercapital.tamercapital.service;

import com.tamercapital.tamercapital.business.abstracts.AuthorService;
import com.tamercapital.tamercapital.business.abstracts.BookTypeService;
import com.tamercapital.tamercapital.business.abstracts.ImageService;
import com.tamercapital.tamercapital.business.concretes.BookManager;
import com.tamercapital.tamercapital.model.Dtos.CreateDtos.BookCreateRequest;
import com.tamercapital.tamercapital.model.Dtos.UpdateDtos.BookUpdateRequest;
import com.tamercapital.tamercapital.model.Dtos.ViewDtos.BookViewRequest;
import com.tamercapital.tamercapital.model.concretes.*;
import com.tamercapital.tamercapital.repository.BookRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.junit.Assert.assertEquals;

public class BookManagerTest {


    private BookManager bookManager;
    private BookRepository bookRepository;
    private AuthorService authorService;
    private ImageService imageService;
    private BookTypeService bookTypeService;


    @Before
    public void setUp() throws Exception {

        bookRepository = Mockito.mock(BookRepository.class);
        authorService = Mockito.mock(AuthorService.class);
        imageService = Mockito.mock(ImageService.class);
        bookTypeService = Mockito.mock(BookTypeService.class);


        bookManager = new BookManager(bookRepository, authorService, imageService, bookTypeService);


    }


    @Test
    public void testAdd() {
        BookCreateRequest bookCreateRequest = new BookCreateRequest();
        bookCreateRequest.setName("The 100");
        bookCreateRequest.setAuthorId("6176853236aaf34ea79c2051");
        bookCreateRequest.setImageId("6172ca419ea9282a410d1354");
        bookCreateRequest.setTypeId("6171a427da6f7d3c18cb8d03");
        bookCreateRequest.setPageCount("120");
        bookCreateRequest.setInternationalStandardBookNumber("1323");

        Author author = generateAuthor();

        Image image = generateImage();

        BookType bookType = generateBookType();

        Book book = Book.builder()
                .id("324342")
                .name(bookCreateRequest.getName())
                .pageCount(bookCreateRequest.getPageCount())
                .internationalStandardBookNumber(bookCreateRequest.getInternationalStandardBookNumber())
                .author(author)
                .bookType(bookType)
                .ımage(image)
                .build();


        when(authorService.findById("6171248a15e6ef1e23398821")).thenReturn(Optional.of(author));
        when(bookRepository.save(book)).thenReturn(book);
        when(imageService.findById("6172ca419ea9282a410d1354")).thenReturn(Optional.of(image));
        when(bookTypeService.findById("6171a427da6f7d3c18cb8d03")).thenReturn(Optional.of(bookType));


    }

    @Test
    public void getAllBookTest() {
        BookCreateRequest bookCreateRequest = new BookCreateRequest();
        Author author = generateAuthor();

        Image image = generateImage();

        BookType bookType = generateBookType();


        List<Book> result = new ArrayList<>();
        result.add(new Book("1234", "Şeker Portakalı", "255", "454647", author, image, bookType));
        result.add(new Book("12345", "Simyacı", "278", "564948", author, image, bookType));

        result.add(new Book("123456", "Suç Ve Ceza", "350", "878896", author, image, bookType));

        when(bookRepository.findAll()).thenReturn(result);


        List<BookViewRequest> expected = bookManager.getAll();

        assertEquals(3, expected.size());
        assertEquals(false, expected.isEmpty());
        verify(bookRepository, times(1)).findAll();


    }

    @Test
    public void getBookByIdTest() {

        Author author = generateAuthor();

        Image image = generateImage();

        BookType bookType = generateBookType();

        final String id = "123456";
        final Book book = new Book("123456", "Suç Ve Ceza", "350", "878896", author, image, bookType);


        given(bookRepository.findById(id)).willReturn(Optional.of(book));

        final Optional<Book> expected = bookManager.findById(id);

        assertThat(expected).isNotNull();

    }

    @Test(expected = RuntimeException.class)
    public void updateBookTest() {
        String id = "1";
        Author author = generateAuthor();

        Image image = generateImage();

        BookType bookType = generateBookType();

        final Book book = new Book("1234", "Şeker Portakalı", "255", "454647", author, image, bookType);
        final BookUpdateRequest bookUpdateRequest = new BookUpdateRequest("1234", "Şeker Portakalı", "255", "454647", "6171248a15e6ef1e23398821", "6171248a15e6ef1e23398234", "123231");
        given(bookRepository.save(book)).willReturn(book);

        final Book expected = bookManager.update(book.getId(), bookUpdateRequest);
        assertThat(expected).isNotNull();
        verify(bookRepository).save(any(Book.class));

    }


    @Test
    public void deleteBookTest() {


        Author author = generateAuthor();

        Image image = generateImage();

        BookType bookType = generateBookType();


        Book book = new Book();
        book.setId("12345");
        book.setName("Şeker Portakalı");
        book.setPageCount("300");
        book.setInternationalStandardBookNumber("231231");
        book.setAuthor(author);
        book.setBookType(bookType);
        book.setImage(image);

        Mockito.doNothing().when(bookRepository).deleteById(book.getId());
        bookManager.delete(book.getId());
        Mockito.verify(bookRepository, Mockito.times(1)).deleteById(book.getId());


    }


    private BookType generateBookType() {
        return BookType.builder()
                .id("123231")
                .typeName("Roman")
                .build();
    }

    private Image generateImage() {
        return Image.builder()
                .id("6171248a15e6ef1e23398234")
                .imageName("Jpeg")
                .deleteId("23132123123")
                .imageUrl("https://res.cloudinary.com/dmeviw9q7/image/upload/v1628713288/hblb3uxvprsvrchp3xbx.jpg")
                .build();
    }

    private Author generateAuthor() {
        return Author.builder()
                .id("6171248a15e6ef1e23398821")
                .firstName("Serhat")
                .lastName("Biricik")
                .build();
    }


}
