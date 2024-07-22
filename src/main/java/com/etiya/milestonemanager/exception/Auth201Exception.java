package com.etiya.milestonemanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Catch 201 Created
@ResponseStatus(value = HttpStatus.CREATED)
public class Auth201Exception extends RuntimeException{

    public Auth201Exception(String message) {
        super(message);
    }
}