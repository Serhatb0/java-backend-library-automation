package com.tamercapital.tamercapital.service;


import com.tamercapital.tamercapital.business.concretes.AuthorManager;
import com.tamercapital.tamercapital.model.Dtos.CreateDtos.AuthorCreateRequest;
import com.tamercapital.tamercapital.model.Dtos.UpdateDtos.AuthorUpdateRequest;
import com.tamercapital.tamercapital.model.Dtos.ViewDtos.AuthorViewRequest;
import com.tamercapital.tamercapital.model.concretes.Author;
import com.tamercapital.tamercapital.repository.AuthorRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AuthorManagerTest {

    private AuthorManager authorManager;
    private AuthorRepository authorRepository;

    @Before
    public void setUp() throws Exception {

        authorRepository = Mockito.mock(AuthorRepository.class);


        authorManager = new AuthorManager(authorRepository);


    }

    @Test
    public void addAuthorTest() {
        final Author author = new Author("1234", "Serhat", "Biricik");
        final AuthorCreateRequest authorCreateRequest = new AuthorCreateRequest("1234", "Serhat", "Biricik");
        given(authorRepository.findById(author.getId())).willReturn(Optional.empty());
        given(authorRepository.save(author)).willAnswer(invocation -> invocation.getArgument(0));

        Author savedAuthor = authorManager.add(authorCreateRequest);
        assertThat(savedAuthor).isNotNull();
        verify(authorRepository).save(any(Author.class));
    }

    @Test(expected = RuntimeException.class)
    public void updateAuthorTest() {
        final Author author = new Author("1234", "Serhat", "Biricik");
        final AuthorUpdateRequest authorUpdateRequest = new AuthorUpdateRequest("1234", "Serhat", "Biricik");

        given(authorRepository.save(author)).willReturn(author);

        final Author expected = authorManager.update(author.getId(), authorUpdateRequest);
        assertThat(expected).isNotNull();
        verify(authorRepository).save(any(Author.class));
    }

    @Test
    public void getAllAuthorTest() {
        AuthorViewRequest authorViewRequest = new AuthorViewRequest();

        List<Author> result = new ArrayList<>();
        result.add(new Author("1234", "Serhat", "Biricik"));
        result.add(new Author("1234", "Hakan", "Biricik"));

        when(authorRepository.findAll()).thenReturn(result);

        List<AuthorViewRequest> expected = authorManager.getAll();
        assertEquals(2, expected.size());
        assertEquals(false, expected.isEmpty());
        verify(authorRepository, times(1)).findAll();

    }

    @Test
    public void getAuthorByIdTest() {


        final String id = "123456";
        final Author author = new Author("123456", "Serhat", "biricik");


        given(authorRepository.findById(id)).willReturn(Optional.of(author));

        final Optional<Author> expected = authorManager.findById(id);

        assertThat(expected).isNotNull();

    }

    @Test
    public void deleteAuthorTest() {


        Author author = new Author();
        author.setId("1234");
        author.setFirstName("Serhat");
        author.setLastName("Biricik");

        Mockito.doNothing().when(authorRepository).deleteById(author.getId());
        authorManager.delete(author.getId());
        Mockito.verify(authorRepository, Mockito.times(1)).deleteById(author.getId());


    }


}
