package com.redeyesncode.estatespring.realestatebackend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
@Entity
@Table(name = "user_table")
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "fullName")
    private String fullName;

    @Column(name = "telephoneNumber")
    private String telephoneNumber;

    @Column(name = "email")
    private String email;



    @Column(name = "postcode")
    private String postcode;

    @Column(name = "password")
    private String password;


    @Column(name = "userName")
    private String userName;


    @CreationTimestamp
    private String createdAt;


    @UpdateTimestamp
    private String updatedAt;


}
