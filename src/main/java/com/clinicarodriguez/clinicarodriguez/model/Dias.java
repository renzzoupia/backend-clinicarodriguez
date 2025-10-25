package com.clinicarodriguez.clinicarodriguez.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "dias")
public class Dias implements Serializable {

    @Id
    @Column(name = "dias_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer diasId;

    @Column(name = "dia", nullable = false, unique = true, length = 20)
    @Enumerated(EnumType.STRING)
    private DiaSemana dia;

    @JsonIgnore
    @OneToMany(mappedBy = "dia", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DiaUsuario> diaUsuarios = new HashSet<>();

    public Integer getDiasId() {
        return diasId;
    }

    public void setDiasId(Integer diasId) {
        this.diasId = diasId;
    }

    public DiaSemana getDia() {
        return dia;
    }

    public void setDia(DiaSemana dia) {
        this.dia = dia;
    }

    public Set<DiaUsuario> getDiaUsuarios() {
        return diaUsuarios;
    }

    public void setDiaUsuarios(Set<DiaUsuario> diaUsuarios) {
        this.diaUsuarios = diaUsuarios;
    }

    // Enum para los días de la semana
    public enum DiaSemana {
        LUNES,
        MARTES,
        MIERCOLES,
        JUEVES,
        VIERNES,
        SABADO,
        DOMINGO
    }
}
