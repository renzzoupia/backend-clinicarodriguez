package com.clinicarodriguez.clinicarodriguez.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "categorias_activo")
public class CategoriasActivo implements Serializable {

    @Id
    @Column(name = "caac_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer caacId;

    @Column(name = "caac_nombre_categoria", length = 150)
    private String caacNombreCategoria;

    @Column(name = "caac_descripcion", length = 50)
    private String caacDescripcion;

    @Column(name = "caac_estado")
    private Integer caacEstado;

    @JsonIgnore
    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL)
    private List<ActivosTecnologicos> activosTecnologicos = new ArrayList<>();

    public Integer getCaacId() {
        return caacId;
    }

    public void setCaacId(Integer caacId) {
        this.caacId = caacId;
    }

    public String getCaacNombreCategoria() {
        return caacNombreCategoria;
    }

    public void setCaacNombreCategoria(String caacNombreCategoria) {
        this.caacNombreCategoria = caacNombreCategoria;
    }

    public String getCaacDescripcion() {
        return caacDescripcion;
    }

    public void setCaacDescripcion(String caacDescripcion) {
        this.caacDescripcion = caacDescripcion;
    }

    public Integer getCaacEstado() {
        return caacEstado;
    }

    public void setCaacEstado(Integer caacEstado) {
        this.caacEstado = caacEstado;
    }

    public List<ActivosTecnologicos> getActivosTecnologicos() {
        return activosTecnologicos;
    }

    public void setActivosTecnologicos(List<ActivosTecnologicos> activosTecnologicos) {
        this.activosTecnologicos = activosTecnologicos;
    }
}
