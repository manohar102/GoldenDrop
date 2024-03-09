package com.techbuddy.goldendrop.exception;

public class InvalidProductException extends InvalidRequestException {

    public InvalidProductException(String errormsg) {
        super(errormsg);
    }
}
