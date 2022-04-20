package com.awbd.project.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessage {
    INTERNAL_SERVER_ERROR(1, "Internal server error. Oops, something went wrong!"),
    ALREADY_EXISTS(2, "{0} already exists!"),
    RESOURCE_NOT_FOUND(3, "The {0} with id={1} was not found!"),
    FORBIDDEN(4, "Forbidden action. {0}!");

    private final int errorCode;
    private final String errorMessage;
}
