package ua.everybuy.buisnesslogic.service;

import ua.everybuy.database.entity.User;
import ua.everybuy.database.repository.UserRepository;

import ua.everybuy.routing.model.dto.UserDTO;
import ua.everybuy.routing.model.dto.response.StatusResponse;
import ua.everybuy.routing.model.dto.response.ValidResponse;
import ua.everybuy.routing.model.inputdto.UserInputDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.everybuy.routing.model.inputdto.UserUpdateDTO;

import java.util.List;
import java.util.NoSuchElementException;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RequestSenderService requestSenderService;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User saveUser(UserInputDTO userDTO){
        User user  = new User();
        user.setId(userDTO.id());
        return userRepository.save(user);
    }

    public User getUserById(long id){
        return userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("User with id: " + id + " not found"));
    }
    public User getUserByUserName(String username){
        return userRepository.findUserByFullName(username)
                .orElseThrow();
    }

    public StatusResponse getUserDataResponse(HttpServletRequest request) {
        ValidResponse validResponse = requestSenderService.extractValidResponse(request);
        if (validResponse == null){
            throw new RuntimeException("Something went wrong. Please try later.");
        }
        if (!isUserPresent(validResponse.getData().getUserId())){
            saveUser(new UserInputDTO(validResponse.getData().getUserId()));
        }
        UserDTO userDTO = composeUserDTO(validResponse);
        return new StatusResponse(200, userDTO);
    }

    public StatusResponse updateUser(UserUpdateDTO userUpdateDTO){
        User user = getUserById(userUpdateDTO.getUserId());
        user.setFullName(userUpdateDTO.getFullName());
        user.setUserPhotoUrl(userUpdateDTO.getUserPhotoURL());
        userRepository.save(user);
        return new StatusResponse(200, userUpdateDTO);
    }

    private UserDTO composeUserDTO(ValidResponse validResponse){
        User user = getUserById(validResponse.getData().getUserId());
        ValidResponse.UserInfoDTO data = validResponse.getData();
        return new UserDTO(
                user.getId(),
                user.getFullName(),
                data.getPhoneNumber(),
                data.getEmail(),
//                user.getAddress().getCityName(),
                user.getUserPhotoUrl());
    }

    private boolean isUserPresent(long userId){
        return getUserById(userId) != null;
    }
}
