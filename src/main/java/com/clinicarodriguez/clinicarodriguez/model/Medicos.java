package com.clinicarodriguez.clinicarodriguez.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "medicos")
public class Medicos implements Serializable {

    @Id
    @Column(name = "medi_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer mediId;

    @OneToOne
    @JoinColumn(name = "medi_pers_id", unique = true, nullable = false)
    private Personas persona;
    
    @OneToOne
    @JoinColumn(name = "medi_usua_id", unique = true, nullable = false)
    private Usuarios usuarios;

    @Column(name = "medi_nro_colegiatura", unique = true, length = 20)
    private String mediNroColegiatura;

    @Column(name = "medi_estado", nullable = false)
    private Boolean mediEstado;

    @JsonIgnore
    @OneToMany(mappedBy = "medico", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MedicosEspecialidades> medicosEspecialidades = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "medico", cascade = CascadeType.ALL)
    private List<Cita> citas = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "medico", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DiasMedico> diasMedicos = new HashSet<>();

    // Getters and Setters

    public Integer getMediId() {
        return mediId;
    }

    public void setMediId(Integer mediId) {
        this.mediId = mediId;
    }

    public Personas getPersona() {
        return persona;
    }

    public void setPersona(Personas persona) {
        this.persona = persona;
    }

    public Usuarios getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Usuarios usuarios) {
        this.usuarios = usuarios;
    }

    
    public String getMediNroColegiatura() {
        return mediNroColegiatura;
    }

    public void setMediNroColegiatura(String mediNroColegiatura) {
        this.mediNroColegiatura = mediNroColegiatura;
    }

    public Boolean getMediEstado() {
        return mediEstado;
    }

    public void setMediEstado(Boolean mediEstado) {
        this.mediEstado = mediEstado;
    }

    public Set<MedicosEspecialidades> getMedicosEspecialidades() {
        return medicosEspecialidades;
    }

    public void setMedicosEspecialidades(Set<MedicosEspecialidades> medicosEspecialidades) {
        this.medicosEspecialidades = medicosEspecialidades;
    }

    public List<Cita> getCitas() {
        return citas;
    }

    public void setCitas(List<Cita> citas) {
        this.citas = citas;
    }

    public Set<DiasMedico> getDiasMedicos() {
        return diasMedicos;
    }

    public void setDiasMedicos(Set<DiasMedico> diasMedicos) {
        this.diasMedicos = diasMedicos;
    }
}
