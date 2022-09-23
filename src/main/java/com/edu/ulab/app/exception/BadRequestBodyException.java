package com.edu.ulab.app.exception;

public class BadRequestBodyException extends RuntimeException {
    public BadRequestBodyException(String message) {
        super(message);
    }
}
