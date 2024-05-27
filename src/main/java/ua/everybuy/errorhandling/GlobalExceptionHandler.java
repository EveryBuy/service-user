package ua.everybuy.errorhandling;


import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import ua.everybuy.errorhandling.exception.FileFormatException;
import ua.everybuy.errorhandling.exception.FileValidException;
import ua.everybuy.errorhandling.exception.UserNotFoundException;
import ua.everybuy.routing.model.model.response.ErrorResponse;
import ua.everybuy.routing.model.model.response.MessageResponse;

import java.io.IOException;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ErrorResponse> handleValidationExceptions(HttpStatusCodeException ex) {
        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse(ex.getStatusCode().value(), new MessageResponse(ex.getMessage())));
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<ErrorResponse> handleFileValidExceptions(FileValidException ex) {
        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), new MessageResponse(ex.getMessage())));
    }

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleNoElementExceptions(UserNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatusCode.valueOf(404))
                .body(new ErrorResponse(404, new MessageResponse(ex.getMessage())));
    }

    @ExceptionHandler({FileFormatException.class})
    public ResponseEntity<ErrorResponse> handleFileDownloadException(FileFormatException ex) {
        return ResponseEntity
                .status(HttpStatusCode.valueOf(415))
                .body(new ErrorResponse(415, new MessageResponse(ex.getMessage())));
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
}
