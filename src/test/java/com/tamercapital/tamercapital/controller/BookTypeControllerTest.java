package com.tamercapital.tamercapital.controller;


import com.fasterxml.jackson.databind.ObjectMapper;


import com.tamercapital.tamercapital.business.abstracts.BookTypeService;
import com.tamercapital.tamercapital.model.Dtos.CreateDtos.BookTypeCreateRequest;
import com.tamercapital.tamercapital.model.concretes.BookType;
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
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
public class BookTypeControllerTest {

    private final static String CONTENT_TYPE = "application/json";


    @Autowired()
    private MockMvc mockMvc;

    @Autowired()
    private ObjectMapper objectMapper;

    @MockBean
    private BookTypeService bookTypeService;


    @Test
    public void shouldCreateNewBookType() throws Exception {
        BookTypeCreateRequest request = new BookTypeCreateRequest();
        request.setTypeName("Roman");
        ResultActions actions = this.mockMvc.perform(post("/api/bookType/add")
                        .contentType(CONTENT_TYPE)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        ArgumentCaptor<BookTypeCreateRequest> captor = ArgumentCaptor.forClass(BookTypeCreateRequest.class);
        verify(bookTypeService, times(1)).add(captor.capture());
        assertThat(captor.getValue().getTypeName()).isEqualTo("Roman");
        actions.andExpect(status().isOk());
    }


    @Test
    public void bookTypeByIdGet_noAuth_returnsUnauthorized() throws Exception {

        final BookType bookType = new BookType("1234", "Roman");


        this.mockMvc.perform(get("/api/bookType/{id}", "1234")
                        .content(CONTENT_TYPE)
                        .content(objectMapper.writeValueAsString(bookType)))
                .andExpect(status().isUnauthorized());
    }
}
