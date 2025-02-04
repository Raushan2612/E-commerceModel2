package com.cts.ppstores.exceptions;

import org.slf4j.Logger;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InvalidCredentialsException extends RuntimeException{
    public InvalidCredentialsException(String message){
        super(message);
        log.error("InvalidCredentialsException: {}", message);
    }
}