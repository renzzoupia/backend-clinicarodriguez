package com.clinicarodriguez.clinicarodriguez.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "episodios_clinicos")
public class EpisodiosClinicos implements Serializable {

    @Id
    @Column(name = "epcl_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer epclId;

    @ManyToOne
    @JoinColumn(name = "epcl_hist_id", nullable = false)
    @JsonBackReference
    private Historias historia;

    @Column(name = "epcl_fecha")
    private LocalDateTime epclFecha;

    @Column(name = "epcl_tipo", length = 50)
    private String epclTipo;

    @Column(name = "epcl_motivo_consulta", length = 150)
    private String epclMotivoConsulta;

    @Column(name = "epcl_diagnostico", length = 150)
    private String epclDiagnostico;

    @Column(name = "epcl_tratamiento", length = 150)
    private String epclTratamiento;

    @Column(name = "epcl_observaciones", length = 150)
    private String epclObservaciones;

    @Column(name = "epcl_estado")
    private Boolean epclEstado;

    @OneToMany(mappedBy = "episodioClinico", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Receta> recetas;

    // Getters and Setters

    public Integer getEpclId() {
        return epclId;
    }

    public void setEpclId(Integer epclId) {
        this.epclId = epclId;
    }

    public Historias getHistoria() {
        return historia;
    }

    public void setHistoria(Historias historia) {
        this.historia = historia;
    }

    public LocalDateTime getEpclFecha() {
        return epclFecha;
    }

    public void setEpclFecha(LocalDateTime epclFecha) {
        this.epclFecha = epclFecha;
    }

    public String getEpclTipo() {
        return epclTipo;
    }

    public void setEpclTipo(String epclTipo) {
        this.epclTipo = epclTipo;
    }

    public String getEpclMotivoConsulta() {
        return epclMotivoConsulta;
    }

    public void setEpclMotivoConsulta(String epclMotivoConsulta) {
        this.epclMotivoConsulta = epclMotivoConsulta;
    }

    public String getEpclDiagnostico() {
        return epclDiagnostico;
    }

    public void setEpclDiagnostico(String epclDiagnostico) {
        this.epclDiagnostico = epclDiagnostico;
    }

    public String getEpclTratamiento() {
        return epclTratamiento;
    }

    public void setEpclTratamiento(String epclTratamiento) {
        this.epclTratamiento = epclTratamiento;
    }

    public String getEpclObservaciones() {
        return epclObservaciones;
    }

    public void setEpclObservaciones(String epclObservaciones) {
        this.epclObservaciones = epclObservaciones;
    }

    public Boolean getEpclEstado() {
        return epclEstado;
    }

    public void setEpclEstado(Boolean epclEstado) {
        this.epclEstado = epclEstado;
    }

    public List<Receta> getRecetas() {
        return recetas;
    }

    public void setRecetas(List<Receta> recetas) {
        this.recetas = recetas;
    }
}
