package com.techbuddy.goldendrop.exception;

import jakarta.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = InvalidStoreException.class)
    public ResponseEntity invalidStoreException(InvalidStoreException exception) {
        Map<String, String> message = new HashMap<String, String>();
        message.put("message", exception.getMessage());
        message.put("status", "error");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    @ExceptionHandler(value = InvalidProductException.class)
    public ResponseEntity invalidProductException(InvalidProductException exception) {
        Map<String, String> message = new HashMap<String, String>();
        message.put("message", exception.getMessage());
        message.put("status", "error");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity invalidRequest(MethodArgumentNotValidException exception) {
        List<String> errorMessagesList = exception.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessagesList);
    }

    @ExceptionHandler(value = StoreConflictException.class)
    public ResponseEntity storeConflictException(StoreConflictException exception) {
        Map<String, String> message = new HashMap<String, String>();
        message.put("message", exception.getMessage());
        message.put("status", "error");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity constraintViolation(ConstraintViolationException exception) {
        List<String> templateMessages = new ArrayList<>();
        exception.getConstraintViolations().forEach(violation -> templateMessages.add(violation.getMessageTemplate()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(templateMessages);
    }
}
