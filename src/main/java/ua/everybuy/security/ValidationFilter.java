package ua.everybuy.security;

import lombok.NonNull;
import ua.everybuy.buisnesslogic.service.RequestSenderService;
import ua.everybuy.routing.model.dto.response.ErrorResponse;
import ua.everybuy.routing.model.dto.response.MessageResponse;
import ua.everybuy.routing.model.dto.response.ValidResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class ValidationFilter extends OncePerRequestFilter {
    private final ObjectMapper objectMapper;
    private final RequestSenderService requestSenderService;


    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {


        try{
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
}

