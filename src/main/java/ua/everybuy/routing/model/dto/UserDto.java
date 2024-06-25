package ua.everybuy.routing.model.dto;

import lombok.*;
import ua.everybuy.routing.model.response.ResponseMarker;

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

    public UserDto (long userId){
        this.userId = userId;
    }
}
