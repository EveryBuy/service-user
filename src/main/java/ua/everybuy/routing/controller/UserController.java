package ua.everybuy.routing.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import ua.everybuy.buisnesslogic.service.UserService;
import ua.everybuy.routing.model.response.ErrorResponse;
import ua.everybuy.routing.model.response.StatusResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.everybuy.routing.model.request.UpdateUserFullNameRequest;

import java.security.Principal;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "User service", description = "Endpoints for profile management and user data manipulation")
public class UserController {
    private final UserService userService;

    @Operation(summary = "Get user data", description = "Fetch the user data based on the request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved user data",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = StatusResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
    })
    @GetMapping
    public ResponseEntity<StatusResponse> getUser(HttpServletRequest request){
        return ResponseEntity.ok(userService.getUserData(request));
    }

    @Operation(summary = "Update user full name", description = "Update the user's full name based on the provided request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated user full name",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = StatusResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
    })
    @PutMapping("/update-full-name")
    public ResponseEntity<StatusResponse> updateUserFullName(@RequestBody @Valid UpdateUserFullNameRequest updateUserFullNameRequest,
                                                                               Principal principal){
        return ResponseEntity.ok(userService.updateUserFullName(updateUserFullNameRequest, principal));
    }

    @GetMapping("/short-info")
    public  ResponseEntity<StatusResponse> getShortUserInfo(@RequestParam(name = "userId") long userId){
        return ResponseEntity.ok(userService.getShortUserInfo(userId));
    }

    @PostMapping("/create")
    public ResponseEntity<StatusResponse> saveUser(HttpServletRequest request, @RequestParam ("userId") long userId){
        return ResponseEntity.status(200).body(userService.createUser(request, userId));
    }

    @DeleteMapping("/remove")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(HttpServletRequest request,
                                                     @RequestParam(name = "userId") long userId){
        userService.deleteUser(request, userId);
    }

}
