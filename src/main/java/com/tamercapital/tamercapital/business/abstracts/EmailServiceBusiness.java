package com.tamercapital.tamercapital.business.abstracts;

import com.tamercapital.tamercapital.core.utilities.Result;
import com.tamercapital.tamercapital.model.concretes.User;

public interface EmailServiceBusiness {

    Result sendEmail(User user , String email);
    public  boolean isValid(String email);

    Result confirmUserAccount(String confirmationToken);
}
