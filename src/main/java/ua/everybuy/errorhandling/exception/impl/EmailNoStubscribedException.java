package ua.everybuy.errorhandling.exception.impl;

import org.springframework.http.HttpStatus;
import ua.everybuy.errorhandling.exception.CustomException;

public class EmailNoStubscribedException extends RuntimeException implements CustomException {
    public EmailNoStubscribedException(String email) {
        super("The email " + email +" does not exist in the subscriptions.");
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.NOT_FOUND.value();
    }
}
