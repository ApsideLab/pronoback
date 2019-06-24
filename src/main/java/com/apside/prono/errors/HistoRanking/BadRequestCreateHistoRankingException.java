package com.apside.prono.errors.HistoRanking;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestCreateHistoRankingException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public BadRequestCreateHistoRankingException(String message) {
        super(message);
    }
}