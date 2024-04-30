package ua.everybuy.buisnesslogic.service;

import ua.everybuy.database.entity.Role;
import ua.everybuy.database.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
    public Role findByRoleName(String roleName){
        return roleRepository.findByRoleName(roleName).orElseThrow();
    }
}
