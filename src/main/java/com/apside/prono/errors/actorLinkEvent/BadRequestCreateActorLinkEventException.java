package com.apside.prono.errors.actorLinkEvent;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestCreateActorLinkEventException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public BadRequestCreateActorLinkEventException(String message) {
        super(message);
    }
}