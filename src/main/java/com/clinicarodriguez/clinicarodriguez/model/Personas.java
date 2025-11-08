package com.clinicarodriguez.clinicarodriguez.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "personas", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"pers_tipo_doc", "pers_nro_doc"})
})
public class Personas implements Serializable {

    @Id
    @Column(name = "pers_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer persId;

    @Column(name = "pers_nombrecompleto", nullable = false, length = 60)
    private String persNombrecompleto;

    @Column(name = "pers_tipo_doc", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private TipoDocumento persTipoDoc;

    @Column(name = "pers_nro_doc", nullable = false, length = 20)
    private String persNroDoc;

    @Column(name = "pers_sexo", length = 50)
    @Enumerated(EnumType.STRING)
    private Sexo persSexo;

    @Column(name = "pers_fec_nacimiento")
    private LocalDate persFecNacimiento;

    @Column(name = "pers_estado_civil", length = 20)
    @Enumerated(EnumType.STRING)
    private EstadoCivil persEstadoCivil;

    @Column(name = "pers_telefono", length = 30)
    private String persTelefono;

    @Column(name = "pers_email", length = 100)
    private String persEmail;

    @Column(name = "pers_direccion", length = 255)
    private String persDireccion;

    @Column(name = "pers_foto_url", length = 255)
    private String persFotoUrl;

    @Column(name = "pers_es_activo", nullable = false)
    private Boolean persEsActivo;

    @Column(name = "pers_created_at", nullable = false, updatable = false)
    private LocalDateTime persCreatedAt;

    @Column(name = "pers_updated_at")
    private LocalDateTime persUpdatedAt;

    // Relaciones 1:1 con las tablas hijas
    @JsonIgnore
    @OneToOne(mappedBy = "persona", cascade = CascadeType.ALL)
    private Usuarios usuario;

    @JsonIgnore
    @OneToOne(mappedBy = "persona", cascade = CascadeType.ALL)
    private Paciente paciente;

    @JsonIgnore
    @OneToOne(mappedBy = "persona", cascade = CascadeType.ALL)
    private Medicos medico;

    @PrePersist
    protected void onCreate() {
        persCreatedAt = LocalDateTime.now();
        persUpdatedAt = LocalDateTime.now();
        if (persEsActivo == null) {
            persEsActivo = true;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        persUpdatedAt = LocalDateTime.now();
    }

    // Getters and Setters

    public Integer getPersId() {
        return persId;
    }

    public void setPersId(Integer persId) {
        this.persId = persId;
    }

    public String getPersNombrecompleto() {
        return persNombrecompleto;
    }

    public void setPersNombrecompleto(String persNombrecompleto) {
        this.persNombrecompleto = persNombrecompleto;
    }

    public TipoDocumento getPersTipoDoc() {
        return persTipoDoc;
    }

    public void setPersTipoDoc(TipoDocumento persTipoDoc) {
        this.persTipoDoc = persTipoDoc;
    }

    public String getPersNroDoc() {
        return persNroDoc;
    }

    public void setPersNroDoc(String persNroDoc) {
        this.persNroDoc = persNroDoc;
    }

    public Sexo getPersSexo() {
        return persSexo;
    }

    public void setPersSexo(Sexo persSexo) {
        this.persSexo = persSexo;
    }

    public LocalDate getPersFecNacimiento() {
        return persFecNacimiento;
    }

    public void setPersFecNacimiento(LocalDate persFecNacimiento) {
        this.persFecNacimiento = persFecNacimiento;
    }

    public EstadoCivil getPersEstadoCivil() {
        return persEstadoCivil;
    }

    public void setPersEstadoCivil(EstadoCivil persEstadoCivil) {
        this.persEstadoCivil = persEstadoCivil;
    }

    public String getPersTelefono() {
        return persTelefono;
    }

    public void setPersTelefono(String persTelefono) {
        this.persTelefono = persTelefono;
    }

    public String getPersEmail() {
        return persEmail;
    }

    public void setPersEmail(String persEmail) {
        this.persEmail = persEmail;
    }

    public String getPersDireccion() {
        return persDireccion;
    }

    public void setPersDireccion(String persDireccion) {
        this.persDireccion = persDireccion;
    }

    public String getPersFotoUrl() {
        return persFotoUrl;
    }

    public void setPersFotoUrl(String persFotoUrl) {
        this.persFotoUrl = persFotoUrl;
    }

    public Boolean getPersEsActivo() {
        return persEsActivo;
    }

    public void setPersEsActivo(Boolean persEsActivo) {
        this.persEsActivo = persEsActivo;
    }

    public LocalDateTime getPersCreatedAt() {
        return persCreatedAt;
    }

    public void setPersCreatedAt(LocalDateTime persCreatedAt) {
        this.persCreatedAt = persCreatedAt;
    }

    public LocalDateTime getPersUpdatedAt() {
        return persUpdatedAt;
    }

    public void setPersUpdatedAt(LocalDateTime persUpdatedAt) {
        this.persUpdatedAt = persUpdatedAt;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Medicos getMedico() {
        return medico;
    }

    public void setMedico(Medicos medico) {
        this.medico = medico;
    }

    // Enums
    public enum TipoDocumento {
        DNI,
        CE,
        PAS
    }

    public enum Sexo {
        MASCULINO,
        FEMENINO,
        OTRO
    }

    public enum EstadoCivil {
        SOLTERO,
        CASADO,
        DIVORCIADO,
        VIUDO,
        CONVIVIENTE
    }
}
