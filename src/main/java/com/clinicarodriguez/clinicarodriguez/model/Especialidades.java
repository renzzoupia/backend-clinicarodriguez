package com.clinicarodriguez.clinicarodriguez.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "especialidades")
public class Especialidades implements Serializable {

    @Id
    @Column(name = "espe_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long espeId;

    @Column(name = "espe_nombre")
    private String espeNombre;

    @Column(name = "espe_descripcion")
    private String espeDescripcion;

    @JsonIgnore
    @OneToMany(mappedBy = "especialidad", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MedicosEspecialidades> medicosEspecialidades = new HashSet<>();

    public Long getEspeId() {
        return espeId;
    }

    public void setEspeId(Long espeId) {
        this.espeId = espeId;
    }

    public String getEspeNombre() {
        return espeNombre;
    }

    public void setEspeNombre(String espeNombre) {
        this.espeNombre = espeNombre;
    }

    public String getEspeDescripcion() {
        return espeDescripcion;
    }

    public void setEspeDescripcion(String espeDescripcion) {
        this.espeDescripcion = espeDescripcion;
    }

    public Set<MedicosEspecialidades> getMedicosEspecialidades() {
        return medicosEspecialidades;
    }

    public void setMedicosEspecialidades(Set<MedicosEspecialidades> medicosEspecialidades) {
        this.medicosEspecialidades = medicosEspecialidades;
    }
}

