package ua.everybuy.errorhandling;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import ua.everybuy.errorhandling.exception.UserNotFoundException;
import ua.everybuy.routing.model.dto.response.ErrorResponse;
import ua.everybuy.routing.model.dto.response.MessageResponse;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ErrorResponse> handleValidationExceptions(HttpStatusCodeException ex) {
        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse(ex.getStatusCode().value(), new MessageResponse(ex.getMessage())));
    }

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleNoElementExceptions(UserNotFoundException ex, HttpServletResponse response) {
        response.setStatus(404);
        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse(404, new MessageResponse(ex.getMessage())));
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
}
