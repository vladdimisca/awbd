package com.awbd.project.error.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends AbstractApiException {
    public BadRequestException(String errorMessage) {
        super(HttpStatus.BAD_REQUEST, errorMessage);
    }
}
