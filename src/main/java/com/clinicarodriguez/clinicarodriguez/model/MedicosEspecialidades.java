package com.clinicarodriguez.clinicarodriguez.model;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "medicos_especialidades")
public class MedicosEspecialidades implements Serializable {

    @Id
    @Column(name = "mees_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long meesId;

    @ManyToOne
    @JoinColumn(name = "mees_medi_id", nullable = false)
    private Medicos medico;

    @ManyToOne
    @JoinColumn(name = "mees_espe_id", nullable = false)
    private Especialidades especialidad;

    public Long getMeesId() {
        return meesId;
    }

    public void setMeesId(Long meesId) {
        this.meesId = meesId;
    }

    public Medicos getMedico() {
        return medico;
    }

    public void setMedico(Medicos medico) {
        this.medico = medico;
    }

    public Especialidades getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidades especialidad) {
        this.especialidad = especialidad;
    }
}

