package com.pawer.exception;

import lombok.Getter;

@Getter
public class PostException extends RuntimeException{
    private final EErrorType EErrorType;
    public PostException(EErrorType EErrorType){
        super(EErrorType.getMessage());
        this.EErrorType = EErrorType;
    }
    public PostException(EErrorType EErrorType, String message){
        super(message);
        this.EErrorType = EErrorType;
    }
}
