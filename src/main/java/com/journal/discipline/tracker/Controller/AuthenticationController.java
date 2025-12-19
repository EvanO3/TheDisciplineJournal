package com.journal.discipline.tracker.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.journal.discipline.tracker.DTOs.LoginRequestDTO;
import com.journal.discipline.tracker.DTOs.LoginResponse;
import com.journal.discipline.tracker.DTOs.UserDTO;
import com.journal.discipline.tracker.Jwt.JwtUtils;
import com.journal.discipline.tracker.Security.UserDetailsImpl;
import com.journal.discipline.tracker.Service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    
    @Autowired
    AuthenticationManager authManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);


   
@PostMapping("/register")
public ResponseEntity<UserDTO> createUser(@Valid @RequestBody  UserDTO user){
    
       UserDTO createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }


@PostMapping("/login")
public ResponseEntity<LoginResponse> login(@RequestBody LoginRequestDTO loginRequestDTO){
    Authentication authRequest= UsernamePasswordAuthenticationToken.unauthenticated(loginRequestDTO.getUsername(), loginRequestDTO.getPassword());
    try{
        /*try loging in the user */
        Authentication authResponse = authManager.authenticate(authRequest);
         /*This tells spring to consider the user that just tried to login to be loggerd in */
            SecurityContextHolder.getContext().setAuthentication(authResponse);
            String jwtToken = jwtUtils.generateTokenFromUsername((UserDetailsImpl) authResponse.getPrincipal());
        LoginResponse response = new LoginResponse("Login Successful", jwtToken, true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }catch(AuthenticationException e){
        LoginResponse response = new LoginResponse("Bad Credentials", null, false);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }




}

@PostMapping("/logout")
public ResponseEntity<?> logout(){
 return new ResponseEntity<>("Logged Out User", HttpStatus.OK);
}

}