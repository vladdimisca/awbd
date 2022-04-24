package com.awbd.project.error.exception;

import com.awbd.project.error.ErrorMessage;
import org.springframework.http.HttpStatus;

public class ForbiddenActionException extends AbstractApiException {
    public ForbiddenActionException(ErrorMessage errorMessage, Object... params) {
        super(HttpStatus.FORBIDDEN, errorMessage, params);
    }
}
