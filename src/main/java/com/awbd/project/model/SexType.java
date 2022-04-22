package com.awbd.project.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SexType {
    MALE("Male"),
    FEMALE("Female"),
    OTHER("Other");

    private String description;
}
