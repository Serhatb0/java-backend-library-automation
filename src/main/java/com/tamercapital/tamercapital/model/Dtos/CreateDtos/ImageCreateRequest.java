package com.tamercapital.tamercapital.model.Dtos.CreateDtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class ImageCreateRequest {

    @NotBlank
    @NotNull
    private String imageName;
    @NotBlank
    @NotNull
    private String imageUrl;
    @NotBlank
    @NotNull
    private String deleteId;


}
