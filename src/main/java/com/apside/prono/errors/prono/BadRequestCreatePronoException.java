package com.apside.prono.errors.prono;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestCreatePronoException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public BadRequestCreatePronoException(String message) {
        super(message);
    }
}