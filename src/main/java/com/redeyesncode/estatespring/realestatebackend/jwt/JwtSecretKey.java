package com.redeyesncode.estatespring.realestatebackend.jwt;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Base64;

public class JwtSecretKey {
    static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    // Convert the key to a string (for storage, e.g., in application properties)
    static String encodedKey = Base64.getEncoder().encodeToString(key.getEncoded());
    private static final String SECRET_KEY = encodedKey; // Replace with your actual secret key

    public static String getSecretKey() {
        return SECRET_KEY;
    }
}
