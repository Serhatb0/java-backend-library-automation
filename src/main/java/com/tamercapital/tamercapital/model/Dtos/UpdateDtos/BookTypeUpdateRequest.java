package com.tamercapital.tamercapital.model.Dtos.UpdateDtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class BookTypeUpdateRequest {

    @NotBlank
    @NotNull
    private String typeName;
}
