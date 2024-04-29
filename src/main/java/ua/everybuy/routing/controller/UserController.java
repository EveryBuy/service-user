package ua.everybuy.routing.controller;


import jakarta.validation.Valid;
import ua.everybuy.buisnesslogic.service.UserService;
import ua.everybuy.routing.model.dto.response.StatusResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.everybuy.routing.model.inputdto.UserUpdateDTO;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/get")
    public ResponseEntity<StatusResponse> getUser(HttpServletRequest request){
        return ResponseEntity.ok(userService.getUserData(request));
    }

    @PostMapping("/update")
    public ResponseEntity<StatusResponse> updateUser(@RequestBody @Valid UserUpdateDTO userUpdateDTO){
        System.out.println(userUpdateDTO);
        return ResponseEntity.ok(userService.updateUser(userUpdateDTO));
    }

}
