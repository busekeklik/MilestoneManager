package com.etiya.milestonemanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//Catch 400 Bad request or there is no url that entered
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class Auth400Exception extends RuntimeException{

    public Auth400Exception(String message){
        super(message);
    }

}
