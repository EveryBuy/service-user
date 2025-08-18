package ua.everybuy.buisnesslogic.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ua.everybuy.errorhandling.exception.impl.PasswordValidException;

@Service
public class InternalValidateService {
    @Value("${service.password.value}")
    private String servicePassword;

    public void validatePassword(HttpServletRequest request) {
        String servicePassword = request.getHeader("Service-Password");
        if (servicePassword == null || !servicePassword.equals(this.servicePassword)) {
            throw new PasswordValidException();
        }
    }
}
