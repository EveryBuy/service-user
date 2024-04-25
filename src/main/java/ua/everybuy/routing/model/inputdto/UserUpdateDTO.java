package ua.everybuy.routing.model.inputdto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ua.everybuy.routing.model.dto.response.ResponseMarker;

@Getter
@Setter
@ToString
public class UserUpdateDTO implements ResponseMarker {
    private Long userId;
    private String fullName;
    private String userPhotoURL;
    private String email;
    private String phone;

    public UserUpdateDTO(Long userId, String fullName, String userPhotoURL, String email, String phone) {
        this.userId = userId;
        this.fullName = fullName;
        this.userPhotoURL = userPhotoURL;
        this.email = email;
        this.phone = phone;
    }
}
