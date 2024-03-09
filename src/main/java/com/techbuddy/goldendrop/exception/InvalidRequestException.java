package com.techbuddy.goldendrop.exception;

public class InvalidRequestException extends RuntimeException {

    public InvalidRequestException(String errormsg) {
        super(errormsg);
    }
}
