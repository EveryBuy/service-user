package ua.everybuy.buisnesslogic.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import ua.everybuy.buisnesslogic.util.RequestSenderService;
import ua.everybuy.database.entity.User;
import ua.everybuy.database.repository.UserRepository;

import ua.everybuy.errorhandling.exception.impl.PasswordValidException;
import ua.everybuy.errorhandling.exception.impl.UserAlreadyExistsException;
import ua.everybuy.errorhandling.exception.impl.UserNotFoundException;
import ua.everybuy.routing.model.dto.AuthUserInfoDto;
import ua.everybuy.routing.model.dto.ShortUserInfoDto;
import ua.everybuy.routing.model.response.UserCreatedResponse;
import ua.everybuy.routing.model.dto.UserDto;
import ua.everybuy.routing.model.response.FullNameResponse;
import ua.everybuy.routing.model.response.StatusResponse;
import ua.everybuy.routing.model.request.UpdateUserFullNameRequest;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RequestSenderService requestSenderService;
    @Value("${service.password.value}")
    private String servicePassword;

    public StatusResponse createUser(HttpServletRequest request, long userId) {
        validatePassword(request);

        if (userRepository.existsById(userId)) {
            throw new UserAlreadyExistsException(userId);
        }

        User user = new User(userId);
        long savedUserId = userRepository.save(user).getId();

        return new StatusResponse(HttpStatus.OK.value(), new UserCreatedResponse(savedUserId));
    }

    public StatusResponse getUserData(HttpServletRequest request) {
        UserDto userDTO = composeUserDTO(extractAuthUserInfo(request));
        return new StatusResponse(HttpStatus.OK.value(), userDTO);
    }

    public void updatePhotoUrl(String url, long userId) {
        User user = getUserById(userId);
        user.setUserPhotoUrl(url);
        user.onUpdate();
        userRepository.save(user);
    }

    public StatusResponse updateUserFullName(UpdateUserFullNameRequest updateUserFullNameRequest, Principal principal) {
        User user = getUserById(Long.parseLong(principal.getName()));
        user.setFullName(updateUserFullNameRequest.fullName());
        userRepository.save(user);
        FullNameResponse fullNameResponse = new FullNameResponse(user.getFullName());
        return new StatusResponse(HttpStatus.OK.value(), fullNameResponse);
    }

    public StatusResponse getShortUserInfo(long userId) {
        User user = getUserById(userId);
        return new StatusResponse(HttpStatus.OK.value(),
                new ShortUserInfoDto(user.getId(), user.getFullName(), user.getUserPhotoUrl()));
    }

    public void deleteUser(HttpServletRequest request, long userId) {
        validatePassword(request);
        User user = getUserById(userId);
        userRepository.delete(user);
    }

    private User getUserById(long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
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

    private void validatePassword(HttpServletRequest request) {
        String servicePassword = request.getHeader("Service-Password");
        if (!servicePassword.equals(this.servicePassword)) {
            throw new PasswordValidException();
        }
    }

    private AuthUserInfoDto extractAuthUserInfo(HttpServletRequest request) {
        return requestSenderService.extractValidResponse(request).getData();
    }
}
