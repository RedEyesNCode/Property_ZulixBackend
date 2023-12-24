package com.redeyesncode.estatespring.realestatebackend.controllers;


import com.redeyesncode.estatespring.realestatebackend.models.UserRegistrationDTO;
import com.redeyesncode.estatespring.realestatebackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/spring-property")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register-user")
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationDTO registerUserDTO){

        return userService.registerUser(registerUserDTO);

    }

    @PostMapping("/login-user")
    public ResponseEntity<?> loginUser(@RequestBody HashMap<String,String> loginMap){


        return userService.loginUser(loginMap);
    }

}
