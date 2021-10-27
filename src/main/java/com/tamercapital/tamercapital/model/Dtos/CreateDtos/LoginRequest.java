package com.tamercapital.tamercapital.model.Dtos.CreateDtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@AllArgsConstructor
@Data
@Builder
public class LoginRequest  {
    private String username;

    private String password;
}
