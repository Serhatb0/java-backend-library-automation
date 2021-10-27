package com.tamercapital.tamercapital.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;

@RestController
public class SpringSessionController {


    @GetMapping("/viewSessionData")
    public ResponseEntity<?> start(HttpSession session) {
        Set<Object> data = new HashSet<Object>();
        data.add(session.getAttribute("username"));
        data.add(session.getAttribute("password"));

        return  ResponseEntity.ok(data);


    }
}
