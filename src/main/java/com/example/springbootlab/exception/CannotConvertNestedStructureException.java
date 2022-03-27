package com.example.springbootlab.exception;

public class CannotConvertNestedStructureException extends RuntimeException{
    public CannotConvertNestedStructureException(String message) {
        super(message);
    }
}
