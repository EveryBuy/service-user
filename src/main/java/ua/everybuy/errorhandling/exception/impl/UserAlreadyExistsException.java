package ua.everybuy.errorhandling.exception.impl;

import org.springframework.http.HttpStatus;
import ua.everybuy.errorhandling.exception.CustomException;

public class UserAlreadyExistsException extends RuntimeException implements CustomException {
    public UserAlreadyExistsException(long userId){
        super("User with id " + userId + " already exists");
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.CONFLICT.value();
    }
}
