package com.tamercapital.tamercapital;

import com.tamercapital.tamercapital.business.abstracts.AuthorService;
import com.tamercapital.tamercapital.business.abstracts.BookTypeService;
import com.tamercapital.tamercapital.business.abstracts.ImageService;
import com.tamercapital.tamercapital.business.concretes.BookManager;
import com.tamercapital.tamercapital.model.Dtos.CreateDtos.BookCreateRequest;
import com.tamercapital.tamercapital.model.concretes.Author;
import com.tamercapital.tamercapital.model.concretes.Book;
import com.tamercapital.tamercapital.model.concretes.BookType;
import com.tamercapital.tamercapital.model.concretes.Image;
import com.tamercapital.tamercapital.repository.BookRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.MockedConstructionImpl;

import java.util.Optional;

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

        Author author = Author.builder()
                .id("6171248a15e6ef1e23398821")
                .firstName("Serhat")
                .lastName("Biricik")
                .build();

        Image image = Image.builder()
                .id("6171248a15e6ef1e23398234")
                .imageName("Jpeg")
                .deleteId("23132123123")
                .imageUrl("https://res.cloudinary.com/dmeviw9q7/image/upload/v1628713288/hblb3uxvprsvrchp3xbx.jpg")
                .build();


        BookType bookType = BookType.builder()
                .id("123231")
                .typeName("Roman")
                .build();

        Book book = Book.builder()
                .id("324342")
                .name(bookCreateRequest.getName())
                .pageCount(bookCreateRequest.getPageCount())
                .internationalStandardBookNumber(bookCreateRequest.getInternationalStandardBookNumber())
                .author(author)
                .bookType(bookType)
                .ımage(image)
                .build();



        Mockito.when(authorService.findById("6171248a15e6ef1e23398821")).thenReturn(Optional.of(author));
        Mockito.when(bookRepository.save(book)).thenReturn(book);
        Mockito.when(imageService.findById("6172ca419ea9282a410d1354")).thenReturn(Optional.of(image));
        Mockito.when(bookTypeService.findById("6171a427da6f7d3c18cb8d03")).thenReturn(Optional.of(bookType));



    }

}
