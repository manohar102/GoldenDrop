package com.techbuddy.goldendrop.exception;

import lombok.Getter;

public class InvalidStoreException extends RuntimeException {

    public InvalidStoreException(String errormsg) {
        super(errormsg);
    }
}
