package ua.everybuy.routing.model.mapper;

import org.springframework.stereotype.Service;
import ua.everybuy.database.entity.User;
import ua.everybuy.routing.model.dto.AuthUserInfoDto;
import ua.everybuy.routing.model.dto.UserDto;
@Service
public class UserMapper {
    public UserDto convertUserToDto(AuthUserInfoDto userInfo, User user){
        return UserDto.builder()
                .userId(user.getId())
                .fullName(user.getFullName())
                .userPhotoUrl(user.getUserPhotoUrl())
                .phone(userInfo.phoneNumber())
                .email(userInfo.email())
                .build();
    }
}
