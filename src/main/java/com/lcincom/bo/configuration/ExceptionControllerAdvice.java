package com.lcincom.bo.configuration;

import lombok.Data;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(Exception.class)
    public ErrorResponse handleException(Exception ex) {
        ErrorResponse err = new ErrorResponse();
        err.setStatusCode(500);
        err.setErrorMessage(ex.getMessage());
        return err;
    }

}

@Data
class ErrorResponse {
    private int statusCode;
    private String errorMessage;
}
