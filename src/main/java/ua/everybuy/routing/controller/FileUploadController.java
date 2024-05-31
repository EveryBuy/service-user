package ua.everybuy.routing.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.everybuy.buisnesslogic.service.PhotoService;
import ua.everybuy.routing.model.response.ErrorResponse;
import ua.everybuy.routing.model.response.StatusResponse;

import java.io.IOException;
import java.security.Principal;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "User service", description = "Endpoints for user photo management")
public class FileUploadController {
    private final PhotoService photoService;

    @Operation(summary = "Upload a photo. Parameter for MultiPartFile name = 'photo'")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Photo uploaded successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StatusResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Null file value",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "415", description = "Invalid file format",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Aws server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/photo-upload")
    public StatusResponse uploadPhoto(
            Principal principal,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "The photo file to upload",
                    required = true,
                    content = @Content(mediaType = "multipart/form-data",
                            schema = @Schema(type = "string", format = "binary")))
            @RequestParam(name = "photo")
            MultipartFile photo
    ) throws IOException {
        return photoService.handlePhotoUpload(photo, principal);
    }

}