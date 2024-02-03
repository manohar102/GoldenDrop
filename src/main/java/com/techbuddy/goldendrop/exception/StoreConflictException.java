package com.techbuddy.goldendrop.exception;

public class StoreConflictException extends RuntimeException {

    public StoreConflictException(String errormsg) {
        super(errormsg);
    }
}
