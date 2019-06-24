package com.apside.prono.errors.result;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestCreateResultTryRugbyException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public BadRequestCreateResultTryRugbyException(String message) {
        super(message);
    }
}