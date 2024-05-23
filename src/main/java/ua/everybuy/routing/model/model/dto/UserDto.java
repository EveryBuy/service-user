package ua.everybuy.routing.model.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import ua.everybuy.routing.model.model.response.ResponseMarker;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "User details")
public class UserDto implements ResponseMarker {
    @Schema(description = "User ID")
    private Long userId;

    @Schema(description = "Full name of the user")
    private String fullName;

    @Schema(description = "Phone number of the user")
    private String phone;

    @Schema(description = "Email address of the user")
    private String email;

    @Schema(description = "URL of the user's photo")
    private String userPhotoUrl;
}
