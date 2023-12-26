package com.redeyesncode.estatespring.realestatebackend.service;


import com.redeyesncode.estatespring.realestatebackend.jwt.JwtSecretKey;
import com.redeyesncode.estatespring.realestatebackend.models.*;
import com.redeyesncode.estatespring.realestatebackend.repository.UserTableRepo;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserTableRepo userTableRepo;

    private String jwtSecret = "springjwt";
    private long jwtExpirationMs = 3600000; // 1 hour in milliseconds

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
    public ResponseEntity<?> updateUserProfile(UserUpdateDTO userDto) {
        // Extract necessary information from DTO
        int userId = userDto.getUserId(); // Assuming the DTO contains userId for identification
        String fullName = userDto.getFullName();
        String email = userDto.getEmail();
        String telephoneNumber = userDto.getTelephoneNumber();
        String postcode = userDto.getPostcode();
        String password = userDto.getPassword();

        // Perform validations or business logic here if needed

        // Check if the user exists based on userId
        Optional<UserTable> optionalUser = userTableRepo.findById((long) userId);
        if (optionalUser.isPresent()) {
            UserTable existingUser = optionalUser.get();

            // Update user details
            existingUser.setFullName(fullName);
            existingUser.setEmail(email);
            existingUser.setTelephoneNumber(telephoneNumber);
            existingUser.setPostcode(postcode);
            existingUser.setPassword(password);
            existingUser.setUserName(userDto.getUserName());

            // Update timestamps (if needed)
            existingUser.setUpdatedAt(String.valueOf(LocalDateTime.now()));

            // Save the updated user to the database
            try {
                UserTable updatedUser = userTableRepo.save(existingUser);
                return ResponseEntity.ok(new CustomStatusCodeModel("200", 200, "User profile updated", updatedUser));
            } catch (Exception e) {
                return BadResponseMessage("Failed to update user profile");
            }
        } else {
            return BadResponseMessage("User not found");
        }
    }




    public ResponseEntity<?> loginUser(HashMap<String, String> loginMap) {
        String mail = loginMap.get("mail");
        String password = loginMap.get("password");
        String number = loginMap.get("telephoneNumber");
        UserTable checkUserLogin = userTableRepo.findByEmailAndTelephoneNumberAndPassword(mail,number,password);
        if(checkUserLogin!=null){
            LoginJwtResponse loginJwtResponse = new LoginJwtResponse();
            loginJwtResponse.setCode(200);
            loginJwtResponse.setUser(checkUserLogin);
            loginJwtResponse.setJWT(generateJwtToken(checkUserLogin.getEmail()));
            loginJwtResponse.setMessage("Login Successfully ");
            loginJwtResponse.setStatus("200 OK");

            return ResponseEntity.ok(loginJwtResponse);
        }else {
            return BadResponseMessage("User not found !");
        }

    }
    private String generateJwtToken(String email) {
        Date expirationDate = new Date(System.currentTimeMillis() + jwtExpirationMs);
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

// Convert the key to a string (for storage, e.g., in application properties)
        String encodedKey = Base64.getEncoder().encodeToString(key.getEncoded());
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, JwtSecretKey.getSecretKey())
                .compact();
    }

    public ResponseEntity<?> checkUsername(HashMap<String, String> hashMap) {

        if(userTableRepo.existsByUsername(hashMap.get("username"))){
            return BadResponseMessage("Username already exists !");
        }else {
            return SuccessResponseMessage("Username available !");
        }
    }
}
