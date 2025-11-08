package com.clinicarodriguez.clinicarodriguez.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "triaje")
public class Triaje implements Serializable {

    @Id
    @Column(name = "tria_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long triaId;

    @ManyToOne
    @JoinColumn(name = "tria_hist_id", nullable = false)
    @JsonBackReference
    private Historias historia;

    @Column(name = "tria_fecha")
    private LocalDate triaFecha;

    @Column(name = "tria_talla", precision = 10, scale = 2)
    private BigDecimal triaTalla;

    @Column(name = "tria_peso", precision = 10, scale = 3)
    private BigDecimal triaPeso;

    @Column(name = "tria_temp", precision = 10, scale = 2)
    private BigDecimal triaTemp;

    @Column(name = "tria_presion", length = 20)
    private String triaPresion;

    @Column(name = "tria_frec_cardiaca", precision = 10, scale = 2)
    private BigDecimal triaFrecCardiaca;

    @Column(name = "tria_saturacion", precision = 10, scale = 2)
    private BigDecimal triaSaturacion;

    @Column(name = "tria_observaciones", length = 255)
    private String triaObservaciones;

    @Column(name = "tria_estado")
    private Boolean triaEstado;

    // Getters and Setters

    public Long getTriaId() {
        return triaId;
    }

    public void setTriaId(Long triaId) {
        this.triaId = triaId;
    }

    public Historias getHistoria() {
        return historia;
    }

    public void setHistoria(Historias historia) {
        this.historia = historia;
    }

    public LocalDate getTriaFecha() {
        return triaFecha;
    }

    public void setTriaFecha(LocalDate triaFecha) {
        this.triaFecha = triaFecha;
    }

    public BigDecimal getTriaTalla() {
        return triaTalla;
    }

    public void setTriaTalla(BigDecimal triaTalla) {
        this.triaTalla = triaTalla;
    }

    public BigDecimal getTriaPeso() {
        return triaPeso;
    }

    public void setTriaPeso(BigDecimal triaPeso) {
        this.triaPeso = triaPeso;
    }

    public BigDecimal getTriaTemp() {
        return triaTemp;
    }

    public void setTriaTemp(BigDecimal triaTemp) {
        this.triaTemp = triaTemp;
    }

    public String getTriaPresion() {
        return triaPresion;
    }

    public void setTriaPresion(String triaPresion) {
        this.triaPresion = triaPresion;
    }

    public BigDecimal getTriaFrecCardiaca() {
        return triaFrecCardiaca;
    }

    public void setTriaFrecCardiaca(BigDecimal triaFrecCardiaca) {
        this.triaFrecCardiaca = triaFrecCardiaca;
    }

    public BigDecimal getTriaSaturacion() {
        return triaSaturacion;
    }

    public void setTriaSaturacion(BigDecimal triaSaturacion) {
        this.triaSaturacion = triaSaturacion;
    }

    public String getTriaObservaciones() {
        return triaObservaciones;
    }

    public void setTriaObservaciones(String triaObservaciones) {
        this.triaObservaciones = triaObservaciones;
    }

    public Boolean getTriaEstado() {
        return triaEstado;
    }

    public void setTriaEstado(Boolean triaEstado) {
        this.triaEstado = triaEstado;
    }
}
