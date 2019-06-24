package com.apside.prono.errors.tryEventLink;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestCreateTypeEventLinkException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public BadRequestCreateTypeEventLinkException(String message) {
        super(message);
    }
}