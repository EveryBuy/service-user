package com.everybuy.routing.model.dto;

import com.everybuy.database.entity.Address;
import com.everybuy.database.entity.User;
import com.everybuy.routing.model.dto.response.ResponseMarker;
import lombok.Setter;

import java.util.Set;
import java.util.stream.Collectors;

public record UserDTO(String fullName,
                      String phone,
                      String email,
                      String address,
                      String deliveryAddress) implements ResponseMarker {
}
