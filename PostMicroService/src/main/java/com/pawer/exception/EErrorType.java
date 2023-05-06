package com.pawer.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum EErrorType {

    INTERNAL_ERROR(3000,"Unexpected error on the server.",INTERNAL_SERVER_ERROR),
    INVALID_TOKEN(4001,"Invalid token error",BAD_REQUEST),
    BAD_REQUEST_ERROR(1202,"You have entered an invalid parameter.",BAD_REQUEST),
    POST_BULUNAMADI(2302,"There are no posts with the id you are looking for.",INTERNAL_SERVER_ERROR);
    private int code;
    private String message;
    private HttpStatus httpStatus;
}
