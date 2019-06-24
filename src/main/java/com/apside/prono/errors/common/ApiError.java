package com.apside.prono.errors.common;

import java.util.Date;

public class ApiError extends ApiErrorSub {
    private Date date;
    private String message;
    private String status;
    private String description;

    public ApiError(Date date, String message, String status, String description) {
        this.date = date;
        this.message = message;
        this.status = status;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}