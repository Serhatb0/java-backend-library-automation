package com.tamercapital.tamercapital.model.concretes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "images")
public class Image {

    @Id
    private String id;

    private String imageName;

    private String imageUrl;

    private String deleteId;

}
