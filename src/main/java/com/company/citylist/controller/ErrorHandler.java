package com.company.citylist.controller;

import com.company.citylist.domain.dto.ExceptionResponse;
import com.company.citylist.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

    private static final Logger log = LoggerFactory.getLogger(ErrorHandler.class);

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ExceptionResponse handleUnexpectedError(Exception ex) {
        log.error(String.format("Unexpected exception: %s", ex));
        return new ExceptionResponse("unexpected error", HttpStatus.INTERNAL_SERVER_ERROR.name());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ExceptionResponse handleException(NotFoundException ex) {
        log.error(String.format("Not Found exception: %s", ex));
        return new ExceptionResponse(ex.getMessage(), HttpStatus.NOT_FOUND.name());
    }
}
