package com.edu.ulab.app.exception;

public class IncorrectRequestBodyException extends RuntimeException {
    public IncorrectRequestBodyException(String message) {
        super(message);
    }
}
