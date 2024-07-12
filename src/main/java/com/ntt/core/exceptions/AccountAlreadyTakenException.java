package com.ntt.core.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AccountAlreadyTakenException extends RuntimeException {
    public AccountAlreadyTakenException(String message) {
        super(message);
    }
}
