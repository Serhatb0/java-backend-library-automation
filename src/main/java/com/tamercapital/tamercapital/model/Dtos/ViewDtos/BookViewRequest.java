package com.tamercapital.tamercapital.model.Dtos.ViewDtos;

import com.tamercapital.tamercapital.model.concretes.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class BookViewRequest implements Serializable {


    private static final long serialVersionUID = 1L;

    private String id;

    private String name;

    private  String pageCount;

    private String internationalStandardBookNumber;

    private String authorName;

    private String bookTypeName;

    private  String imageUrl;



    public  static  BookViewRequest of(Book book){
        return  new BookViewRequest(book.getId(),book.getName(),
                book.getPageCount(),book.getInternationalStandardBookNumber(),
                book.getAuthor().getFirstName(),book.getBookType().getTypeName(),book.getImage().getImageUrl());
    }


}
