package ua.everybuy.errorhandling.exception.impl;

import org.springframework.http.HttpStatus;
import ua.everybuy.errorhandling.exception.CustomException;

public class EmailAlreadyExistsException extends RuntimeException implements CustomException {
    public EmailAlreadyExistsException(String email) {
        super("Email: " + email + " already exists in mailing list");
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.CONFLICT.value();
    }
}
