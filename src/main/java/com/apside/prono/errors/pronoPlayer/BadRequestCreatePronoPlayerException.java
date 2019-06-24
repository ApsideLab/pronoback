package com.apside.prono.errors.pronoPlayer;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestCreatePronoPlayerException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public BadRequestCreatePronoPlayerException(String message) {
        super(message);
    }
}