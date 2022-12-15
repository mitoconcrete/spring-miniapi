package com.app.api.exception;

public class DuplicateDataException extends RuntimeException{
    public DuplicateDataException(String message) {
        super(message);
    }
}
