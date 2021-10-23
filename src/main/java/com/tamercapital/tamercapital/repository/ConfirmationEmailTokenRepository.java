package com.tamercapital.tamercapital.repository;

import com.tamercapital.tamercapital.model.concretes.ConfirmationEmailToken;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfirmationEmailTokenRepository extends MongoRepository<ConfirmationEmailToken,String> {
    ConfirmationEmailToken  findByConfirmationToken(String confirmationToken);
}
