package ua.everybuy.buisnesslogic.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ua.everybuy.buisnesslogic.util.RequestSenderService;

@Service
@RequiredArgsConstructor
public class WakeUpService {
    private final RequestSenderService requestSenderService;
    @Value("${ad.service.wakeup.url}")
    private String adServiceUrl;
    @Value("${auth.service.wakeup.url}")
    private String authServiceUrl;

    @Scheduled(fixedRate = 150000)
    public void wakeUpAuthService(){
        System.out.println("I send wake up request to auth service");
        requestSenderService.sendEmptyRequestToWakeUpService(authServiceUrl);
    }

    @Scheduled(fixedRate = 150000)
    public void wakeUpAdService(){
        System.out.println("I send wake up request to ad service");
        requestSenderService.sendEmptyRequestToWakeUpService(adServiceUrl);
    }
}
