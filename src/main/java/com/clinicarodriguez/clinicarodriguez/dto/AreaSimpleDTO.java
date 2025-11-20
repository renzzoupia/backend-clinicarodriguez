package com.clinicarodriguez.clinicarodriguez.dto;

/**
 * DTO simplificado para listar áreas en formularios <select>
 * Sin relaciones jerárquicas, solo datos básicos
 */
public class AreaSimpleDTO {
    
    private Integer areaId;
    private String areaNombre;
    private String areaDescripcion;
    
    // Constructor vacío
    public AreaSimpleDTO() {
    }
    
    // Constructor completo
    public AreaSimpleDTO(Integer areaId, String areaNombre, String areaDescripcion) {
        this.areaId = areaId;
        this.areaNombre = areaNombre;
        this.areaDescripcion = areaDescripcion;
    }

    // Getters y Setters
    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
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
