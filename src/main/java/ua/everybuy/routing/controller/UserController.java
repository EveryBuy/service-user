package ua.everybuy.routing.controller;


import jakarta.validation.Valid;
import ua.everybuy.buisnesslogic.service.UserService;
import ua.everybuy.routing.model.dto.response.StatusResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.everybuy.routing.model.requet.UpdateUserRequest;


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
    public ResponseEntity<StatusResponse> updateUser(@RequestBody @Valid UpdateUserRequest updateUserRequest){
        return ResponseEntity.ok(userService.updateUser(updateUserRequest));
    }
}
