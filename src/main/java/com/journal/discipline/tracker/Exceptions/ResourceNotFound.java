package com.journal.discipline.tracker.Exceptions;

public class ResourceNotFound extends RuntimeException {
    
    String message;

    public ResourceNotFound(){

    }
    
    public ResourceNotFound(String message){
        super(message);
        
    }
}
