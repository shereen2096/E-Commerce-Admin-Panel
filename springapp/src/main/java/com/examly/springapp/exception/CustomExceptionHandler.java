package com.examly.springapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntime(RuntimeException ex) {

        Map<String, String> error = new HashMap<>();
        error.put("message", ex.getMessage());

        // ONLY "Order not found" should return 404
        if ("Order not found".equals(ex.getMessage())) {
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        // Everything else should return 400
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}