package com.tamercapital.tamercapital.core.security.response;


import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class JwtResponse implements Serializable {
    private String token;
    private String type = "Bearer";
    private String id;
    private String username;
    private String email;
    private List<String> roles;

    public JwtResponse(String accessToken, String id, String username, String email, List<String> roles) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }

    public JwtResponse(String accessToken,  String username, String email, List<String> roles) {
        this.token = accessToken;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }



}
