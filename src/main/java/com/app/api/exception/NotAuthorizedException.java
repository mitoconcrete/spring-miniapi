package com.app.api.exception;

public class NotAuthorizedException extends RuntimeException{
    public NotAuthorizedException(String message) {
        super(message);
    }
}
