package com.tamercapital.tamercapital.business.abstracts;

import com.tamercapital.tamercapital.model.Dtos.CreateDtos.LoginRequest;
import com.tamercapital.tamercapital.model.Dtos.CreateDtos.SignupRequest;
import com.tamercapital.tamercapital.model.concretes.User;

import javax.servlet.http.HttpSession;
import java.util.Optional;

public interface UserService {

    User register(SignupRequest signupRequest);

    String login(LoginRequest loginRequest);

    Optional<User> findById(String id);

    User findByEmailIgnoreCase(String email);

     void logout(HttpSession session);

}
