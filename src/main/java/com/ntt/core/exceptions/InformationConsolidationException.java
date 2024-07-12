package com.ntt.core.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InformationConsolidationException extends RuntimeException {
    public InformationConsolidationException(String message) {
        super(message);
    }
}
