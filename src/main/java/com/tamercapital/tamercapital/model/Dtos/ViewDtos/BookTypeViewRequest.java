package com.tamercapital.tamercapital.model.Dtos.ViewDtos;

import com.tamercapital.tamercapital.model.concretes.BookType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookTypeViewRequest implements Serializable {

    private static final long serialVersionUID = 1L;


    private String typeName;


    public  static  BookTypeViewRequest of(BookType bookType){
        return  new BookTypeViewRequest(bookType.getTypeName());
    }
}
