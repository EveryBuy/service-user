package ua.everybuy.security;

import lombok.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
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
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class ValidationFilter extends OncePerRequestFilter {
    private final ObjectMapper objectMapper;
    private final RequestSenderService requestSenderService;
    private static final List<RequestMatcher> EXCLUDED_PATH_PATTERNS = List.of(
            new AntPathRequestMatcher ("/swagger/**"),
            new AntPathRequestMatcher ("/swagger-ui/**"),
            new AntPathRequestMatcher ("/v3/**"),
            new AntPathRequestMatcher ("/user/short-info"),
            new AntPathRequestMatcher ("/user/create"),
            new AntPathRequestMatcher ("/user/remove"),
            new AntPathRequestMatcher ("/user/keep-alive")
    );

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
    public boolean shouldNotFilter(@NonNull HttpServletRequest request) {
        return EXCLUDED_PATH_PATTERNS
                .stream()
                .anyMatch(pattern -> pattern.matches(request));
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

