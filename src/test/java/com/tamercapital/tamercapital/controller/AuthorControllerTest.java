package com.tamercapital.tamercapital.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tamercapital.tamercapital.business.abstracts.AuthorService;
import com.tamercapital.tamercapital.model.Dtos.CreateDtos.AuthorCreateRequest;
import com.tamercapital.tamercapital.model.Dtos.UpdateDtos.AuthorUpdateRequest;
import com.tamercapital.tamercapital.model.Dtos.ViewDtos.AuthorViewRequest;
import com.tamercapital.tamercapital.model.concretes.Author;
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

import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
public class AuthorControllerTest {

    private final static String CONTENT_TYPE = "application/json";


    @Autowired()
    private MockMvc mockMvc;

    @Autowired()
    private ObjectMapper objectMapper;

    @MockBean
    private AuthorService authorService;


    @Test
    public void shouldCreateNewAuthor() throws Exception {
        AuthorCreateRequest request = new AuthorCreateRequest();
        request.setFirstName("Serhat");
        request.setLastName("Biricik");
        request.setId("1234");

        ResultActions actions = this.mockMvc.perform(post("/api/author/add")
                        .contentType(CONTENT_TYPE)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        ArgumentCaptor<AuthorCreateRequest> captor = ArgumentCaptor.forClass(AuthorCreateRequest.class);
        verify(authorService, times(1)).add(captor.capture());
        assertThat(captor.getValue().getFirstName()).isEqualTo("Serhat");
        assertThat(captor.getValue().getLastName()).isEqualTo("Biricik");

        actions.andExpect(status().isOk());


    }

    @Test
    public void shouldUpdateAuthor() throws Exception {
        String id = "1234";
        AuthorUpdateRequest request = new AuthorUpdateRequest(id, "Serhat", "Biricik");


        ResultActions actions = this.mockMvc.perform(put("/api/author/{authorId}", id)
                        .contentType(CONTENT_TYPE)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldDeleteAuthor() throws Exception {
        String id = "12345";
        Author author = new Author(id, "Serhat", "Biricik");
        given(authorService.findById(id)).willReturn(Optional.of(author));
        doNothing().when(authorService).delete(author.getId());

        this.mockMvc.perform(delete("/api/author/delete/{authorId}", author.getId())
                        .contentType(CONTENT_TYPE)
                        .content(objectMapper.writeValueAsString(author)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void whenCallListAll_Author_thenReturns200() throws Exception {
        // given
        AuthorViewRequest request = AuthorViewRequest.builder()
                .id("12345")
                .firstName("Serhat")
                .lastName("Biricik")
                .build();
        when(authorService.getAll()).thenReturn(Arrays.asList(request));

        // when
        MvcResult mvcResult = mockMvc.perform(get("/api/author")
                .accept(CONTENT_TYPE)).andReturn();


        // then
        String responseBody = mvcResult.getResponse().getContentAsString();
        verify(authorService, times(1)).getAll();
        assertThat(objectMapper.writeValueAsString(Arrays.asList(request)))
                .isEqualToIgnoringWhitespace(responseBody);
    }

}
