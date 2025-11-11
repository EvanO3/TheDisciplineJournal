package com.journal.discipline.tracker.Jwt;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

import javax.crypto.SecretKey;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;

import com.journal.discipline.tracker.Security.UserDetailsImpl;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class JwtUtils {
    /*Secret and exp */
    @Value("${spring.app.jwtSecret}")
    private String jwtSecret;

    /*expiration value sety for 1 day */
    @Value("${spring.app.jwtExpiration}")
    private int jwtExpirationInMs;

    private final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    /*Get the token from the header */

    public String getJwtTokenFromHeader(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");

        if(bearerToken !=null && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }




    //Generate the token from the username
    public String generateTokenFromUsername(UserDetailsImpl userDetails){
        String username = userDetails.getUsername();
        String userId = userDetails.getUserId().toString();
        
        return Jwts.builder()
        .subject(username)
        .claim("userId", userId)
        .issuedAt(new Date())
        .expiration(new Date((new Date().getTime() + jwtExpirationInMs)))
        .signWith(key())
        .compact();
    }


    //Get the username from the Jwt

    public String getUsernameFromJwt(String token){
        return Jwts.parser()
        .verifyWith((SecretKey) key())
        .build()
        .parseSignedClaims(token)
        .getPayload()
        .getSubject();
    }


    public String getUserIdFromJwt(String token){
        return Jwts.parser()
        .verifyWith((SecretKey) key())
        .build()
        .parseSignedClaims(token)
        .getPayload()
        .get("userId", String.class);
    }


    //Generating the Signing Key

    public Key key(){
        return Keys.hmacShaKeyFor(
            Decoders.BASE64.decode(jwtSecret)
        );
    }



        public boolean validateJwtToken(String authToken){
        try{
            System.out.println("Validate");
            Jwts.parser().verifyWith((SecretKey) key())
            .build().parseSignedClaims(authToken);
            return true;


        }catch(MalformedJwtException e){
            logger.error("Malformed JWT Exception", e.getMessage());
        }catch(ExpiredJwtException e){
            logger.error("Malformed JWT Exception", e.getMessage());
        }catch(UnsupportedJwtException e){
             logger.error("Malformed JWT Exception", e.getMessage());
        }catch(IllegalArgumentException e){
             logger.error("Malformed JWT Exception", e.getMessage());
        }
        return false;
    }

}
