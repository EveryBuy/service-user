package ua.everybuy.routing.model.dto;

import lombok.*;
import ua.everybuy.routing.model.dto.response.ResponseMarker;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO implements ResponseMarker {
    private Long userId;
    private String fullName;
    private String phone;
    private String email;
    private String userPhotoUrl;
}
