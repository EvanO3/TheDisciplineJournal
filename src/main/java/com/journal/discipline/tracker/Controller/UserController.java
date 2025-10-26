package com.journal.discipline.tracker.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.journal.discipline.tracker.DTOs.UserDTO;
import com.journal.discipline.tracker.Model.User;
import com.journal.discipline.tracker.Service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {
private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;
    
    @PostMapping("/user")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO user){
        logger.info("This is the controller getting the user: {}", user);
       UserDTO createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

}
