package com.tamercapital.tamercapital.core.utilities;

import com.tamercapital.tamercapital.model.Dtos.CreateDtos.BookCreateRequest;

public class Result  {
    private  boolean success;
    private  String message;

    public  Result(boolean success){
        this.success =success;
    }

    public  Result(boolean success,String message){
        this(success);
        this.message =message;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public String getMessage() {
        return this.message;
    }



}