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

    INTERNAL_ERROR(3000,"Sunucuda beklenmeyen hata",INTERNAL_SERVER_ERROR),
    INVALID_TOKEN(4001,"Geçersiz token bilgisi",BAD_REQUEST),
    BAD_REQUEST_ERROR(1202,"Geçersiz Parametre Girişi Yaptınız",BAD_REQUEST),

    POST_BULUNAMADI(2302,"Aradığınız id ye ait post bulunamamıştır",INTERNAL_SERVER_ERROR);
    private int code;
    private String message;
    private HttpStatus httpStatus;
}
