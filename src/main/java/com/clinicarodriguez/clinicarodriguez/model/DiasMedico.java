package com.clinicarodriguez.clinicarodriguez.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalTime;

@Entity
@Table(name = "dias_medico")
public class DiasMedico implements Serializable {

    @Id
    @Column(name = "dime_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dimeId;

    @ManyToOne
    @JoinColumn(name = "dime_medi_id", nullable = false)
    private Medicos medico;

    @ManyToOne
    @JoinColumn(name = "dime_dias_id", nullable = false)
    private Dias dia;

    @Column(name = "dime_estado")
    private Integer dimeEstado;

    @Column(name = "dime_hora_inicio")
    private LocalTime dimeHoraInicio;

    @Column(name = "dime_hora_fin")
    private LocalTime dimeHoraFin;

    @Column(name = "dime_duracion")
    private Integer dimeDuracion;  // Duración en minutos

    public Long getDimeId() {
        return dimeId;
    }

    public void setDimeId(Long dimeId) {
        this.dimeId = dimeId;
    }

    public Medicos getMedico() {
        return medico;
    }

    public void setMedico(Medicos medico) {
        this.medico = medico;
    }

    public Dias getDia() {
        return dia;
    }

    public void setDia(Dias dia) {
        this.dia = dia;
    }

    public Integer getDimeEstado() {
        return dimeEstado;
    }

    public void setDimeEstado(Integer dimeEstado) {
        this.dimeEstado = dimeEstado;
    }

    public LocalTime getDimeHoraInicio() {
        return dimeHoraInicio;
    }

    public void setDimeHoraInicio(LocalTime dimeHoraInicio) {
        this.dimeHoraInicio = dimeHoraInicio;
    }

    public LocalTime getDimeHoraFin() {
        return dimeHoraFin;
    }

    public void setDimeHoraFin(LocalTime dimeHoraFin) {
        this.dimeHoraFin = dimeHoraFin;
    }

    public Integer getDimeDuracion() {
        return dimeDuracion;
    }

    public void setDimeDuracion(Integer dimeDuracion) {
        this.dimeDuracion = dimeDuracion;
    }
}
