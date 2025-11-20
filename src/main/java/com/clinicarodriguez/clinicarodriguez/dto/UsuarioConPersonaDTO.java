package com.clinicarodriguez.clinicarodriguez.dto;

import com.clinicarodriguez.clinicarodriguez.model.Personas.TipoDocumento;
import com.clinicarodriguez.clinicarodriguez.model.Personas.Sexo;
import com.clinicarodriguez.clinicarodriguez.model.Personas.EstadoCivil;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class UsuarioConPersonaDTO {
    
    // Datos del usuario
    private Integer usuaId;
    private String username;
    private LocalDateTime ultimaSesion;
    private Boolean estado;
    
    // Datos de la persona
    private Integer persId;
    private String nombrecompleto;
    private TipoDocumento tipoDoc;
    private String nroDoc;
    private Sexo sexo;
    private LocalDate fecNacimiento;
    private EstadoCivil estadoCivil;
    private String telefono;
    private String email;
    private String direccion;
    private String fotoUrl;
    
    // Constructor vacío
    public UsuarioConPersonaDTO() {
    }
    
    // Constructor completo
    public UsuarioConPersonaDTO(Integer usuaId, String username, LocalDateTime ultimaSesion, 
                               Boolean estado, Integer persId, String nombrecompleto, 
                               TipoDocumento tipoDoc, String nroDoc, Sexo sexo, 
                               LocalDate fecNacimiento, EstadoCivil estadoCivil, 
                               String telefono, String email, String direccion, String fotoUrl) {
        this.usuaId = usuaId;
        this.username = username;
        this.ultimaSesion = ultimaSesion;
        this.estado = estado;
        this.persId = persId;
        this.nombrecompleto = nombrecompleto;
        this.tipoDoc = tipoDoc;
        this.nroDoc = nroDoc;
        this.sexo = sexo;
        this.fecNacimiento = fecNacimiento;
        this.estadoCivil = estadoCivil;
        this.telefono = telefono;
        this.email = email;
        this.direccion = direccion;
        this.fotoUrl = fotoUrl;
    }

    // Getters y Setters
    public Integer getUsuaId() {
        return usuaId;
    }

    public void setUsuaId(Integer usuaId) {
        this.usuaId = usuaId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getUltimaSesion() {
        return ultimaSesion;
    }

    public void setUltimaSesion(LocalDateTime ultimaSesion) {
        this.ultimaSesion = ultimaSesion;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Integer getPersId() {
        return persId;
    }

    public void setPersId(Integer persId) {
        this.persId = persId;
    }

    public String getNombrecompleto() {
        return nombrecompleto;
    }

    public void setNombrecompleto(String nombrecompleto) {
        this.nombrecompleto = nombrecompleto;
    }

    public TipoDocumento getTipoDoc() {
        return tipoDoc;
    }

    public void setTipoDoc(TipoDocumento tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    public String getNroDoc() {
        return nroDoc;
    }

    public void setNroDoc(String nroDoc) {
        this.nroDoc = nroDoc;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public LocalDate getFecNacimiento() {
        return fecNacimiento;
    }

    public void setFecNacimiento(LocalDate fecNacimiento) {
        this.fecNacimiento = fecNacimiento;
    }

    public EstadoCivil getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(EstadoCivil estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }
}
