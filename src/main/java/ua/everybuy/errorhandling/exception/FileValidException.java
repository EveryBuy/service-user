package ua.everybuy.errorhandling.exception;

import org.springframework.core.MethodParameter;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

public class FileValidException extends IllegalArgumentException {
    public FileValidException (String message){
        super(message);
    }
}
