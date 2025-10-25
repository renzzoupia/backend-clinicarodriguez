package com.clinicarodriguez.clinicarodriguez.model;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "usuarios_roles")
public class UsuariosRoles implements Serializable {

    @Id
    @Column(name = "usro_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long usroId;

    @ManyToOne
    @JoinColumn(name = "usro_usua_id", nullable = false)
    private Usuarios usuario;

    @ManyToOne
    @JoinColumn(name = "usro_role_id", nullable = false)
    private Roles role;

    public Long getUsroId() {
        return usroId;
    }

    public void setUsroId(Long usroId) {
        this.usroId = usroId;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }
}
