package ua.everybuy.buisnesslogic.service;

import ua.everybuy.database.entity.Role;
import ua.everybuy.database.entity.User;
import ua.everybuy.database.repository.RoleRepository;
import ua.everybuy.routing.model.dto.RoleDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role getRoleById(long id){
        return roleRepository.findById(id).orElseThrow();
    }

    public List<Role> getAllRoles(){
        return roleRepository.findAll();
    }

    public RoleDTO convertToRoleDTO(Role role){
        Set<Long> userIds = role.getUsers()
                .stream()
                .map(User::getId)
                .collect(Collectors.toSet());
        return new RoleDTO(role.getRoleName());
    }

    public Role findByRoleName(String roleName){
        return roleRepository.findByRoleName(roleName).orElseThrow();
    }

    public Role convertFromRoleDTO(RoleDTO roleDTO){
        return findByRoleName(roleDTO.roleName());
    }
}
