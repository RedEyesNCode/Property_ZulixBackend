package com.redeyesncode.estatespring.realestatebackend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.redeyesncode.estatespring.realestatebackend.jwt.JwtSecretKey;
import com.redeyesncode.estatespring.realestatebackend.models.common.StatusCodeModel;
import com.redeyesncode.estatespring.realestatebackend.repository.UserTableRepo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.Key;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
public class CustomHeaderFilter implements Filter {
    private static final String HEADER_TO_CHECK = "x-api-key";
    private final ObjectMapper objectMapper;

    @Autowired
    private UserTableRepo userTableRepo;


    private final List<String> excludedEndpoints = Arrays.asList("/spring-property/register-user", "/spring-property/login-user");



    public CustomHeaderFilter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;

        // Check if the incoming request contains the specified header
        String headerValue = httpRequest.getHeader(HEADER_TO_CHECK);
        String JwtHeader = httpRequest.getHeader("Authorization");

        String requestURI = httpRequest.getRequestURI();

        if (excludedEndpoints.contains(requestURI)) {
            if(headerValue.equals("application/javascript")){
                filterChain.doFilter(servletRequest, servletResponse);

            }else{
                StatusCodeModel statusCodeModel = new StatusCodeModel(String.valueOf(HttpStatus.FORBIDDEN.value()),403, "Application Type Not Supported");

                // Convert StatusCodeModel to JSON
                String jsonResponse = objectMapper.writeValueAsString(statusCodeModel);

                // Set the response content type to JSON
                servletResponse.setContentType("application/json");

                // Write the JSON response to the output stream
                servletResponse.getWriter().write(jsonResponse);
            }
            return;
        }else if (headerValue != null && !headerValue.isEmpty()) {


            // Header exists, do something
            // You can access the header value via headerValue variable
            if(headerValue.equals("application/javascript") && isValidJWTToken(JwtHeader)){

                filterChain.doFilter(servletRequest, servletResponse);

            }else if(!isValidJWTToken(JwtHeader)){
                StatusCodeModel statusCodeModel = new StatusCodeModel(String.valueOf(HttpStatus.FORBIDDEN.value()),403, "Token Expired.");

                // Convert StatusCodeModel to JSON
                String jsonResponse = objectMapper.writeValueAsString(statusCodeModel);

                // Set the response content type to JSON
                servletResponse.setContentType("application/json");

                // Write the JSON response to the output stream
                servletResponse.getWriter().write(jsonResponse);

            }else {
                StatusCodeModel statusCodeModel = new StatusCodeModel(String.valueOf(HttpStatus.FORBIDDEN.value()),403, "Application Type Not Supported");

                // Convert StatusCodeModel to JSON
                String jsonResponse = objectMapper.writeValueAsString(statusCodeModel);

                // Set the response content type to JSON
                servletResponse.setContentType("application/json");

                // Write the JSON response to the output stream
                servletResponse.getWriter().write(jsonResponse);
            }

            System.out.println("Incoming request has the header: " + HEADER_TO_CHECK);
            // Proceed with the filter chain

        } else {

            StatusCodeModel statusCodeModel = new StatusCodeModel(String.valueOf(HttpStatus.FORBIDDEN.value()),403, "App is Missing Header");

            // Convert StatusCodeModel to JSON
            String jsonResponse = objectMapper.writeValueAsString(statusCodeModel);

            // Set the response content type to JSON
            servletResponse.setContentType("application/json");

            // Write the JSON response to the output stream
            servletResponse.getWriter().write(jsonResponse);
        }
    }
    private boolean isValidJWTToken(String jwtToken) {
        try {
            // Parse the token
            Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

// Convert the key to a string (for storage, e.g., in application properties)
            String encodedKey = Base64.getEncoder().encodeToString(key.getEncoded());
// Convert the key to a string (for storage, e.g., in application properties)
            Claims claims = Jwts.parser()
                    .setSigningKey(JwtSecretKey.getSecretKey()) // Replace with your signing key
                    .build()
                    .parseClaimsJws(jwtToken)
                    .getBody();
            String subject = claims.getSubject();

            // Get the expiration time claim
            Date expiration = claims.getExpiration();

            // Check if the current time is before the expiration time
            return expiration.after(new Date());

        } catch (JwtException | IllegalArgumentException e) {
            // Log any potential exceptions or handle accordingly
            e.printStackTrace();
            return false;
        }
    }
}
