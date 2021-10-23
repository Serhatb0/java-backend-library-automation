package com.tamercapital.tamercapital.model.Dtos.ViewDtos;

import com.tamercapital.tamercapital.model.concretes.Author;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorViewRequest {


    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String ımageUrl;


    public  static  AuthorViewRequest of(Author author){
        return  new AuthorViewRequest(author.getId(),author.getFirstName(),
                author.getLastName(),author.getImage().getImageUrl());
    }

}
