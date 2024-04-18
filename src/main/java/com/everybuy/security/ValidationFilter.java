package com.everybuy.security;

import com.everybuy.buisnesslogic.service.RequestSenderService;
import com.everybuy.routing.model.dto.response.ErrorResponse;
import com.everybuy.routing.model.dto.response.MessageResponse;
import com.everybuy.routing.model.dto.response.ValidResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.net.http.HttpHeaders;

@Component
@RequiredArgsConstructor
public class ValidationFilter extends OncePerRequestFilter {
    private final static String AUTH_HEADER_PREFIX = "Authorization";
    private final ObjectMapper objectMapper;
    private final RequestSenderService requestSenderService;

    @Value("${auth.service.url}")
    private String authServiceUrl;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

//        final RestTemplate restTemplate = new RestTemplate();
//        String authHeader = request.getHeader(AUTH_HEADER_PREFIX);
//        HttpEntity<HttpHeaders> requestEntity = getHttpHeadersHttpEntity(authHeader);

        try{
//            ResponseEntity<ValidResponse> exchange = restTemplate.exchange(
//                    authServiceUrl,
//                    HttpMethod.GET,
//                    requestEntity,
//                    ValidResponse.class);
            ResponseEntity<ValidResponse> exchange = requestSenderService.doRequest(request);
            System.out.println(exchange.getBody());
            filterChain.doFilter(request, response);
        }
        catch (HttpClientErrorException e) {
            int statusCode = e.getStatusCode().value();
            extractErrorMessage(response, e, statusCode);
        }catch (RuntimeException e){
            int statusCode = 503;
            extractErrorMessage(response, e, statusCode);
        }

    }

    private void extractErrorMessage(HttpServletResponse response, RuntimeException e, int statusCode) throws IOException {
        String message = e.getMessage();

        ErrorResponse errorResponse = new ErrorResponse(statusCode, new MessageResponse(message));
        String json = objectMapper.writeValueAsString(errorResponse);
        response.setStatus(statusCode);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(json);
    }

//    private static HttpEntity<HttpHeaders> getHttpHeadersHttpEntity(String authHeader) {
//        MultiValueMap<String, String> header = new LinkedMultiValueMap<>();
//        header.add(AUTH_HEADER_PREFIX, authHeader);
//        return new HttpEntity<>(null, header);
//    }
}

