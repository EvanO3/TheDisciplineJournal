package com.journal.discipline.tracker.Exceptions;


public class ApiException extends RuntimeException {
    private String message;


    public ApiException(){

    }

    public ApiException(String message){
        super(message);
    }
}
