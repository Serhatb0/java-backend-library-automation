package com.tamercapital.tamercapital.model.concretes;

import com.tamercapital.tamercapital.model.concretes.Author;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
