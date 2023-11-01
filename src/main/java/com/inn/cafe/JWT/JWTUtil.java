package com.inn.cafe.JWT;

import com.google.common.base.Function;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTUtil {

    /*a private member variable secret is declared and initialized with the string "chandud".
     This secret key is used for signing and verifying JWT tokens.*/
    private String secret = "chandud";

   /* This method extractUsername takes a JWT token as input
     and extracts the username from it using the extractClaims method.
    It uses a method reference Claims::getSubject to specify
     how to extract the username from the JWT claims.*/
    public String extractUsername(String token) {

        return extractClaims(token, Claims::getSubject);
    }

    /**
     * Extracts the expiration date from a JSON Web Token (JWT) given a token string.
     *
     * @param token The JWT token string from which to extract the expiration claim.
     * @return The expiration date as a Date object extracted from the JWT token.
     */
    public Date extractExperation(String token) {
        // Call the extractClaims method to extract the expiration claim from the JWT.
        // The Claims::getExpiration method reference is used to specify the extraction of the expiration claim.
        return extractClaims(token, Claims::getExpiration);
    }

    //This method extractClaims takes a JWT token and a Function that specifies how to extract a specific claim from the JWT's claims.
    // It first extracts all claims from the token using extractAllClaims and then applies the provided claimsResolver function to retrieve the specific claim.
    public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
    This method extractAllClaims parses the JWT token using the Jwts.parser() method and sets the signing key to secret.
     It then parses the token, extracts the body (claims) of the token,
     and returns it as a Claims object.
     */
    public Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    //This private method isTokenExpired checks if the JWT token has expired by comparing the expiration date extracted from the token with the current date and time.
    private Boolean isTokenExpired(String token) {

        return extractExperation(token).before(new Date());
    }


    //This method generateToken takes a username and a role as input, creates a set of claims ,
    // and then uses the createToken method to generate a JWT token with these claims.
    public String generateToken(String username, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        return createToken(claims, username);
    }

    /**
     This private method createToken takes a set of claims and a subject (username),
     and creates a JWT token. It sets the claims, subject, issued at (current date and time),
     expiration (current date and time plus 10 hours), and signs the token using the HMAC SHA-256 algorithm with the secret key.
    java
    */
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                //.setExpiration(new Date(System.currentTimeMillis() +  1 * 60 * 1000))
                .signWith(SignatureAlgorithm.HS256, secret).compact();
    }

    /**
    This method validateToken takes a JWT token and a UserDetails object (user)
     and checks whether the token is valid. It does this by extracting the username from the token,
     comparing it to the username from the UserDetails, and checking whether the token has expired.
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}
