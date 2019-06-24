package com.apside.prono.errors.player;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestCreatePlayerException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public BadRequestCreatePlayerException(String message) {
        super(message);
    }
}