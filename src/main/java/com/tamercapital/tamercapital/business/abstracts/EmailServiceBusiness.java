package com.tamercapital.tamercapital.business.abstracts;

import com.tamercapital.tamercapital.model.concretes.User;

public interface EmailServiceBusiness {

    void sendEmail(User user , String email);
    public  boolean isValid(String email);

    String confirmUserAccount(String confirmationToken);
}
