package com.tamercapital.tamercapital.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.tamercapital.tamercapital.business.abstracts.AuthorService;
import com.tamercapital.tamercapital.business.abstracts.UserService;
import com.tamercapital.tamercapital.model.Dtos.CreateDtos.AuthorCreateRequest;
import com.tamercapital.tamercapital.model.Dtos.CreateDtos.LoginRequest;
import com.tamercapital.tamercapital.model.Dtos.CreateDtos.SignupRequest;
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

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
public class UserControllerTest {



    private final static String CONTENT_TYPE = "application/json";


    @Autowired()
    private MockMvc mockMvc;

    @Autowired()
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;


    @Test
    public void shouldCreateNewUser() throws Exception {
        SignupRequest request = new SignupRequest();
        Set roles = new HashSet<>();
        roles.add("user");
        request.setEmail("biricik47@gmail.com");
        request.setPassword("12345678");
        request.setUsername("serhat");
        request.setRoles(roles);

        ResultActions actions = this.mockMvc.perform(post("/api/user/register")
                        .contentType(CONTENT_TYPE)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        ArgumentCaptor<SignupRequest> captor = ArgumentCaptor.forClass(SignupRequest.class);
        verify(userService, times(1)).register(captor.capture());
        assertThat(captor.getValue().getEmail()).isEqualTo("biricik47@gmail.com");
        assertThat(captor.getValue().getUsername()).isEqualTo("serhat");
        actions.andExpect(status().isOk());


    }


    @Test
    public void shouldLoginUser() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setPassword("12345678");
        request.setUsername("serhat");

        ResultActions actions = this.mockMvc.perform(post("/api/user/login")
                        .contentType(CONTENT_TYPE)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        ArgumentCaptor<LoginRequest> captor = ArgumentCaptor.forClass(LoginRequest.class);
        verify(userService, times(1)).login(captor.capture());
        assertThat(captor.getValue().getUsername()).isEqualTo("serhat");
        assertThat(captor.getValue().getPassword()).isEqualTo("12345678");
        actions.andExpect(status().isOk());


    }

}
