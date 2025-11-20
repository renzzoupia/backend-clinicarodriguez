/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clinicarodriguez.clinicarodriguez.dto;

import com.clinicarodriguez.clinicarodriguez.model.Areas;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author renzo
 */
public class AreaDTO {
    private Integer areaId;
    private String areaNombre;
    private String areaDescripcion;
    private List<AreaDTO> subAreas;
    
    // Constructor para conversión rápida
    public AreaDTO(Integer areaId, String areaNombre, String areaDescripcion) {
        this.areaId = areaId;
        this.areaNombre = areaNombre;
        this.areaDescripcion = areaDescripcion;
        this.subAreas = new ArrayList<>();
    }

    private AreaDTO() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
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

    public List<AreaDTO> getSubAreas() {
        return subAreas;
    }

    public void setSubAreas(List<AreaDTO> subAreas) {
        this.subAreas = subAreas;
    }
    
    // Método para convertir Entity a DTO (con jerarquía completa)
    public static AreaDTO fromEntity(Areas area) {
        if (area == null) return null;
        
        AreaDTO dto = new AreaDTO();
        dto.setAreaId(area.getAreaId());
        dto.setAreaNombre(area.getAreaNombre());
        dto.setAreaDescripcion(area.getAreaDescripcion());
        
        // Convertir sub-áreas recursivamente
        if (area.getSubAreas() != null && !area.getSubAreas().isEmpty()) {
            dto.setSubAreas(
                area.getSubAreas().stream()
                    .map(AreaDTO::fromEntity)
                    .collect(Collectors.toList())
            );
        } else {
            dto.setSubAreas(new ArrayList<>());
        }
        
        return dto;
    }
    
    // Método para convertir Entity a DTO (sin jerarquía - plano)
    public static AreaDTO fromEntityPlano(Areas area) {
        if (area == null) return null;
        
        return new AreaDTO(
            area.getAreaId(),
            area.getAreaNombre(),
            area.getAreaDescripcion()
        );
    }
    
    // Método para convertir lista de entities a DTOs
    public static List<AreaDTO> fromEntityList(List<Areas> areas) {
        return areas.stream()
            .map(AreaDTO::fromEntity)
            .collect(Collectors.toList());
    }
}
