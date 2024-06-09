package ua.everybuy.errorhandling.exception.impl;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import ua.everybuy.errorhandling.exception.CustomException;

public class FileValidException extends RuntimeException implements CustomException {

    public FileValidException (String message){
        super(message);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }
}
