package com.clinicarodriguez.clinicarodriguez.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "paciente")
public class Paciente implements Serializable {

    @Id
    @Column(name = "paci_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer paciId;

    @OneToOne
    @JoinColumn(name = "paci_pers_id", unique = true, nullable = false)
    private Personas persona;

    @ManyToOne
    @JoinColumn(name = "paci_apoderado_pers_id")
    private Personas apoderado;

    @Column(name = "paci_estado", nullable = false)
    private Boolean paciEstado;

    @JsonIgnore
    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL)
    private Set<Cita> citas = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL)
    private Set<Historias> historias = new HashSet<>();

    // Getters and Setters

    public Integer getPaciId() {
        return paciId;
    }

    public void setPaciId(Integer paciId) {
        this.paciId = paciId;
    }

    public Personas getPersona() {
        return persona;
    }

    public void setPersona(Personas persona) {
        this.persona = persona;
    }

    public Personas getApoderado() {
        return apoderado;
    }

    public void setApoderado(Personas apoderado) {
        this.apoderado = apoderado;
    }

    public Boolean getPaciEstado() {
        return paciEstado;
    }

    public void setPaciEstado(Boolean paciEstado) {
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

}
