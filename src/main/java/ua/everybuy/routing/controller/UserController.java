package ua.everybuy.routing.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import ua.everybuy.buisnesslogic.service.RequestSenderService;
import ua.everybuy.buisnesslogic.service.UserService;
import ua.everybuy.database.entity.User;
import ua.everybuy.routing.model.dto.response.ErrorResponse;
import ua.everybuy.routing.model.dto.response.MessageResponse;
import ua.everybuy.routing.model.dto.response.StatusResponse;
import ua.everybuy.routing.model.inputdto.UserInputDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.everybuy.routing.model.inputdto.UserUpdateDTO;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final RequestSenderService requestSenderService;

//    @GetMapping("/get-all")
//    public List<UserDTO> getAllUsers(){
//        return userService.getAllUsersDTO();
//    }

    @PostMapping("/save")
    public User saveUser(@RequestBody UserInputDTO userDTO){
        return userService.saveUser(userDTO);
    }

    @GetMapping("/get")
    public ResponseEntity<StatusResponse> getUser(HttpServletRequest request){
        return ResponseEntity.ok(userService.getUserDataResponse(request));
    }

    @PostMapping("/update")
    public ResponseEntity<StatusResponse> updateUser(@RequestBody UserUpdateDTO userUpdateDTO){
        System.out.println(userUpdateDTO);
        return ResponseEntity.ok(userService.updateUser(userUpdateDTO));
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ErrorResponse> handleValidationExceptions(HttpStatusCodeException ex,
                                                                    HttpServletRequest request) {
        System.out.println("IN HANDLER");
        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse(ex.getStatusCode().value(), new MessageResponse(ex.getMessage())));
    }

    @ExceptionHandler({NoSuchElementException.class})
    public ResponseEntity<ErrorResponse> handleNoElementExceptions(NoSuchElementException ex,
                                                                    HttpServletResponse response) {
        System.out.println("IN HANDLER");
        response.setStatus(404);
        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse(404, new MessageResponse(ex.getMessage())));
    }
}
