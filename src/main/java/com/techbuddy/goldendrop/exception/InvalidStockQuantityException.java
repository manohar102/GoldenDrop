package com.techbuddy.goldendrop.exception;

public class InvalidStockQuantityException extends InvalidRequestException {

    public InvalidStockQuantityException(String errormsg) {
        super(errormsg);
    }
}
