package com.pawer.exception;

import lombok.Getter;

@Getter
public class AuthException extends RuntimeException{
    private final EErrorType EErrorType;

    public AuthException(EErrorType EErrorType){
        super(EErrorType.getMessage());
        this.EErrorType = EErrorType;
    }

    public AuthException(EErrorType EErrorType, String message){
        super(message);
        this.EErrorType = EErrorType;
    }
}
