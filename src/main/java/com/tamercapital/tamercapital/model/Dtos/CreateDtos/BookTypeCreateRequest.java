package com.tamercapital.tamercapital.model.Dtos.CreateDtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class BookTypeCreateRequest  {


    private  String id;
    @NotBlank
    @NotNull
    private String typeName;
}
