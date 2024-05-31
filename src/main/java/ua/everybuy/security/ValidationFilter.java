package ua.everybuy.security;

import lombok.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import ua.everybuy.buisnesslogic.util.RequestSenderService;
import ua.everybuy.routing.model.response.ErrorResponse;
import ua.everybuy.routing.model.response.MessageResponse;
import ua.everybuy.routing.model.dto.ValidRequestDto;
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
import java.util.List;

@Component
@RequiredArgsConstructor
public class ValidationFilter extends OncePerRequestFilter {
    private final ObjectMapper objectMapper;
    private final RequestSenderService requestSenderService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        if (shouldNotFilter(request)) {
//            filterChain.doFilter(request, response);
            return;
        }

        String userId;
        ValidRequestDto validRequest;

        try{
            ResponseEntity<ValidRequestDto> exchange = requestSenderService.doRequest(request);
            validRequest = exchange.getBody();
        }
        catch (HttpClientErrorException e) {
            int statusCode =  e.getStatusCode().value();
            extractErrorMessage(response, e, statusCode);
            return;
        }

        if(validRequest != null){
            userId = String.valueOf(validRequest.getData().userId());
            List<SimpleGrantedAuthority> grantedAuthorities = validRequest.getData().roles()
                    .stream()
                    .map(SimpleGrantedAuthority::new)
                    .toList();
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userId, null,
                    grantedAuthorities);

            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            context.setAuthentication(authToken);
            SecurityContextHolder.setContext(context);
            filterChain.doFilter(request, response);
        }

    }
    @Override
    public boolean shouldNotFilter(HttpServletRequest request) {
        return request.getRequestURI().startsWith("/swagger")
                || request.getRequestURI().startsWith("/v3");
    }


    private void extractErrorMessage(HttpServletResponse response, RuntimeException e, int statusCode) throws IOException {
        String message = statusCode == 401 ? "Unauthorized" : e.getMessage();
        ErrorResponse errorResponse = new ErrorResponse(statusCode, new MessageResponse(message));
        String json = objectMapper.writeValueAsString(errorResponse);
        response.setStatus(statusCode);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(json);
    }
}

