package com.tamercapital.tamercapital.model.Dtos.UpdateDtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class BookUpdateRequest {

    @NotBlank
    @NotNull
    private String name;
    @NotBlank
    @NotNull
    private String internationalStandardBookNumber;
    @NotBlank
    @NotNull
    private String authorId;
    @NotBlank
    @NotNull
    private String ımageId;
    @NotBlank
    @NotNull
    private String typeId;

    @NotBlank
    @NotNull
    private String pageCount;
}
