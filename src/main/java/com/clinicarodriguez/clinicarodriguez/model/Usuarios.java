package com.clinicarodriguez.clinicarodriguez.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "usuarios")
public class Usuarios implements Serializable {

    @Id
    @Column(name = "usua_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long usuaId;

    @Column(name = "usua_username", unique = true, nullable = false, length = 50)
    private String usuaUsername;

    @Column(name = "usua_nombrecompleto", nullable = false, length = 150)
    private String usuaNombrecompleto;

    @Column(name = "usua_clave", nullable = false, length = 255)
    private String usuaClave;

    @Column(name = "usua_dni", unique = true, length = 20)
    private String usuaDni;

    @Column(name = "usua_email", unique = true, nullable = false, length = 100)
    private String usuaEmail;

    @Column(name = "usua_telefono", length = 20)
    private String usuaTelefono;

    @Column(name = "usua_foto_url", length = 255)
    private String usuaFotoUrl;

    //@Column(name = "usua_estado", length = 20)
    //private String usuaEstado;

    @Column(name = "usua_es_activo")
    private Boolean usuaEsActivo;

    @Column(name = "usua_ultima_sesion")
    private LocalDateTime usuaUltimaSesion;

    @JsonIgnore
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UsuariosRoles> usuariosRoles = new HashSet<>();

    public Long getUsuaId() {
        return usuaId;
    }

    public void setUsuaId(Long usuaId) {
        this.usuaId = usuaId;
    }

    public String getUsuaUsername() {
        return usuaUsername;
    }

    public void setUsuaUsername(String usuaUsername) {
        this.usuaUsername = usuaUsername;
    }

    public String getUsuaNombrecompleto() {
        return usuaNombrecompleto;
    }

    public void setUsuaNombrecompleto(String usuaNombrecompleto) {
        this.usuaNombrecompleto = usuaNombrecompleto;
    }

    public String getUsuaClave() {
        return usuaClave;
    }

    public void setUsuaClave(String usuaClave) {
        this.usuaClave = usuaClave;
    }

    public String getUsuaDni() {
        return usuaDni;
    }

    public void setUsuaDni(String usuaDni) {
        this.usuaDni = usuaDni;
    }

    public String getUsuaEmail() {
        return usuaEmail;
    }

    public void setUsuaEmail(String usuaEmail) {
        this.usuaEmail = usuaEmail;
    }

    public String getUsuaTelefono() {
        return usuaTelefono;
    }

    public void setUsuaTelefono(String usuaTelefono) {
        this.usuaTelefono = usuaTelefono;
    }

    public String getUsuaFotoUrl() {
        return usuaFotoUrl;
    }

    public void setUsuaFotoUrl(String usuaFotoUrl) {
        this.usuaFotoUrl = usuaFotoUrl;
    }

    public Boolean getUsuaEsActivo() {
        return usuaEsActivo;
    }

    public void setUsuaEsActivo(Boolean usuaEsActivo) {
        this.usuaEsActivo = usuaEsActivo;
    }

    public LocalDateTime getUsuaUltimaSesion() {
        return usuaUltimaSesion;
    }

    public void setUsuaUltimaSesion(LocalDateTime usuaUltimaSesion) {
        this.usuaUltimaSesion = usuaUltimaSesion;
    }

    public Set<UsuariosRoles> getUsuariosRoles() {
        return usuariosRoles;
    }

    public void setUsuariosRoles(Set<UsuariosRoles> usuariosRoles) {
        this.usuariosRoles = usuariosRoles;
    }
}

