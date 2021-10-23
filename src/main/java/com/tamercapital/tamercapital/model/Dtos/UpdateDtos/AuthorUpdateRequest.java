package com.tamercapital.tamercapital.model.Dtos.UpdateDtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AuthorUpdateRequest {

    @NotBlank
    @NotNull
    private String firstName;
    @NotBlank
    @NotNull
    private String lastName;

    @NotBlank
    @NotNull
    private String ımageId;
}
