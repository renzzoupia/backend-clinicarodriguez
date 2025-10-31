package com.clinicarodriguez.clinicarodriguez.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "historias")
public class Historias implements Serializable {

    @Id
    @Column(name = "hist_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long histId;

    @ManyToOne
    @JoinColumn(name = "hist_usua_id", nullable = false)
    private Usuarios usuario;

    @ManyToOne
    @JoinColumn(name = "hist_paci_id", nullable = false)
    private Paciente paciente;

    @Column(name = "hist_num_historia")
    private Integer histNumHistoria;

    @Column(name = "hist_fecha")
    private LocalDateTime histFecha;

    @Column(name = "hist_talle", precision = 10, scale = 2)
    private BigDecimal histTalle;

    @Column(name = "hist_peso", precision = 10, scale = 3)
    private BigDecimal histPeso;

    @Column(name = "hist_temperatura_c", precision = 10, scale = 2)
    private BigDecimal histTemperaturaC;

    @Column(name = "hist_frec_cardiaca", precision = 10, scale = 2)
    private BigDecimal histFrecCardiaca;

    @Column(name = "hist_estado")
    private Integer histEstado;

    public Long getHistId() {
        return histId;
    }

    public void setHistId(Long histId) {
        this.histId = histId;
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

    public Integer getHistNumHistoria() {
        return histNumHistoria;
    }

    public void setHistNumHistoria(Integer histNumHistoria) {
        this.histNumHistoria = histNumHistoria;
    }

    public LocalDateTime getHistFecha() {
        return histFecha;
    }

    public void setHistFecha(LocalDateTime histFecha) {
        this.histFecha = histFecha;
    }

    public BigDecimal getHistTalle() {
        return histTalle;
    }

    public void setHistTalle(BigDecimal histTalle) {
        this.histTalle = histTalle;
    }

    public BigDecimal getHistPeso() {
        return histPeso;
    }

    public void setHistPeso(BigDecimal histPeso) {
        this.histPeso = histPeso;
    }

    public BigDecimal getHistTemperaturaC() {
        return histTemperaturaC;
    }

    public void setHistTemperaturaC(BigDecimal histTemperaturaC) {
        this.histTemperaturaC = histTemperaturaC;
    }

    public BigDecimal getHistFrecCardiaca() {
        return histFrecCardiaca;
    }

    public void setHistFrecCardiaca(BigDecimal histFrecCardiaca) {
        this.histFrecCardiaca = histFrecCardiaca;
    }

    public Integer getHistEstado() {
        return histEstado;
    }

    public void setHistEstado(Integer histEstado) {
        this.histEstado = histEstado;
    }
}
