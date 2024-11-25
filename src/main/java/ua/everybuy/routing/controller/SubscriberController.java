package ua.everybuy.routing.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ua.everybuy.buisnesslogic.service.SubscriberService;
import ua.everybuy.routing.model.request.SubscriberRequest;
import ua.everybuy.routing.model.response.resposedataimpl.StatusResponse;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class SubscriberController {
    private final SubscriberService subscriberService;

    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/add-subscriber")
    public StatusResponse addSubscriber(@RequestBody @Valid SubscriberRequest request){
        return subscriberService.addEmailToMailing(request);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete-subscriber")
    public void deleteSubscriber(@RequestBody @Valid SubscriberRequest request){
        subscriberService.deleteSubscribe(request);
    }
}
