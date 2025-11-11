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
import com.journal.discipline.tracker.Exceptions.ApiException;
import com.journal.discipline.tracker.Jwt.JwtUtils;
import com.journal.discipline.tracker.Model.User;
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

    @GetMapping("/user")
    public ResponseEntity<UserResponse> retrieveUser(HttpServletRequest request ){
        UUID userId = provideUserIdFromRequest(request);

        UserResponse user = userService.retrieveUser(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


     @DeleteMapping("/user")
    public ResponseEntity<UserDTO> deleteUser( HttpServletRequest request){
        
        UUID userId = provideUserIdFromRequest(request);
        UserDTO user = userService.deleteUser(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/user")
    public ResponseEntity<UserDTO> updateUsername( @Valid @RequestBody UserDTO userDTO, HttpServletRequest request){
        UUID userId = provideUserIdFromRequest(request);
        UserDTO user = userService.updateUsername(userDTO,userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }






    /*Helper function for user Id */

    private UUID provideUserIdFromRequest(HttpServletRequest request){
        String token = jwtUtils.getJwtTokenFromHeader(request);
        String extractedUserId = jwtUtils.getUserIdFromJwt(token);

        if(extractedUserId.isEmpty() || extractedUserId ==null){
            throw new ApiException("Failed to retrieve user Id");
        }
        return UUID.fromString(extractedUserId);
    }
}
