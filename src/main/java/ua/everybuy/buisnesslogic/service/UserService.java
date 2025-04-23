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
import ua.everybuy.routing.model.mapper.UserMapper;
import ua.everybuy.routing.model.response.resposedataimpl.UserCreatedResponse;
import ua.everybuy.routing.model.dto.UserDto;
import ua.everybuy.routing.model.response.resposedataimpl.FullNameResponse;
import ua.everybuy.routing.model.response.resposedataimpl.StatusResponse;
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
    private final UserMapper userMapper;

    @Value("${service.password.value}")
    private String servicePassword;
    @Value("${chat.service.change.info.url}")
    private String chatServiceChangeUserInfoUrl;

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
        UserDto userDTO;
        // temporally solution in the case when userId not found, because we also auth-service trust))
        try {
            userDTO = composeUserDTO(extractAuthUserInfo(request));
        } catch (UserNotFoundException ex) {
            User user = new User(extractAuthUserInfo(request).userId());
            userRepository.save(user);
            userDTO = composeUserDTO(extractAuthUserInfo(request));
        }

        return new StatusResponse(HttpStatus.OK.value(), userDTO);
    }

    public void updatePhotoUrl(String url, Principal principal) {
        User user = getOrCreateUser(principal);
        user.setUserPhotoUrl(url);
        user.onUpdate();
        userRepository.save(user);
        sendInfoAboutChangesToChatService(user);
    }

    public StatusResponse updateUserFullName(UpdateUserFullNameRequest updateUserFullNameRequest, Principal principal) {
        User user = getOrCreateUser(principal);
        user.setFullName(updateUserFullNameRequest.fullName());
        userRepository.save(user);
        FullNameResponse fullNameResponse = new FullNameResponse(user.getFullName());
        sendInfoAboutChangesToChatService(user);
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
        return userMapper.convertUserToDto(userInfo, user);
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

    private void sendInfoAboutChangesToChatService(User user) {
        requestSenderService.sendInfoAboutChange(chatServiceChangeUserInfoUrl,
                new ShortUserInfoDto(user.getId(), user.getFullName(), user.getUserPhotoUrl()));
    }

    private void saveUserWhenNotExist(long userId) {
        if (!userRepository.existsById(userId)) {
            saveUser(userId);
        }
    }

    private void saveUser(long userId) {
        userRepository.save(new User(userId));
    }

    private long extractUserId(Principal principal) {
        return Long.parseLong(principal.getName());
    }

    private User getOrCreateUser(Principal principal) {
        long userId = extractUserId(principal);
        saveUserWhenNotExist(userId);
        return getUserById(userId);
    }
}
