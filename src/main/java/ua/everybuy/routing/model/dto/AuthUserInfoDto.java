package ua.everybuy.routing.model.dto;

import java.util.List;

public record AuthUserInfoDto(
        Boolean isValid,
         long userId,
         String email,
         String phoneNumber,
         List<String> roles) {
}
