package ua.everybuy.routing.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ua.everybuy.buisnesslogic.service.EmailSenderService;
import ua.everybuy.routing.model.request.NewsLetterRequest;
import ua.everybuy.routing.model.response.resposedataimpl.StatusResponse;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class SendNewsController {
   private final EmailSenderService emailSenderService;

   @PostMapping("/send-news")
   @ResponseBody
   @ResponseStatus(HttpStatus.OK)
    public StatusResponse sendNewsLetters(@RequestBody NewsLetterRequest newsLetterRequest){
        return emailSenderService.sendNewsLetters(newsLetterRequest);
    }
}
