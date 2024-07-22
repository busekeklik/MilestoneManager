package com.etiya.milestonemanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//Catch 403 Forbidden
@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class Auth403Exception extends RuntimeException{
    public Auth403Exception(String message) {
        super(message);
    }
}
