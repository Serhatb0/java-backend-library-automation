package com.tamercapital.tamercapital.model.concretes;

import com.tamercapital.tamercapital.model.concretes.Author;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "books")
public class Book {
    @Id
    private String id;
    private String name;
    private String internationalStandardBookNumber;
    private String pageCount;
    @DBRef
    private Author author;

    @DBRef
    private Image ımage;

    @DBRef
    private BookType bookType;

}
