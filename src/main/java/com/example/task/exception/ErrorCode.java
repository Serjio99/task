package com.example.task.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    USER_NOT_FOUND("TASK-100"),

    STORE_VALIDATION_ERROR("TASK-200"),
    STORE_NOT_FOUND("TASK-201"),

    NOT_ENOUGH_MONEY_IN_THE_ACCOUNT("TASK-300");
    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }

}
