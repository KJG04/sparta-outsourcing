package com.sparta.spartaoutsourcing.menu.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) { super(message);}
}
