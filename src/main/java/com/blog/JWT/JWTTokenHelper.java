package com.blog.JWT;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTTokenHelper {

    //secret key
	    private String secret="8J8G1/2QTYJeK1k5Fj0mFf8g3N7h8S9F0G2jL9k7R4b3D2A8I7Y5T1P0X3V2W6Z4";

     //time in millliseconds 
	    private long jwtExpirationInMs=1000 * 60 * 60 * 24;

	    // Retrieve username from JWT token
	    public String extractUsername(String token) {
	        return extractClaim(token, Claims::getSubject);
	    }

	    // Retrieve expiration date from JWT token
	    public Date extractExpiration(String token) {
	        return extractClaim(token, Claims::getExpiration);
	    }

	    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
	        final Claims claims = extractAllClaims(token);
	        return claimsResolver.apply(claims);
	    }

	    //For retrieving any info from token we will need secret key
	    private Claims extractAllClaims(String token) {
	        return Jwts.parserBuilder().setSigningKey(secret).setAllowedClockSkewSeconds(60).build().parseClaimsJws(token).getBody();
	    }

	    private Boolean isTokenExpired(String token) {
	        return extractExpiration(token).before(new Date());
	    }

	    // Generate token for user
	    public String generateToken(String username) {
	        Map<String, Object> claims = new HashMap();
	        return createToken(claims, username);
	    }

	    // Create token
	    private String createToken(Map<String, Object> claims, String subject) {
	        return Jwts.builder()
	                .setClaims(claims)
	                .setSubject(subject)
	                .setIssuedAt(new Date(System.currentTimeMillis()))
	                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMs))
	                .signWith(SignatureAlgorithm.HS256, secret)
	                .compact();
	    }

	    // Validate token
	    public Boolean validateToken(String token, String username) {
	        final String extractedUsername = extractUsername(token);
	        return (extractedUsername.equals(username) && !isTokenExpired(token));
	    }

}
