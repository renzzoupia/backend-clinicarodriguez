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
    private Long citaId;

    @ManyToOne
    @JoinColumn(name = "cita_usua_id", nullable = false)
    private Usuarios usuario;

    @ManyToOne
    @JoinColumn(name = "cita_paci_id", nullable = false)
    private Paciente paciente;

    @Column(name = "cita_fecha")
    private LocalDate citaFecha;

    @Column(name = "cita_hora", length = 50)
    private String citaHora;  // Puede ser varchar o LocalTime

    @Column(name = "cita_cupo")
    private Integer citaCupo;

    @Column(name = "cita_fecha_impresion", length = 100)
    private String citaFechaImpresion;

    @Column(name = "cita_estado", length = 25)
    private String citaEstado;

    public Long getCitaId() {
        return citaId;
    }

    public void setCitaId(Long citaId) {
        this.citaId = citaId;
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

    public LocalDate getCitaFecha() {
        return citaFecha;
    }

    public void setCitaFecha(LocalDate citaFecha) {
        this.citaFecha = citaFecha;
    }

    public String getCitaHora() {
        return citaHora;
    }

    public void setCitaHora(String citaHora) {
        this.citaHora = citaHora;
    }

    public Integer getCitaCupo() {
        return citaCupo;
    }

    public void setCitaCupo(Integer citaCupo) {
        this.citaCupo = citaCupo;
    }

    public String getCitaFechaImpresion() {
        return citaFechaImpresion;
    }

    public void setCitaFechaImpresion(String citaFechaImpresion) {
        this.citaFechaImpresion = citaFechaImpresion;
    }

    public String getCitaEstado() {
        return citaEstado;
    }

    public void setCitaEstado(String citaEstado) {
        this.citaEstado = citaEstado;
    }
}
