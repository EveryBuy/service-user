package ua.everybuy.errorhandling.exception.impl;

import org.springframework.http.HttpStatus;
import ua.everybuy.errorhandling.exception.CustomException;

public class EmailSendPermissionException extends RuntimeException implements CustomException {
    public EmailSendPermissionException(){
        super("Not valid password");
    }
    @Override
    public int getStatusCode() {
        return HttpStatus.FORBIDDEN.value();
    }
}
