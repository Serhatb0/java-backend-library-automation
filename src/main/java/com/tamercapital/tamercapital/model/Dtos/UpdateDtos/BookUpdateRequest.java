package com.tamercapital.tamercapital.model.Dtos.UpdateDtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class BookUpdateRequest {

    private String id;

    @NotBlank
    @NotNull
    private String name;
    @NotBlank
    @NotNull
    private String pageCount;
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


}
