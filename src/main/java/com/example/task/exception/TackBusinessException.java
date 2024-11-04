package com.example.task.exception;

import lombok.Getter;

@Getter
public class TackBusinessException extends RuntimeException {

    private final ErrorCode errorCode;

    public TackBusinessException(String customMessage, ErrorCode errorCode) {
        super(customMessage);
        this.errorCode = errorCode;
    }

}