package ua.everybuy.routing.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping
public class FileUploadController {

    private static String UPLOADED_FOLDER = "";

    @PostMapping("/upload")
    public String singleFileUpload(@RequestParam MultipartFile file, RedirectAttributes redirectAttributes) {
        if (file == null) {
//            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "fail";
        }

        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);

//            redirectAttributes.addFlashAttribute("message",
//                    "You successfully uploaded '" + file.getOriginalFilename() + "'");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "ok";
    }

    @RequestMapping("/uploadStatus")
    public String uploadStatus() {
        return "uploadStatus";
    }
}