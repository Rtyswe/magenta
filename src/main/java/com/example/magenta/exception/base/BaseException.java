package com.example.magenta.exception.base;

import org.springframework.http.HttpStatus;

public abstract class BaseException extends RuntimeException {

    protected HttpStatus status;
    protected String message;

    public BaseException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
