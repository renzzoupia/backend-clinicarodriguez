package com.clinicarodriguez.clinicarodriguez.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "paciente")
public class Paciente implements Serializable {

    @Id
    @Column(name = "paci_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paciId;

    @Column(name = "paci_nombrecompleto", length = 255)
    private String paciNombrecompleto;

    @Column(name = "paci_sexo", length = 10)
    @Enumerated(EnumType.STRING)
    private Sexo paciSexo;

    @Column(name = "paci_fec_nacimiento")
    private LocalDate paciFecNacimiento;

    @Column(name = "paci_dni", length = 20)
    private String paciDni;

    @Column(name = "paci_estado_civil", length = 20)
    @Enumerated(EnumType.STRING)
    private EstadoCivil paciEstadoCivil;

    @Column(name = "paci_direccion", length = 255)
    private String paciDireccion;

    @Column(name = "paci_telefono", length = 15)
    private String paciTelefono;

    @Column(name = "paci_email", length = 100)
    private String paciEmail;

    @Column(name = "paci_apoderado", length = 100)
    private String paciApoderado;

    @Column(name = "paci_numhistoria", length = 255)
    private String paciNumhistoria;

    @Column(name = "paci_estado")
    private Integer paciEstado;

    @JsonIgnore
    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL)
    private Set<Cita> citas = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL)
    private Set<Historias> historias = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL)
    private Set<Documentos> documentos = new HashSet<>();

    public Long getPaciId() {
        return paciId;
    }

    public void setPaciId(Long paciId) {
        this.paciId = paciId;
    }

    public String getPaciNombrecompleto() {
        return paciNombrecompleto;
    }

    public void setPaciNombrecompleto(String paciNombrecompleto) {
        this.paciNombrecompleto = paciNombrecompleto;
    }

    public Sexo getPaciSexo() {
        return paciSexo;
    }

    public void setPaciSexo(Sexo paciSexo) {
        this.paciSexo = paciSexo;
    }

    public LocalDate getPaciFecNacimiento() {
        return paciFecNacimiento;
    }

    public void setPaciFecNacimiento(LocalDate paciFecNacimiento) {
        this.paciFecNacimiento = paciFecNacimiento;
    }

    public String getPaciDni() {
        return paciDni;
    }

    public void setPaciDni(String paciDni) {
        this.paciDni = paciDni;
    }

    public EstadoCivil getPaciEstadoCivil() {
        return paciEstadoCivil;
    }

    public void setPaciEstadoCivil(EstadoCivil paciEstadoCivil) {
        this.paciEstadoCivil = paciEstadoCivil;
    }

    public String getPaciDireccion() {
        return paciDireccion;
    }

    public void setPaciDireccion(String paciDireccion) {
        this.paciDireccion = paciDireccion;
    }

    public String getPaciTelefono() {
        return paciTelefono;
    }

    public void setPaciTelefono(String paciTelefono) {
        this.paciTelefono = paciTelefono;
    }

    public String getPaciEmail() {
        return paciEmail;
    }

    public void setPaciEmail(String paciEmail) {
        this.paciEmail = paciEmail;
    }

    public String getPaciApoderado() {
        return paciApoderado;
    }

    public void setPaciApoderado(String paciApoderado) {
        this.paciApoderado = paciApoderado;
    }

    public String getPaciNumhistoria() {
        return paciNumhistoria;
    }

    public void setPaciNumhistoria(String paciNumhistoria) {
        this.paciNumhistoria = paciNumhistoria;
    }

    public Integer getPaciEstado() {
        return paciEstado;
    }

    public void setPaciEstado(Integer paciEstado) {
        this.paciEstado = paciEstado;
    }

    public Set<Cita> getCitas() {
        return citas;
    }

    public void setCitas(Set<Cita> citas) {
        this.citas = citas;
    }

    public Set<Historias> getHistorias() {
        return historias;
    }

    public void setHistorias(Set<Historias> historias) {
        this.historias = historias;
    }

    public Set<Documentos> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(Set<Documentos> documentos) {
        this.documentos = documentos;
    }

    // Enums
    public enum Sexo {
        MASCULINO,
        FEMENINO,
        OTRO
    }

    public enum EstadoCivil {
        SOLTERO,
        CASADO,
        DIVORCIADO,
        VIUDO
    }
}
