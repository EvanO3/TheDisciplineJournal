package com.journal.discipline.tracker.Exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.journal.discipline.tracker.DTOs.APIResponse;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String,String>> myConstraintViolationException(ConstraintViolationException  e){
        Map<String, String> response = new HashMap<>();

       e.getConstraintViolations().forEach(constraint ->{
        String fieldName = constraint.getPropertyPath().toString();
        String message = constraint.getMessage();
        response.put(fieldName, message);
       });

       return new ResponseEntity<Map<String,String>>(response, HttpStatus.BAD_REQUEST);
        

    }
    

     @ExceptionHandler(ApiException.class)
    public ResponseEntity<APIResponse> myAPIException(ApiException e){
        String message = e.getMessage();
        APIResponse response = new APIResponse(message, false);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /*Specific usecase for if t */
    @ExceptionHandler(UnauthorizationException.class)
    public ResponseEntity<APIResponse> myUnAuthorizationException(UnauthorizationException e){
        String message = e.getMessage();
        APIResponse response = new APIResponse(message, false);
        
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

 
    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<APIResponse> myResourceNotFoundException(ResourceNotFound e){
        String message = e.getMessage();

        APIResponse response = new APIResponse(message, false);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }




}
