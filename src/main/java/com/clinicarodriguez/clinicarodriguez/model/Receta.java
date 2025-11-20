package com.clinicarodriguez.clinicarodriguez.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "receta")
public class Receta implements Serializable {

    @Id
    @Column(name = "rece_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer receId;

    @ManyToOne
    @JoinColumn(name = "epcl_id", nullable = false)
    private EpisodiosClinicos episodioClinico;

    @Column(name = "rece_fecha")
    private LocalDateTime receFecha;

    @Column(name = "rece_indicaciones", length = 75)
    private String receIndicaciones;

    @Column(name = "rece_estado")
    private Boolean receEstado;

    @OneToMany(mappedBy = "receta", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<RecetaDetalle> detalles;

    // Getters and Setters

    public Integer getReceId() {
        return receId;
    }

    public void setReceId(Integer receId) {
        this.receId = receId;
    }

    public EpisodiosClinicos getEpisodioClinico() {
        return episodioClinico;
    }

    public void setEpisodioClinico(EpisodiosClinicos episodioClinico) {
        this.episodioClinico = episodioClinico;
    }

    public LocalDateTime getReceFecha() {
        return receFecha;
    }

    public void setReceFecha(LocalDateTime receFecha) {
        this.receFecha = receFecha;
    }

    public String getReceIndicaciones() {
        return receIndicaciones;
    }

    public void setReceIndicaciones(String receIndicaciones) {
        this.receIndicaciones = receIndicaciones;
    }

    public Boolean getReceEstado() {
        return receEstado;
    }

    public void setReceEstado(Boolean receEstado) {
        this.receEstado = receEstado;
    }

    public List<RecetaDetalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<RecetaDetalle> detalles) {
        this.detalles = detalles;
    }
}
