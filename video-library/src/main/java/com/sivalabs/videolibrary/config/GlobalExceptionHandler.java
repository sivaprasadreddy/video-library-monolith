package com.sivalabs.videolibrary.config;

import com.sivalabs.videolibrary.common.exception.AccessDeniedException;
import com.sivalabs.videolibrary.common.exception.ResourceNotFoundException;
import com.sivalabs.videolibrary.common.exception.UnauthorizedAccessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(UnauthorizedAccessException.class)
    public String handleUnauthorizedAccessException(UnauthorizedAccessException e) {
        log.error(e.getMessage(), e);
        return "401";
    }

    @ExceptionHandler(AccessDeniedException.class)
    public String handleAccessDeniedException(AccessDeniedException e) {
        log.error(e.getMessage(), e);
        return "401";
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleResourceNotFoundException(ResourceNotFoundException e) {
        log.error(e.getMessage(), e);
        return "404";
    }
}
