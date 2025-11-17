package com.journal.discipline.tracker.Exceptions;

public class UnauthorizationException extends RuntimeException{
      private String message;


    public UnauthorizationException(){

    }

    public UnauthorizationException(String message){
        super(message);
    }
}
