package ua.everybuy.routing.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/keep-alive")
public class KeepAliveController {
    @GetMapping()
    public String keepAlive(){
        return "User service wake up";
    }
}
