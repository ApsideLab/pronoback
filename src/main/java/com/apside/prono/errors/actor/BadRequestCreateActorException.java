package com.apside.prono.errors.actor;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestCreateActorException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public BadRequestCreateActorException(String message) {
        super(message);
    }
}