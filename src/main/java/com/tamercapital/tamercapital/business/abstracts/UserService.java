package com.tamercapital.tamercapital.business.abstracts;

import com.tamercapital.tamercapital.core.security.response.JwtResponse;
import com.tamercapital.tamercapital.core.utilities.DataResult;
import com.tamercapital.tamercapital.core.utilities.Result;
import com.tamercapital.tamercapital.model.Dtos.CreateDtos.LoginRequest;
import com.tamercapital.tamercapital.model.Dtos.CreateDtos.SignupRequest;
import com.tamercapital.tamercapital.model.concretes.User;

import java.util.Optional;

public interface UserService {

    Result register(SignupRequest signupRequest);

    DataResult<JwtResponse> login(LoginRequest loginRequest);

    DataResult<Optional<User>> findById(String id);

    DataResult<User> findByEmailIgnoreCase(String email);

}
