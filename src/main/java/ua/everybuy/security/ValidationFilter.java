package ua.everybuy.security;

import lombok.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import ua.everybuy.buisnesslogic.service.RequestSenderService;
import ua.everybuy.routing.model.model.response.ErrorResponse;
import ua.everybuy.routing.model.model.response.MessageResponse;
import ua.everybuy.routing.model.model.dto.ValidRequestDto;
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
        String userId;
        ValidRequestDto validRequest = null;

        try{
            ResponseEntity<ValidRequestDto> exchange = requestSenderService.doRequest(request);
            System.out.println(exchange.getBody());
            validRequest = exchange.getBody();
        }
        catch (HttpClientErrorException e) {
            int statusCode =  e.getStatusCode().value();
            extractErrorMessage(response, e, statusCode);
        }
        userId = String.valueOf(validRequest.getData().userId());

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userId, null,
                validRequest.getData().roles().stream().map(SimpleGrantedAuthority::new).toList());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        context.setAuthentication(authToken);
        SecurityContextHolder.setContext(context);
        filterChain.doFilter(request, response);
    }

    private void extractErrorMessage(HttpServletResponse response, RuntimeException e, int statusCode) throws IOException {
//        statusCode = statusCode == 403 ? 401 : statusCode;
        String message = statusCode == 401 ? "Unauthorized" : e.getMessage();
        ErrorResponse errorResponse = new ErrorResponse(statusCode, new MessageResponse(message));
        String json = objectMapper.writeValueAsString(errorResponse);
        response.setStatus(statusCode);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(json);
    }
}

