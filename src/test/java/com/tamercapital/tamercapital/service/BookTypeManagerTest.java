package com.tamercapital.tamercapital.service;

import com.tamercapital.tamercapital.business.concretes.BookTypeManager;
import com.tamercapital.tamercapital.model.Dtos.CreateDtos.BookTypeCreateRequest;
import com.tamercapital.tamercapital.model.Dtos.UpdateDtos.BookTypeUpdateRequest;
import com.tamercapital.tamercapital.model.Dtos.ViewDtos.BookTypeViewRequest;
import com.tamercapital.tamercapital.model.concretes.BookType;
import com.tamercapital.tamercapital.repository.BookTypeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class BookTypeManagerTest {

    private BookTypeManager bookTypeManager;
    private BookTypeRepository bookTypeRepository;

    @Before
    public void setUp() throws Exception {

        bookTypeRepository = Mockito.mock(BookTypeRepository.class);


        bookTypeManager = new BookTypeManager(bookTypeRepository);

    }

    @Test
    public void addBookTypeTest() {
        final BookType bookType = new BookType("1234", "Roman");
        final BookTypeCreateRequest bookTypeCreateRequest = new BookTypeCreateRequest("1234", "Roman");
        given(bookTypeRepository.findById(bookType.getId())).willReturn(Optional.empty());
        given(bookTypeRepository.save(bookType)).willAnswer(invocation -> invocation.getArgument(0));

        BookType savedBookTyped = bookTypeManager.add(bookTypeCreateRequest);
        assertThat(savedBookTyped).isNotNull();
        verify(bookTypeRepository).save(any(BookType.class));
    }

    @Test(expected = RuntimeException.class)
    public void updateBookTypeTest() {
        final BookType bookType = new BookType("1234", "Roman");
        final BookTypeUpdateRequest bookTypeUpdateRequest = new BookTypeUpdateRequest("1234", "Roman");

        given(bookTypeRepository.save(bookType)).willReturn(bookType);

        final BookType expected = bookTypeManager.update(bookType.getId(), bookTypeUpdateRequest);
        assertThat(expected).isNotNull();
        verify(bookTypeRepository).save(any(BookType.class));
    }

    @Test
    public void getAllBookTypeTest() {
        BookTypeViewRequest bookTypeViewRequest = new BookTypeViewRequest();

        List<BookType> result = new ArrayList<>();
        result.add(new BookType("1234", "Roman"));
        result.add(new BookType("1234", "Roman"));

        when(bookTypeRepository.findAll()).thenReturn(result);

        List<BookTypeViewRequest> expected = bookTypeManager.getAll();
        assertEquals(2, expected.size());
        assertEquals(false, expected.isEmpty());
        verify(bookTypeRepository, times(1)).findAll();

    }

    @Test
    public void getBookTypeByIdTest() {


        final String id = "123456";
        final BookType bookType = new BookType("123456", "Roman");


        given(bookTypeRepository.findById(id)).willReturn(Optional.of(bookType));

        final Optional<BookType> expected = bookTypeManager.findById(id);

        assertThat(expected).isNotNull();

    }

    @Test
    public void deleteBookTypeTest() {


        BookType bookType = new BookType();
        bookType.setId("1");
        bookType.setTypeName("Roman");


        Mockito.doNothing().when(bookTypeRepository).deleteById(bookType.getId());
        bookTypeManager.delete(bookType.getId());
        Mockito.verify(bookTypeRepository, Mockito.times(1)).deleteById(bookType.getId());


    }
}
