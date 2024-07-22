package com.etiya.milestonemanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class Auth404Exception extends RuntimeException{

    public Auth404Exception(String message){
        super(message);
    }
}
