package com.example.springbootlab.exception;

public class MemberEmailAlreadyExistsException extends RuntimeException {
    public MemberEmailAlreadyExistsException(String message){
        super(message);
    }

}
