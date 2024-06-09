package ua.everybuy.errorhandling;


import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import ua.everybuy.errorhandling.exception.CustomException;
import ua.everybuy.errorhandling.exception.impl.FileFormatException;
import ua.everybuy.errorhandling.exception.impl.FileValidException;
import ua.everybuy.errorhandling.exception.impl.PasswordValidException;
import ua.everybuy.errorhandling.exception.impl.UserNotFoundException;
import ua.everybuy.routing.model.response.ErrorResponse;
import ua.everybuy.routing.model.response.MessageResponse;

import java.io.IOException;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler({HttpStatusCodeException.class})
//    public ResponseEntity<ErrorResponse> handleValidationExceptions(HttpStatusCodeException ex) {
//        return ResponseEntity
//                .badRequest()
//                .body(new ErrorResponse(ex.getStatusCode().value(), new MessageResponse(ex.getMessage())));
//    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ErrorResponse> handleFileValidExceptions(CustomException ex) {
        return ResponseEntity
                .status(ex.getStatusCode())
                .body(new ErrorResponse(ex.getStatusCode(), new MessageResponse(ex.getMessage())));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException exception) {
        List<String> errors = exception.getBindingResult().getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        return ResponseEntity.badRequest()
                .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                        new MessageResponse(String.join("; ", errors))));
    }

    @ExceptionHandler(IOException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleIOException(IOException ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        new MessageResponse(ex.getMessage())));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingParamException(MissingServletRequestParameterException ex){
        return ResponseEntity
                .status(ex.getStatusCode())
                .body(new ErrorResponse(ex.getStatusCode().value(),
                        new MessageResponse(ex.getMessage())));
    }

}
