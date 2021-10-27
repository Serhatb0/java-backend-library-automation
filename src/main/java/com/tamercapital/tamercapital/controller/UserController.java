package com.tamercapital.tamercapital.controller;


import com.tamercapital.tamercapital.business.abstracts.EmailServiceBusiness;
import com.tamercapital.tamercapital.business.abstracts.UserService;
import com.tamercapital.tamercapital.model.Dtos.CreateDtos.LoginRequest;
import com.tamercapital.tamercapital.model.Dtos.CreateDtos.SignupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {
    private  final EmailServiceBusiness emailService;
    private final UserService userService;

    @Autowired
    public UserController(EmailServiceBusiness emailService, UserService userService) {
        this.emailService = emailService;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        return  ResponseEntity.ok(this.userService.register(signupRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest, HttpSession session) {

        return  ResponseEntity.ok(this.userService.login(loginRequest,session));
    }


    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/confirm-account", method = { RequestMethod.GET, RequestMethod.POST })
    public ResponseEntity<?> confirmUserAccount(@RequestParam("token") String confirmationToken) {
        return  ResponseEntity.ok(this.emailService.confirmUserAccount(confirmationToken));
    }



}
