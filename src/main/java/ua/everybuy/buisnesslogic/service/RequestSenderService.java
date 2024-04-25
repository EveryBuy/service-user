package ua.everybuy.buisnesslogic.service;

import ua.everybuy.routing.model.dto.response.ValidResponse;
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

    public ResponseEntity<ValidResponse> doRequest(HttpServletRequest request){
        final RestTemplate restTemplate = new RestTemplate();
        String authHeader = request.getHeader(AUTH_HEADER_PREFIX);

        HttpEntity<HttpHeaders> requestEntity = getHttpHeadersHttpEntity(authHeader);
        return restTemplate.exchange(
                authServiceUrl,
                HttpMethod.GET,
                requestEntity,
                ValidResponse.class);
    }

    private static HttpEntity<HttpHeaders> getHttpHeadersHttpEntity(String authHeader) {
        MultiValueMap<String, String> header = new LinkedMultiValueMap<>();
        header.add(AUTH_HEADER_PREFIX, authHeader);
        return new HttpEntity<>(null, header);
    }

    public ValidResponse extractValidResponse(HttpServletRequest request){
        return doRequest(request).getBody();
     }
}
