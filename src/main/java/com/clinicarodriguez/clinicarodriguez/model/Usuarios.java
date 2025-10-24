package com.clinicarodriguez.clinicarodriguez.model;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "usuarios")
public class Usuarios implements Serializable {

    @Id
    @Column(name = "usua_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long usuaId;

    @Column(name = "usua_nombre")
    private String usuaNombre;

    @Column(name = "usua_email")
    private String usuaEmail;

    @Column(name = "usua_password")
    private String usuaPassword;

    @Column(name = "usua_rol")
    private String usuaRol;

    @Column(name = "usua_estado")
    private String usuaEstado;

    public Long getUsuaId() {
        return usuaId;
    }

    public void setUsuaId(Long usuaId) {
        this.usuaId = usuaId;
    }

    public String getUsuaNombre() {
        return usuaNombre;
    }

    public void setUsuaNombre(String usuaNombre) {
        this.usuaNombre = usuaNombre;
    }

    public String getUsuaEmail() {
        return usuaEmail;
    }

    public void setUsuaEmail(String usuaEmail) {
        this.usuaEmail = usuaEmail;
    }

    public String getUsuaPassword() {
        return usuaPassword;
    }

    public void setUsuaPassword(String usuaPassword) {
        this.usuaPassword = usuaPassword;
    }

    public String getUsuaRol() {
        return usuaRol;
    }

    public void setUsuaRol(String usuaRol) {
        this.usuaRol = usuaRol;
    }

    public String getUsuaEstado() {
        return usuaEstado;
    }

    public void setUsuaEstado(String usuaEstado) {
        this.usuaEstado = usuaEstado;
    }
}

