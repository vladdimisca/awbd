package com.awbd.project.error.exception;

import com.awbd.project.error.ErrorMessage;
import org.springframework.http.HttpStatus;

public class InternalServerErrorException extends AbstractApiException {
    public InternalServerErrorException(ErrorMessage errorMessage, Object... params) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage, params);
    }
}
