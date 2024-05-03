package ua.everybuy.routing.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.everybuy.buisnesslogic.service.PhotoService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

//todo raw logic need to refine
@RestController
@RequestMapping
@RequiredArgsConstructor
public class FileUploadController {

    private static String UPLOADED_FOLDER = "";
    private final PhotoService photoService;

    //    @PostMapping("/upload")
//    public String singleFileUpload(@RequestParam MultipartFile file, RedirectAttributes redirectAttributes) {
//        if (file == null) {
////            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
//            return "fail";
//        }
//
//        try {
//            byte[] bytes = file.getBytes();
//            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
//            Files.write(path, bytes);
//
////            redirectAttributes.addFlashAttribute("message",
////                    "You successfully uploaded '" + file.getOriginalFilename() + "'");
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return "ok";
//    }
    @PostMapping("/upload")
    public String uploadPhoto(@RequestParam MultipartFile photo, @RequestParam Long userId) throws IOException {
        return photoService.handlePhotoUpload(photo, userId);
    }

    @RequestMapping("/uploadStatus")
    public String uploadStatus() {
        return "uploadStatus";
    }
}