package com.etiya.milestonemanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//Catch 401 Unauthorized Entry
@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class Auth401Exception extends RuntimeException{

    public Auth401Exception(String message) {
        super(message);
    }
}
