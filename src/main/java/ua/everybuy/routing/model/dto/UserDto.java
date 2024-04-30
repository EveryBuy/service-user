package ua.everybuy.routing.model.dto;

import lombok.*;
import ua.everybuy.routing.model.dto.response.ResponseMarker;
import ua.everybuy.routing.model.requet.UpdateUserRequest;

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

    public static UserDto convertUpdateRequestToUserDto(final UpdateUserRequest updateUserRequest){
        return UserDto.builder()
                .userId(updateUserRequest.userId())
                .fullName(updateUserRequest.fullName())
                .phone(updateUserRequest.phone())
                .email(updateUserRequest.email())
                .userPhotoUrl(updateUserRequest.userPhotoUrl())
                .build();
    }
}
