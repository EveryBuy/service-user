package ua.everybuy.routing.model.model.dto;

import lombok.*;
import ua.everybuy.routing.model.model.response.ResponseMarker;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto implements ResponseMarker {
    private Long userId;
    private String fullName;
    private String phone;
    private String email;
    private String userPhotoUrl;
}
