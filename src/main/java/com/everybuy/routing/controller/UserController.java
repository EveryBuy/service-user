package com.everybuy.routing.controller;

import com.everybuy.buisnesslogic.service.RequestSenderService;
import com.everybuy.buisnesslogic.service.UserService;
import com.everybuy.database.entity.User;
import com.everybuy.routing.model.dto.AddressDTO;
import com.everybuy.routing.model.dto.UserDTO;
import com.everybuy.routing.model.dto.response.ErrorResponse;
import com.everybuy.routing.model.dto.response.MessageResponse;
import com.everybuy.routing.model.dto.response.StatusResponse;
import com.everybuy.routing.model.dto.response.ValidResponse;
import com.everybuy.routing.model.inputdto.UserInputDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(RuntimeException ex, HttpServletRequest request) {
        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse(503, new MessageResponse(ex.getMessage())));
    }
}
