package com.example.busstation.exception;

public class DuplicateVinException extends RuntimeException{
    public DuplicateVinException(String message){
        super(message);
    }
}
