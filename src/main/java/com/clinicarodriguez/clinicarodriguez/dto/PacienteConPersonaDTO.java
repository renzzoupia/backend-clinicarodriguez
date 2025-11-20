package com.clinicarodriguez.clinicarodriguez.dto;

import com.clinicarodriguez.clinicarodriguez.model.Personas.TipoDocumento;
import com.clinicarodriguez.clinicarodriguez.model.Personas.Sexo;
import com.clinicarodriguez.clinicarodriguez.model.Personas.EstadoCivil;
import java.time.LocalDate;

public class PacienteConPersonaDTO {
    
    // Datos del paciente
    private Integer paciId;
    private Boolean paciEstado;
    
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
    
    // Datos del apoderado (opcional)
    private ApoderadoSimpleDTO apoderado;
    
    // Constructores
    public PacienteConPersonaDTO() {
    }

    public PacienteConPersonaDTO(Integer paciId, Boolean paciEstado, Integer persId, 
                                String nombrecompleto, TipoDocumento tipoDoc, String nroDoc, 
                                Sexo sexo, LocalDate fecNacimiento, EstadoCivil estadoCivil, 
                                String telefono, String email, String direccion, String fotoUrl,
                                ApoderadoSimpleDTO apoderado) {
        this.paciId = paciId;
        this.paciEstado = paciEstado;
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
        this.apoderado = apoderado;
    }

    // Getters y Setters
    public Integer getPaciId() {
        return paciId;
    }

    public void setPaciId(Integer paciId) {
        this.paciId = paciId;
    }

    public Boolean getPaciEstado() {
        return paciEstado;
    }

    public void setPaciEstado(Boolean paciEstado) {
        this.paciEstado = paciEstado;
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

    public ApoderadoSimpleDTO getApoderado() {
        return apoderado;
    }

    public void setApoderado(ApoderadoSimpleDTO apoderado) {
        this.apoderado = apoderado;
    }

    // Clase interna para representar el apoderado
    public static class ApoderadoSimpleDTO {
        private Integer persId;
        private String nombrecompleto;
        private TipoDocumento tipoDoc;
        private String nroDoc;
        private String telefono;
        private String email;

        public ApoderadoSimpleDTO() {
        }

        public ApoderadoSimpleDTO(Integer persId, String nombrecompleto, TipoDocumento tipoDoc, 
                                 String nroDoc, String telefono, String email) {
            this.persId = persId;
            this.nombrecompleto = nombrecompleto;
            this.tipoDoc = tipoDoc;
            this.nroDoc = nroDoc;
            this.telefono = telefono;
            this.email = email;
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
    }
}
