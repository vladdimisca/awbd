package com.awbd.project.error.exception;

import com.awbd.project.error.ErrorMessage;
import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends AbstractApiException {
    public ResourceNotFoundException(ErrorMessage errorMessage, Object... params) {
        super(HttpStatus.NOT_FOUND, errorMessage, params);
    }
}
