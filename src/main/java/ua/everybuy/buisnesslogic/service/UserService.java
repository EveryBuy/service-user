package ua.everybuy.buisnesslogic.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import ua.everybuy.database.entity.User;
import ua.everybuy.database.repository.UserRepository;

import ua.everybuy.errorhandling.exception.UserNotFoundException;
import ua.everybuy.routing.model.dto.AuthUserInfoDto;
import ua.everybuy.routing.model.dto.UserDto;
import ua.everybuy.routing.model.dto.response.StatusResponse;
import ua.everybuy.routing.model.requet.UpdateUserRequest;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RequestSenderService requestSenderService;

    public void createUser(long userId) {
        User user = new User(userId);
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

        UserDto userDTO = composeUserDTO(userInfo);
        return new StatusResponse(200, userDTO);
    }

    private AuthUserInfoDto extractAuthUserInfo(HttpServletRequest request) {
        return requestSenderService.extractValidResponse(request).getData();
    }

    public StatusResponse updateUser(UpdateUserRequest userUpdateRequest) {
        return new StatusResponse(200, updateDetails(userUpdateRequest));
    }


    public void updatePhotoUrl(String url, long userId){
        User user = getUserById(userId);
        user.setUserPhotoUrl(url);
        userRepository.save(user);
    }

    private UserDto updateDetails(UpdateUserRequest updateUserRequest) {
        User user = getUserById(updateUserRequest.userId());
//        user.setUserPhotoUrl(updateUserRequest.userPhotoUrl());
        user.setFullName(updateUserRequest.fullName());
        userRepository.save(user);
        return UserDto.convertUpdateRequestToUserDto(updateUserRequest);
    }

    private UserDto composeUserDTO(AuthUserInfoDto userInfo) {
        User user = getUserById(userInfo.userId());
        return UserDto.builder()
                .userId(user.getId())
                .fullName(user.getFullName())
                .userPhotoUrl(user.getUserPhotoUrl())
                .phone(userInfo.phoneNumber())
                .email(userInfo.email())
                .build();
    }
}
