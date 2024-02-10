package com.techbuddy.goldendrop.exception;

public class InvalidStockQuantityException extends RuntimeException {

    public InvalidStockQuantityException(String errormsg) {
        super(errormsg);
    }
}
