package com.sivalabs.videolibrary.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends ApplicationException {

    public BadRequestException(String message) {
        super(message);
    }
}
