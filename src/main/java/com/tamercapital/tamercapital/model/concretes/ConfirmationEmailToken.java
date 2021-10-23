package com.tamercapital.tamercapital.model.concretes;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "confirmation_email_token")
public class ConfirmationEmailToken {

    @Id
    private String id;

    private String confirmationToken;

    private Date createdDate;


    private User user;



    public ConfirmationEmailToken(User user) {
        this.user = user;
        createdDate = new Date();
        confirmationToken = UUID.randomUUID().toString();
    }
}
