package com.clinicarodriguez.clinicarodriguez.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "areas")
public class Areas implements Serializable {

    @Id
    @Column(name = "area_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer areaId;

    @ManyToOne
    @JoinColumn(name = "area_padre")
    @JsonBackReference
    private Areas areaPadre;

    @OneToMany(mappedBy = "areaPadre")
    @JsonManagedReference
    private List<Areas> subAreas;

    @Column(name = "area_nombre", length = 75, nullable = false)
    private String areaNombre;

    @Column(name = "area_descripcion", length = 100)
    private String areaDescripcion;

    // Getters and Setters

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public Areas getAreaPadre() {
        return areaPadre;
    }

    public void setAreaPadre(Areas areaPadre) {
        this.areaPadre = areaPadre;
    }

    public List<Areas> getSubAreas() {
        return subAreas;
    }

    public void setSubAreas(List<Areas> subAreas) {
        this.subAreas = subAreas;
    }

    public String getAreaNombre() {
        return areaNombre;
    }

    public void setAreaNombre(String areaNombre) {
        this.areaNombre = areaNombre;
    }

    public String getAreaDescripcion() {
        return areaDescripcion;
    }

    public void setAreaDescripcion(String areaDescripcion) {
        this.areaDescripcion = areaDescripcion;
    }
}
