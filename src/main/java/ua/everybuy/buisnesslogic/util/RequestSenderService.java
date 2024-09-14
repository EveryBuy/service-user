package ua.everybuy.buisnesslogic.util;


import ua.everybuy.routing.model.dto.ShortUserInfoDto;
import ua.everybuy.routing.model.dto.ValidRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpHeaders;

@Service
@RequiredArgsConstructor
public class RequestSenderService {
    private final static String AUTH_HEADER_PREFIX = "Authorization";

    @Value("${auth.service.url}")
    private String authServiceUrl;


    public ResponseEntity<ValidRequestDto> doRequest(HttpServletRequest request){
        final RestTemplate restTemplate = new RestTemplate();
        String authHeader = request.getHeader(AUTH_HEADER_PREFIX);

        HttpEntity<HttpHeaders> requestEntity = getHttpHeadersHttpEntity(authHeader);
        return restTemplate.exchange(
                authServiceUrl,
                HttpMethod.GET,
                requestEntity,
                ValidRequestDto.class);
    }

    public void sendEmptyRequestToWakeUpService(String url){
        final RestTemplate restTemplate = new RestTemplate();
        restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                String.class);
    }

    public void sendInfoAboutChange(String url, ShortUserInfoDto userInfoDto){
        final RestTemplate restTemplate = new RestTemplate();
        HttpEntity<ShortUserInfoDto> requestEntity = new HttpEntity<>(userInfoDto);
        restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                String.class);
    }

    private static HttpEntity<HttpHeaders> getHttpHeadersHttpEntity(String authHeader) {
        MultiValueMap<String, String> header = new LinkedMultiValueMap<>();
        header.add(AUTH_HEADER_PREFIX, authHeader);
        return new HttpEntity<>(null, header);
    }

    public ValidRequestDto extractValidResponse(HttpServletRequest request){
        return doRequest(request).getBody();
     }
}
