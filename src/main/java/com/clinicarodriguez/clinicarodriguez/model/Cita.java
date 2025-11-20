package com.clinicarodriguez.clinicarodriguez.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "cita")
public class Cita implements Serializable {

    @Id
    @Column(name = "cita_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer citaId;

    @ManyToOne
    @JoinColumn(name = "cita_paci_id", nullable = false)
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "cita_medi_id", nullable = false)
    private Medicos medico;

    @Column(name = "cita_fecha")
    private LocalDate citaFecha;

    @Column(name = "cita_hora")
    private LocalTime citaHora;

    @Column(name = "cita_hora_fin")
    private LocalTime citaHoraFin;

    @Column(name = "cita_tipo", length = 100)
    private String citaTipo;

    @Column(name = "cita_motivo", length = 500)
    private String citaMotivo;

    @Column(name = "cita_estado", length = 50)
    private String citaEstado;

    @Column(name = "cita_fecha_registro")
    private LocalDate citaFechaRegistro;

    public Integer getCitaId() {
        return citaId;
    }

    public void setCitaId(Integer citaId) {
        this.citaId = citaId;
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

    public LocalDate getCitaFecha() {
        return citaFecha;
    }

    public void setCitaFecha(LocalDate citaFecha) {
        this.citaFecha = citaFecha;
    }

    public LocalTime getCitaHora() {
        return citaHora;
    }

    public void setCitaHora(LocalTime citaHora) {
        this.citaHora = citaHora;
    }

    public LocalTime getCitaHoraFin() {
        return citaHoraFin;
    }

    public void setCitaHoraFin(LocalTime citaHoraFin) {
        this.citaHoraFin = citaHoraFin;
    }

    public String getCitaTipo() {
        return citaTipo;
    }

    public void setCitaTipo(String citaTipo) {
        this.citaTipo = citaTipo;
    }

    public String getCitaMotivo() {
        return citaMotivo;
    }

    public void setCitaMotivo(String citaMotivo) {
        this.citaMotivo = citaMotivo;
    }

    public String getCitaEstado() {
        return citaEstado;
    }

    public void setCitaEstado(String citaEstado) {
        this.citaEstado = citaEstado;
    }

    public LocalDate getCitaFechaRegistro() {
        return citaFechaRegistro;
    }

    public void setCitaFechaRegistro(LocalDate citaFechaRegistro) {
        this.citaFechaRegistro = citaFechaRegistro;
    }
}
