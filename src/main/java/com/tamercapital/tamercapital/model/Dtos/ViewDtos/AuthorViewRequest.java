package com.tamercapital.tamercapital.model.Dtos.ViewDtos;

import com.tamercapital.tamercapital.model.concretes.Author;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthorViewRequest {


    @Id
    private String id;
    private String firstName;
    private String lastName;



    public  static  AuthorViewRequest of(Author author){
        return  new AuthorViewRequest(author.getId(),author.getFirstName(),
                author.getLastName());
    }

}
