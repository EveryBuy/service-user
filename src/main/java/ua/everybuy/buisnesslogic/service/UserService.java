package ua.everybuy.buisnesslogic.service;

import ua.everybuy.database.entity.User;
import ua.everybuy.database.repository.UserRepository;

import ua.everybuy.errorhandling.exception.UserNotFoundException;
import ua.everybuy.routing.model.dto.AuthUserInfoDto;
import ua.everybuy.routing.model.dto.UserDTO;
import ua.everybuy.routing.model.dto.response.StatusResponse;
import ua.everybuy.routing.model.inputdto.UserUpdateDTO;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RequestSenderService requestSenderService;

    public void createUser(long userId) {
        User user = new User(userId); // Consider adding required fields
        userRepository.save(user);
    }

    public User getUserById(long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }

    public StatusResponse getUserData(HttpServletRequest request) {
        AuthUserInfoDto userInfo = extractAuthUserInfo(request);
        if (!userRepository.existsById(userInfo.userId())){
                    createUser(userInfo.userId());
        }

        UserDTO userDTO = composeUserDTO(userInfo);
        return new StatusResponse(200, userDTO);
    }

    private AuthUserInfoDto extractAuthUserInfo(HttpServletRequest request) {
        return requestSenderService.extractValidResponse(request).getData(); // Handle potential exceptions
    }

    public StatusResponse updateUser(UserUpdateDTO userUpdateDTO) {
        updateDetails(userUpdateDTO);
        return new StatusResponse(200, userUpdateDTO); // Consider returning updated user info if relevant
    }

    private void updateDetails(UserUpdateDTO userUpdateDTO) {
        User user = getUserById(userUpdateDTO.getUserId());
        user.setUserPhotoUrl(userUpdateDTO.getUserPhotoUrl());
        user.setFullName(user.getFullName());
        userRepository.save(user);
    }

    private UserDTO composeUserDTO(AuthUserInfoDto userInfo) {
        User user = getUserById(userInfo.userId());
        return UserDTO.builder()
                .userId(user.getId())
                .fullName(user.getFullName())
                .userPhotoUrl(user.getUserPhotoUrl())
                .phone(userInfo.phoneNumber())
                .email(userInfo.email())
                .build();
    }
}
