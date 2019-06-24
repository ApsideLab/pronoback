package com.apside.prono.errors.resultEvent;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestCreateResultEventException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public BadRequestCreateResultEventException(String message) {
        super(message);
    }
}