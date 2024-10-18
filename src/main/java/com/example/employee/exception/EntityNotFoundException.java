package com.example.employee.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String s){
        super(s);
    }
}
