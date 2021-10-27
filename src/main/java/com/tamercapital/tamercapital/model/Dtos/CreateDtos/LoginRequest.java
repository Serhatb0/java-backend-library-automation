package com.tamercapital.tamercapital.model.Dtos.CreateDtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LoginRequest  {
    private String username;

    private String password;
}
