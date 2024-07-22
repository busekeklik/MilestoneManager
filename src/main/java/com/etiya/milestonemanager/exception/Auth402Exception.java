package com.etiya.milestonemanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//Catch 402 Payment Requirement
@ResponseStatus(value = HttpStatus.PAYMENT_REQUIRED)
public class Auth402Exception extends RuntimeException{
    public Auth402Exception(String message) {
        super(message);
    }
}