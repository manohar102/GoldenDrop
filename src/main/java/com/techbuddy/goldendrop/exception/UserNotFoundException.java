package com.techbuddy.goldendrop.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String errormsg) {
        super(errormsg);
    }
}
