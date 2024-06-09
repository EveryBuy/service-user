package ua.everybuy.errorhandling.exception.impl;

import org.springframework.http.HttpStatus;
import ua.everybuy.errorhandling.exception.CustomException;

public class PasswordValidException extends RuntimeException implements CustomException {
    public PasswordValidException(){
        super("The passwords do not match");
    }

    public int getStatusCode(){
        return HttpStatus.BAD_REQUEST.value();
    }
}
