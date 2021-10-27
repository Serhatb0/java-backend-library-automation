package com.tamercapital.tamercapital.model.Dtos.UpdateDtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class BookTypeUpdateRequest {

    private String id;
    @NotBlank
    @NotNull
    private String typeName;
}
