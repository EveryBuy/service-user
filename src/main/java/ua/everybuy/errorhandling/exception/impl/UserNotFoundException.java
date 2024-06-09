package ua.everybuy.errorhandling.exception.impl;

import org.springframework.http.HttpStatus;
import ua.everybuy.errorhandling.exception.CustomException;

public  class UserNotFoundException extends RuntimeException implements CustomException {
    public UserNotFoundException(long userId) {
        super("User with ID: " + userId + " not found");
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.NOT_FOUND.value();
    }
}
