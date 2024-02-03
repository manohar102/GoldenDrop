package com.techbuddy.goldendrop.exception;

public class InvalidProductException extends RuntimeException {

    public InvalidProductException(String errormsg) {
        super(errormsg);
    }
}
