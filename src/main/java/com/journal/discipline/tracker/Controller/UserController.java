package com.journal.discipline.tracker.Controller;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties.Http;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.journal.discipline.tracker.DTOs.UserDTO;
import com.journal.discipline.tracker.DTOs.UserResponse;
import com.journal.discipline.tracker.Model.User;
import com.journal.discipline.tracker.Service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserController {
private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;
    
    @PostMapping("/user")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody  UserDTO user){
        logger.info("This is the controller getting the user: {}", user);
       UserDTO createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<UserResponse> retrieveUser(@PathVariable UUID userId){
        UserResponse user = userService.retrieveUser(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


     @DeleteMapping("/user/{userId}")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable UUID userId){
        UserDTO user = userService.deleteUser(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/user/{userId}")
    public ResponseEntity<UserDTO> updateUsername( @Valid @RequestBody UserDTO userDTO, @PathVariable UUID userId){
        UserDTO user = userService.updateUsername(userDTO,userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }



}
