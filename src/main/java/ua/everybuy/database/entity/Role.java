package ua.everybuy.database.entity;

import ua.everybuy.routing.model.dto.RoleDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "role")
@Getter
@Setter
public class Role {

    @Id
    @Column (name = "userId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "role_name")
    private String roleName;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private Set<User> users = new HashSet<>();

    public RoleDTO convertToRoleDTO(){
        return new RoleDTO(this.roleName);
    }

}
