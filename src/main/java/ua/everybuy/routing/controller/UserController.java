package ua.everybuy.routing.controller;


import jakarta.validation.Valid;
import ua.everybuy.buisnesslogic.service.UserService;
import ua.everybuy.routing.model.model.response.StatusResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.everybuy.routing.model.request.UpdateUserFullNameRequest;

import java.security.Principal;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<StatusResponse> getUser(HttpServletRequest request){
        return ResponseEntity.ok(userService.getUserData(request));
    }

    @PutMapping
    public ResponseEntity<StatusResponse> updateUserFullName(@RequestBody @Valid UpdateUserFullNameRequest updateUserFullNameRequest,
                                                             Principal principal){
        return ResponseEntity.ok(userService.updateUserFullName(updateUserFullNameRequest, principal));
    }


}
