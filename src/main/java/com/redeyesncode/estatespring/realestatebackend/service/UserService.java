package com.redeyesncode.estatespring.realestatebackend.service;


import com.redeyesncode.estatespring.realestatebackend.jwt.JwtSecretKey;
import com.redeyesncode.estatespring.realestatebackend.models.*;
import com.redeyesncode.estatespring.realestatebackend.models.common.CustomStatusCodeModel;
import com.redeyesncode.estatespring.realestatebackend.models.common.LoginJwtResponse;
import com.redeyesncode.estatespring.realestatebackend.models.common.StatusCodeModel;
import com.redeyesncode.estatespring.realestatebackend.models.dto.ListingSearchCriteriaDTO;
import com.redeyesncode.estatespring.realestatebackend.models.dto.UserRegistrationDTO;
import com.redeyesncode.estatespring.realestatebackend.models.dto.UserUpdateDTO;
import com.redeyesncode.estatespring.realestatebackend.repository.NotificationRepo;
import com.redeyesncode.estatespring.realestatebackend.repository.UserListingRepo;
import com.redeyesncode.estatespring.realestatebackend.repository.UserTableRepo;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserTableRepo userTableRepo;

    @Autowired
    private NotificationRepo notificationRepo;


    private String jwtSecret = "springjwt";
    private long jwtExpirationMs = 3600000; // 1 hour in milliseconds

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UserListingRepo userListingRepo;


    public  List<UserListing> searchListingsByCriteria(ListingSearchCriteriaDTO criteria) {
        StringBuilder queryBuilder = new StringBuilder("SELECT ul FROM UserListing ul WHERE 1=1");

        if (criteria.getListingType() != null ) {
            queryBuilder.append(" AND ul.listingType = :listingType");
        }
        if (criteria.getPostalCode() != null && !criteria.getPostalCode().isEmpty()) {
            queryBuilder.append(" AND ul.address.postalCode = :postalCode");
        }
        if (criteria.getCity() != null && !criteria.getCity().isEmpty()) {
            queryBuilder.append(" AND ul.address.city = :city");
        }
        if (criteria.getTown() != null && !criteria.getTown().isEmpty()) {
            queryBuilder.append(" AND ul.address.town = :town");
        }
        if (criteria.getNumberOfBedrooms() != null) {
            queryBuilder.append(" AND ul.propertyDetails.numberOfBedrooms = :numberOfBedrooms");
        }
        if (criteria.getNumberOfBathrooms() != null) {
            queryBuilder.append(" AND ul.propertyDetails.numberOfBathrooms = :numberOfBathrooms");
        }
        if (criteria.getNumberOfKitchens() != null) {
            queryBuilder.append(" AND ul.propertyDetails.numberOfKitchens = :numberOfKitchens");
        }
        if (criteria.getMinPrice() != null) {
            queryBuilder.append(" AND ul.propertyDetails.price >= :minPrice");
        }
        if (criteria.getMaxPrice() != null) {
            queryBuilder.append(" AND ul.propertyDetails.price <= :maxPrice");
        }

        Query query = entityManager.createQuery(queryBuilder.toString(), UserListing.class);

        if (criteria.getListingType() != null) {
            query.setParameter("listingType", criteria.getListingType());
        }
        if (criteria.getPostalCode() != null && !criteria.getPostalCode().isEmpty()) {
            query.setParameter("postalCode", criteria.getPostalCode());
        }
        if (criteria.getCity() != null && !criteria.getCity().isEmpty()) {
            query.setParameter("city", criteria.getCity());
        }
        if (criteria.getTown() != null && !criteria.getTown().isEmpty()) {
            query.setParameter("town", criteria.getTown());
        }
        if (criteria.getNumberOfBedrooms() != null) {
            query.setParameter("numberOfBedrooms", criteria.getNumberOfBedrooms());
        }
        if (criteria.getNumberOfBathrooms() != null) {
            query.setParameter("numberOfBathrooms", criteria.getNumberOfBathrooms());
        }
        if (criteria.getNumberOfKitchens() != null) {
            query.setParameter("numberOfKitchens", criteria.getNumberOfKitchens());
        }
        if (criteria.getMinPrice() != null) {
            query.setParameter("minPrice", criteria.getMinPrice());
        }
        if (criteria.getMaxPrice() != null) {
            query.setParameter("maxPrice", criteria.getMaxPrice());
        }

        return query.getResultList();
    }



    private static final ResponseEntity<?> SUCCESS_RESPONSE = ResponseEntity.ok(new StatusCodeModel("200",200,"Success"));
    private static final ResponseEntity<?> BAD_RESPONSE = ResponseEntity.badRequest().body(new StatusCodeModel("400",400,"Fail"));


    public static ResponseEntity<?> BadResponseMessage(String message){
        return ResponseEntity.badRequest().body(new StatusCodeModel("400",400,message));

    }

    public static ResponseEntity<?> SuccessResponseMessage(String message){
        return ResponseEntity.ok(new StatusCodeModel("200",200,message));

    }
    public  ResponseEntity<?> searchFeed(ListingSearchCriteriaDTO criteriaDTO){
        List<UserListing> userListingList = searchListingsByCriteria(criteriaDTO);

        if(userListingList.isEmpty()){
            return BadResponseMessage("Unable to fetch feed !");
        }else{
            return ResponseEntity.ok(new CustomStatusCodeModel("200",200,"Feed !",userListingList));

        }
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

    public ResponseEntity<?> getNotificationsByReceiverId(String receiverId) {
        try {
            Long id = Long.valueOf(receiverId);
            List<Notification> notifications = notificationRepo.findAll();
            List<Notification> myNotifications = new ArrayList<>();
            for (int i = 0; i < notifications.size(); i++) {
                if(notifications.get(i).getReceiver().getUserId()==Integer.parseInt(receiverId)){
                    myNotifications.add(notifications.get(i));
                }

            }

            if (myNotifications.isEmpty()) {
                return BadResponseMessage("No notifications found for receiver with ID " + id);
            }

            return ResponseEntity.ok(new CustomStatusCodeModel("200", 200, "Notifications fetched successfully", myNotifications));
        } catch (NumberFormatException e) {
            return BadResponseMessage("Invalid receiver ID format: " + receiverId);
        } catch (Exception e) {
            return BadResponseMessage(e.getMessage());
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

    @Transactional
    public ResponseEntity<?> deleteUser(String userId) {

        if(userTableRepo.existsById(Long.valueOf(userId))){
            userListingRepo.deleteAllByUserId(Long.valueOf(userId));
            userTableRepo.deleteById(Long.valueOf(userId));

            return SuccessResponseMessage("User Deleted successfully");
        }else {
            return BadResponseMessage("User Not Found !");
        }

    }
    public ResponseEntity<?> changePassword(HashMap<String,String> passwordMap){
        Optional<UserTable> userTable = userTableRepo.findById(Long.valueOf(passwordMap.get("userId")));
        if(userTable.isPresent()){
            String currentPassword = passwordMap.get("currentPassword");
            String newPassword = passwordMap.get("newPassword");
            String confirmNewPassword = passwordMap.get("confirmPassword");
            if(userTable.get().getPassword().equals(currentPassword)){
                if(newPassword.equals(confirmNewPassword)){
                    userTable.get().setPassword(newPassword);
                    return SuccessResponseMessage("Password updated !");


                }else{
                    return BadResponseMessage("Password does not match !");

                }

            }else {
                return BadResponseMessage("Password does not match !");
            }

        }else {
            return BadResponseMessage("User not found !");

        }



    }
    public ResponseEntity<?> changeEmailAddress(HashMap<String,String> changeEmailAddressMap){
        Optional<UserTable> userTable = userTableRepo.findById(Long.valueOf(changeEmailAddressMap.get("userId")));
        if(userTable.isPresent()){
            String currentEmailAddress = changeEmailAddressMap.get("currentEmail");
            if(currentEmailAddress.equals(userTable.get().getEmail())){
                String newEmailAddress = changeEmailAddressMap.get("newEmail");
                UserTable userTable1 = userTable.get();
                userTable1.setEmail(newEmailAddress);
                userTableRepo.save(userTable1);
                return SuccessResponseMessage("Email Address Updated !");

            }else {
                return BadResponseMessage("Email does not match !");
            }

        }else {
            return BadResponseMessage("User Not Found !");
        }

    }
}
