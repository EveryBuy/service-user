package com.everybuy.routing.model.dto;

import com.everybuy.buisnesslogic.service.UserService;
import com.everybuy.database.entity.Role;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;
import java.util.stream.Collectors;


public record RoleDTO(String roleName) {
}
