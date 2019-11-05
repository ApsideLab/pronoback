package com.apside.prono.errors.contest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestUpdateContestException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public BadRequestUpdateContestException(String message) {
        super(message);
    }
}