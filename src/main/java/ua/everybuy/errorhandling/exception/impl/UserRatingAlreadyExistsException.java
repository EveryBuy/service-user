package ua.everybuy.errorhandling.exception.impl;

import org.springframework.http.HttpStatus;
import ua.everybuy.errorhandling.exception.CustomException;

public class UserRatingAlreadyExistsException extends RuntimeException implements CustomException {
    public UserRatingAlreadyExistsException(long userId, long raterId){
        super("User " + raterId + " already rated user " + userId);
    }

    public int getStatusCode(){
        return HttpStatus.CONFLICT.value();
    }
}
