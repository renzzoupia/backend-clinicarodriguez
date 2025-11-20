package com.clinicarodriguez.clinicarodriguez.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "receta_detalle")
public class RecetaDetalle implements Serializable {

    @Id
    @Column(name = "rede_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer redeId;

    @ManyToOne
    @JoinColumn(name = "rece_id", nullable = false)
    @JsonBackReference
    private Receta receta;

    @Column(name = "rede_medicamento", length = 200)
    private String redeMedicamento;

    @Column(name = "rede_presentacion", length = 50)
    private String redePresentacion;

    @Column(name = "rede_dosis", length = 50)
    private String redeDosis;

    @Column(name = "rede_frecuencia", length = 50)
    private String redeFrecuencia;

    @Column(name = "rede_duracion", length = 50)
    private String redeDuracion;

    @Column(name = "rede_via_administracion", length = 50)
    private String redeViaAdministracion;

    @Column(name = "rede_observaciones", length = 100)
    private String redeObservaciones;

    // Getters and Setters

    public Integer getRedeId() {
        return redeId;
    }

    public void setRedeId(Integer redeId) {
        this.redeId = redeId;
    }

    public Receta getReceta() {
        return receta;
    }

    public void setReceta(Receta receta) {
        this.receta = receta;
    }

    public String getRedeMedicamento() {
        return redeMedicamento;
    }

    public void setRedeMedicamento(String redeMedicamento) {
        this.redeMedicamento = redeMedicamento;
    }

    public String getRedePresentacion() {
        return redePresentacion;
    }

    public void setRedePresentacion(String redePresentacion) {
        this.redePresentacion = redePresentacion;
    }

    public String getRedeDosis() {
        return redeDosis;
    }

    public void setRedeDosis(String redeDosis) {
        this.redeDosis = redeDosis;
    }

    public String getRedeFrecuencia() {
        return redeFrecuencia;
    }

    public void setRedeFrecuencia(String redeFrecuencia) {
        this.redeFrecuencia = redeFrecuencia;
    }

    public String getRedeDuracion() {
        return redeDuracion;
    }

    public void setRedeDuracion(String redeDuracion) {
        this.redeDuracion = redeDuracion;
    }

    public String getRedeViaAdministracion() {
        return redeViaAdministracion;
    }

    public void setRedeViaAdministracion(String redeViaAdministracion) {
        this.redeViaAdministracion = redeViaAdministracion;
    }

    public String getRedeObservaciones() {
        return redeObservaciones;
    }

    public void setRedeObservaciones(String redeObservaciones) {
        this.redeObservaciones = redeObservaciones;
    }
}
