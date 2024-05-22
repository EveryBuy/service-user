package ua.everybuy.routing.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.everybuy.buisnesslogic.service.PhotoService;
import ua.everybuy.routing.model.model.response.StatusResponse;

import java.io.IOException;
import java.security.Principal;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class FileUploadController {
    private final PhotoService photoService;

    @PostMapping("/photo-upload")
    public StatusResponse uploadPhoto(@RequestParam MultipartFile photo, Principal principal) throws IOException {
        return photoService.handlePhotoUpload(photo, principal);
    }

}