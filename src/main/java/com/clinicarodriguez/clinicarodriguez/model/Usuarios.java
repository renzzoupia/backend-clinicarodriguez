package com.clinicarodriguez.clinicarodriguez.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "usuarios")
public class Usuarios implements Serializable {

    @Id
    @Column(name = "usua_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer usuaId;

    @OneToOne
    @JoinColumn(name = "usua_pers_id", unique = true, nullable = false)
    private Personas persona;

    @Column(name = "usua_username", unique = true, nullable = false, length = 60)
    private String usuaUsername;

    @Column(name = "usua_clave", nullable = false, length = 255)
    private String usuaClave;

    @Column(name = "usua_ultima_sesion")
    private LocalDateTime usuaUltimaSesion;

    @Column(name = "usua_estado", nullable = false)
    private Boolean usuaEstado;

    @JsonIgnore
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UsuariosRoles> usuariosRoles = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<ActivosTecnologicos> activosTecnologicos = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Historias> historias = new ArrayList<>();

    // Getters and Setters

    public Integer getUsuaId() {
        return usuaId;
    }

    public void setUsuaId(Integer usuaId) {
        this.usuaId = usuaId;
    }

    public Personas getPersona() {
        return persona;
    }

    public void setPersona(Personas persona) {
        this.persona = persona;
    }

    public String getUsuaUsername() {
        return usuaUsername;
    }

    public void setUsuaUsername(String usuaUsername) {
        this.usuaUsername = usuaUsername;
    }

    public String getUsuaClave() {
        return usuaClave;
    }

    public void setUsuaClave(String usuaClave) {
        this.usuaClave = usuaClave;
    }

    public LocalDateTime getUsuaUltimaSesion() {
        return usuaUltimaSesion;
    }

    public void setUsuaUltimaSesion(LocalDateTime usuaUltimaSesion) {
        this.usuaUltimaSesion = usuaUltimaSesion;
    }

    public Boolean getUsuaEstado() {
        return usuaEstado;
    }

    public void setUsuaEstado(Boolean usuaEstado) {
        this.usuaEstado = usuaEstado;
    }

    public Set<UsuariosRoles> getUsuariosRoles() {
        return usuariosRoles;
    }

    public void setUsuariosRoles(Set<UsuariosRoles> usuariosRoles) {
        this.usuariosRoles = usuariosRoles;
    }

    public List<ActivosTecnologicos> getActivosTecnologicos() {
        return activosTecnologicos;
    }

    public void setActivosTecnologicos(List<ActivosTecnologicos> activosTecnologicos) {
        this.activosTecnologicos = activosTecnologicos;
    }

    public List<Historias> getHistorias() {
        return historias;
    }

    public void setHistorias(List<Historias> historias) {
        this.historias = historias;
    }
}
