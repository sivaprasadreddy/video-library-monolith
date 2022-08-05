package com.sivalabs.videolibrary.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends ApplicationException {

    public UserNotFoundException(String message) {
        super(message);
    }
}
