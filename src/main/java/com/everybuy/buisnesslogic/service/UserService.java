package com.everybuy.buisnesslogic.service;

import com.everybuy.database.entity.Role;
import com.everybuy.database.entity.User;
import com.everybuy.database.repository.UserRepository;
import com.everybuy.routing.model.dto.AddressDTO;
import com.everybuy.routing.model.dto.UserDTO;
import com.everybuy.routing.model.dto.response.StatusResponse;
import com.everybuy.routing.model.dto.response.ValidResponse;
import com.everybuy.routing.model.inputdto.UserInputDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


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
        return userRepository.findById(id).orElseThrow();
    }
    public User getUserByUserName(String username){
        return userRepository.findUserByFullName(username).orElseThrow();
    }


//    public UserDTO convertToUserDTO(User user){
//        return new UserDTO(
//                user.getFullName(),
//                user.getEmail(),
//                user.getPhone(),
//                AddressDTO.getAddressDTO(user.getAddress()).getFullAddress());
//
//    }
//
//    public User convertToUser(UserDTO userDTO){
//        return getUserByUserName(userDTO.fullName());
//    }
//
//    public List<UserDTO> getAllUsersDTO() {
//        return getAllUsers()
//                .stream()
//                .map(this::convertToUserDTO)
//                .toList();
//    }

    public StatusResponse getUserDataResponse(HttpServletRequest request) {
        ValidResponse validResponse = requestSenderService.extractValidResponse(request);
        if (validResponse == null){
            throw new RuntimeException("Something went wrong. Please try later.");
        }
        UserDTO userDTO = composeUserDTO(validResponse);
        return new StatusResponse(200, userDTO);
    }

    private UserDTO composeUserDTO(ValidResponse validResponse){
        User user = getUserById(validResponse.getData().getUserId());
        ValidResponse.UserInfoDTO data = validResponse.getData();
        return new UserDTO(user.getFullName(),
                data.getPhoneNumber(),
                data.getEmail(),
                AddressDTO.getAddressDTO(user.getAddress()).getFullAddress(),
                user.getDeliveryAddress());
    }
}
