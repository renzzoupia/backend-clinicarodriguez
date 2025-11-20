package com.clinicarodriguez.clinicarodriguez.dto;

import com.clinicarodriguez.clinicarodriguez.model.Personas.TipoDocumento;
import com.clinicarodriguez.clinicarodriguez.model.Personas.Sexo;
import com.clinicarodriguez.clinicarodriguez.model.Personas.EstadoCivil;
import java.time.LocalDate;

public class RegistrarPacienteDTO {
    
    // Datos de la persona (paciente)
    private String nombrecompleto;
    private TipoDocumento tipoDoc;
    private String nroDoc;
    private Sexo sexo;
    private LocalDate fecNacimiento;
    private EstadoCivil estadoCivil;
    private String telefono;
    private String email;
    private String direccion;
    private String fotoUrl; // puede ser null
    
    // ID del apoderado (opcional)
    private Integer apoderadoPersId; // null si no tiene apoderado
    
    // Constructores
    public RegistrarPacienteDTO() {
    }

    public RegistrarPacienteDTO(String nombrecompleto, TipoDocumento tipoDoc, String nroDoc, 
                               Sexo sexo, LocalDate fecNacimiento, EstadoCivil estadoCivil, 
                               String telefono, String email, String direccion, String fotoUrl, 
                               Integer apoderadoPersId) {
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
        this.apoderadoPersId = apoderadoPersId;
    }

    // Getters y Setters
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

    public Integer getApoderadoPersId() {
        return apoderadoPersId;
    }

    public void setApoderadoPersId(Integer apoderadoPersId) {
        this.apoderadoPersId = apoderadoPersId;
    }
}
