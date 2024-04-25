package ua.everybuy.routing.model.dto;

import ua.everybuy.routing.model.dto.response.ResponseMarker;

public record UserDTO(Long userId,
                      String fullName,
                      String phone,
                      String email,
//                      String address,
                      String userPhotoUrl) implements ResponseMarker {
}
