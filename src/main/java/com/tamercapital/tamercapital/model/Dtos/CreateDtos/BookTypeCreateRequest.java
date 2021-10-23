package com.tamercapital.tamercapital.model.Dtos.CreateDtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class BookTypeCreateRequest {


    @NotBlank
    @NotNull
    private String typeName;
}
