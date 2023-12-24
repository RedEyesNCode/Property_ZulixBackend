package com.redeyesncode.estatespring.realestatebackend.service;


import com.redeyesncode.estatespring.realestatebackend.models.CustomStatusCodeModel;
import com.redeyesncode.estatespring.realestatebackend.models.StatusCodeModel;
import com.redeyesncode.estatespring.realestatebackend.models.UserRegistrationDTO;
import com.redeyesncode.estatespring.realestatebackend.models.UserTable;
import com.redeyesncode.estatespring.realestatebackend.repository.UserTableRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class UserService {

    @Autowired
    private UserTableRepo userTableRepo;

    private static final ResponseEntity<?> SUCCESS_RESPONSE = ResponseEntity.ok(new StatusCodeModel("200",200,"Success"));
    private static final ResponseEntity<?> BAD_RESPONSE = ResponseEntity.badRequest().body(new StatusCodeModel("400",400,"Fail"));


    public static ResponseEntity<?> BadResponseMessage(String message){
        return ResponseEntity.badRequest().body(new StatusCodeModel("400",400,message));

    }

    public static ResponseEntity<?> SuccessResponseMessage(String message){
        return ResponseEntity.ok(new StatusCodeModel("200",200,message));

    }

    public ResponseEntity<?> registerUser(UserRegistrationDTO userDto) {
        // Extract necessary information from DTO

        String password = userDto.getPassword();
        String email = userDto.getEmail();

        String telephoneNumber = userDto.getTelephoneNumber();

        // Perform validations or business logic here if needed

        if(userTableRepo.existsByEmail(email)){
            return BadResponseMessage("Email Address already exists !");
        }else if(userTableRepo.existsByTelephoneNumber(telephoneNumber)){
            return BadResponseMessage("Number Already exists !");
        }else{
            // Create a UserTable entity from the DTO data
            UserTable newUser = new UserTable();
            newUser.setPassword(password);
            newUser.setEmail(email);
            newUser.setTelephoneNumber(telephoneNumber);

            // Save the user to the database
            try {
                UserTable currentSave = userTableRepo.save(newUser);
                return ResponseEntity.ok(new CustomStatusCodeModel("200",200,"User registered",currentSave));
            } catch (Exception e) {
                return BadResponseMessage("Failed to register user");
            }
        }



    }


    public ResponseEntity<?> loginUser(HashMap<String, String> loginMap) {
        String mail = loginMap.get("mail");
        String password = loginMap.get("password");
        String number = loginMap.get("telephoneNumber");
        UserTable checkUserLogin = userTableRepo.findByEmailAndTelephoneNumberAndPassword(mail,number,password);
        if(checkUserLogin!=null){

            return ResponseEntity.ok(new CustomStatusCodeModel("200",200,"Login  Success",checkUserLogin));
        }else {
            return BadResponseMessage("User not found !");
        }

    }
}
