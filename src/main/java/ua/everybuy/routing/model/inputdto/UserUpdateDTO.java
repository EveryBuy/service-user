package ua.everybuy.routing.model.inputdto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import ua.everybuy.routing.model.dto.response.ResponseMarker;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Valid
public class UserUpdateDTO implements ResponseMarker {

    @NotNull(message = "field userId not should be null")
    private Long userId;
    @NotNull(message = "field fullName not should be null")
    private String fullName;
    private String userPhotoUrl;
    private String email;
    private String phone;
}
