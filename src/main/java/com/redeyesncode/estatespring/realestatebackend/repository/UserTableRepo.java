package com.redeyesncode.estatespring.realestatebackend.repository;

import com.redeyesncode.estatespring.realestatebackend.models.UserTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserTableRepo extends JpaRepository<UserTable,Long> {
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM UserTable u WHERE u.telephoneNumber = :phoneNumber")
    boolean existsByTelephoneNumber(String phoneNumber);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM UserTable u WHERE u.userName = :username")
    boolean existsByUsername(String username);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM UserTable u WHERE u.email = :email")
    boolean existsByEmail(String email);


    @Query("SELECT u FROM UserTable u WHERE u.email = :email AND u.telephoneNumber = :telephoneNumber AND u.password = :password")
    UserTable findByEmailAndTelephoneNumberAndPassword(String email, String telephoneNumber, String password);
}
