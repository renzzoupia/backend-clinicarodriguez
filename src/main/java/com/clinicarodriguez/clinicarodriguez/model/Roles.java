package com.clinicarodriguez.clinicarodriguez.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "roles")
public class Roles implements Serializable {

    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    @Column(name = "role_name", unique = true, nullable = false, length = 50)
    private String roleName;

    @Column(name = "role_descripcion", length = 200)
    private String roleDescripcion;

    @JsonIgnore
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UsuariosRoles> usuariosRoles = new HashSet<>();

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDescripcion() {
        return roleDescripcion;
    }

    public void setRoleDescripcion(String roleDescripcion) {
        this.roleDescripcion = roleDescripcion;
    }

    public Set<UsuariosRoles> getUsuariosRoles() {
        return usuariosRoles;
    }

    public void setUsuariosRoles(Set<UsuariosRoles> usuariosRoles) {
        this.usuariosRoles = usuariosRoles;
    }
}
