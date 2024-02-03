package com.techbuddy.goldendrop.exception;

public class InvalidStoreException extends RuntimeException {

    public InvalidStoreException(String errormsg) {
        super(errormsg);
    }
}
