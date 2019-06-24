package com.apside.prono.errors.type;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestCreateTypeException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public BadRequestCreateTypeException(String message) {
        super(message);
    }
}