package ua.everybuy.errorhandling.exception.impl;

import org.springframework.http.HttpStatus;
import ua.everybuy.errorhandling.exception.CustomException;

import java.io.IOException;

public class FileFormatException extends RuntimeException implements CustomException {
    public FileFormatException(String message){
        super(message);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.UNSUPPORTED_MEDIA_TYPE.value();
    }
}
