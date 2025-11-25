package com.journal.discipline.tracker.Controller;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.journal.discipline.tracker.DTOs.StreakData;
import com.journal.discipline.tracker.DTOs.SummaryDTO;
import com.journal.discipline.tracker.DTOs.UserDTO;
import com.journal.discipline.tracker.DTOs.UserResponse;
import com.journal.discipline.tracker.Exceptions.ApiException;
import com.journal.discipline.tracker.Jwt.JwtUtils;

import com.journal.discipline.tracker.Service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;



@RestController
@RequestMapping("/api")
public class UserController {
private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;
 
    @Autowired
    JwtUtils jwtUtils;

    @GetMapping("/public/user")
    public ResponseEntity<UserResponse> retrieveUser(HttpServletRequest request ){
        UUID userId = jwtUtils.provideUserIdFromRequest(request);

        UserResponse user = userService.retrieveUser(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


     @DeleteMapping("/public/user")
    public ResponseEntity<UserDTO> deleteUser( HttpServletRequest request){
        
        UUID userId = jwtUtils.provideUserIdFromRequest(request);
        UserDTO user = userService.deleteUser(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/public/user")
    public ResponseEntity<UserDTO> updateUsername( @Valid @RequestBody UserDTO userDTO, HttpServletRequest request){
        UUID userId = jwtUtils.provideUserIdFromRequest(request);
        UserDTO user = userService.updateUsername(userDTO,userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @GetMapping("/public/user/summary")
    public ResponseEntity<SummaryDTO> userDailySummary(HttpServletRequest request){
        UUID userId = jwtUtils.provideUserIdFromRequest(request);

        SummaryDTO userSummary = userService.getUserDailySummary(userId);
        return new ResponseEntity<>(userSummary, HttpStatus.OK);
    }


    @PatchMapping("/public/user")
    public ResponseEntity<StreakData> updateUserStreak(HttpServletRequest request){
        UUID usersId = jwtUtils.provideUserIdFromRequest(request);

        StreakData userStreakData = userService.updateUserStreakData(usersId);
        return new ResponseEntity<>(userStreakData, HttpStatus.OK);
        
    }




}
