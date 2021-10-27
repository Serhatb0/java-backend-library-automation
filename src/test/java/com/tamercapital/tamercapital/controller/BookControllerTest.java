package com.tamercapital.tamercapital.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.tamercapital.tamercapital.business.abstracts.BookService;
import com.tamercapital.tamercapital.model.Dtos.CreateDtos.BookCreateRequest;
import com.tamercapital.tamercapital.model.Dtos.UpdateDtos.BookUpdateRequest;
import com.tamercapital.tamercapital.model.Dtos.ViewDtos.BookViewRequest;
import com.tamercapital.tamercapital.model.concretes.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
public class BookControllerTest {

    private final static String CONTENT_TYPE = "application/json";


    @Autowired()
    private MockMvc mockMvc;

    @Autowired()
    private ObjectMapper objectMapper;

    @MockBean
    private BookService bookService;


    @Test
    public void shouldCreateNewBook() throws Exception {

        BookCreateRequest request = new BookCreateRequest();
        request.setName("The 100");
        request.setInternationalStandardBookNumber("12123");
        request.setId("1234");
        request.setPageCount("333");
        request.setTypeId("12345");
        request.setImageId("213123");
        request.setAuthorId("21323");

        ResultActions actions = this.mockMvc.perform(post("/api/book/add")
                        .contentType(CONTENT_TYPE)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        ArgumentCaptor<BookCreateRequest> captor = ArgumentCaptor.forClass(BookCreateRequest.class);
        verify(bookService, times(1)).add(captor.capture());
        assertThat(captor.getValue().getName()).isEqualTo("The 100");
        assertThat(captor.getValue().getAuthorId()).isEqualTo("21323");
        assertThat(captor.getValue().getPageCount()).isEqualTo("333");


        actions.andExpect(status().isOk());


    }

    @Test
    public void shouldUpdateBook_isUnauthorized() throws Exception {
        String id = "1234";
        BookUpdateRequest request = new BookUpdateRequest(id, "Simyacı", "300","123344","12123","12321","123213");


        ResultActions actions = this.mockMvc.perform(put("/api/book/{bookId}", id)
                        .contentType(CONTENT_TYPE)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());
    }


    @Test
    public void shouldDeleteBook() throws Exception {
        String id = "12345";
        Book book = new Book();
        given(bookService.findById(id)).willReturn(Optional.of(book));
        doNothing().when(bookService).delete(book.getId());

        this.mockMvc.perform(delete("/api/book/delete/{bookId}", book.getId())
                        .contentType(CONTENT_TYPE)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void whenCallListAll_Book_thenReturns200() throws Exception {
        // given
        BookViewRequest request = BookViewRequest.builder()
                .id("12345")
                .authorName("Serhat")
                .bookTypeName("Roman")
                .imageUrl("dasdsadasd")
                .internationalStandardBookNumber("12345678")
                .name("The 100")
                .pageCount("333")
                .build();
        when(bookService.getAll()).thenReturn(Arrays.asList(request));

        // when
        MvcResult mvcResult = mockMvc.perform(get("/api/book")
                .accept(CONTENT_TYPE)).andReturn();


        // then
        String responseBody = mvcResult.getResponse().getContentAsString();
        verify(bookService, times(1)).getAll();
        assertThat(objectMapper.writeValueAsString(Arrays.asList(request)))
                .isEqualToIgnoringWhitespace(responseBody);
    }



}
