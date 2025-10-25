package com.clinicarodriguez.clinicarodriguez.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalTime;

@Entity
@Table(name = "dia_usuario")
public class DiaUsuario implements Serializable {

    @Id
    @Column(name = "dius_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long diusId;

    @ManyToOne
    @JoinColumn(name = "dius_usua_id", nullable = false)
    private Usuarios usuario;

    @ManyToOne
    @JoinColumn(name = "dius_dias_id", nullable = false)
    private Dias dia;

    @Column(name = "dius_estado")
    private Integer diusEstado;

    @Column(name = "dius_hora_inicio")
    private LocalTime diusHoraInicio;

    @Column(name = "dius_hora_fin")
    private LocalTime diusHoraFin;

    @Column(name = "dius_duracion")
    private Integer diusDuracion;  // Duración en minutos

    public Long getDiusId() {
        return diusId;
    }

    public void setDiusId(Long diusId) {
        this.diusId = diusId;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public Dias getDia() {
        return dia;
    }

    public void setDia(Dias dia) {
        this.dia = dia;
    }

    public Integer getDiusEstado() {
        return diusEstado;
    }

    public void setDiusEstado(Integer diusEstado) {
        this.diusEstado = diusEstado;
    }

    public LocalTime getDiusHoraInicio() {
        return diusHoraInicio;
    }

    public void setDiusHoraInicio(LocalTime diusHoraInicio) {
        this.diusHoraInicio = diusHoraInicio;
    }

    public LocalTime getDiusHoraFin() {
        return diusHoraFin;
    }

    public void setDiusHoraFin(LocalTime diusHoraFin) {
        this.diusHoraFin = diusHoraFin;
    }

    public Integer getDiusDuracion() {
        return diusDuracion;
    }

    public void setDiusDuracion(Integer diusDuracion) {
        this.diusDuracion = diusDuracion;
    }
}
