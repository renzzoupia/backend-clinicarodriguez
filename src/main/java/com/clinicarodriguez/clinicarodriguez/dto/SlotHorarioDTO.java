package com.clinicarodriguez.clinicarodriguez.dto;

import java.time.LocalTime;

/**
 * DTO para representar un slot de horario individual
 * Indica si está disponible u ocupado
 */
public class SlotHorarioDTO {
    
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private boolean disponible;
    private Integer citaId;
    
    public SlotHorarioDTO() {
    }
    
    public SlotHorarioDTO(LocalTime horaInicio, LocalTime horaFin, boolean disponible) {
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.disponible = disponible;
    }
    
    public SlotHorarioDTO(LocalTime horaInicio, LocalTime horaFin, boolean disponible, Integer citaId) {
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.disponible = disponible;
        this.citaId = citaId;
    }

    // Getters y Setters
    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public Integer getCitaId() {
        return citaId;
    }

    public void setCitaId(Integer citaId) {
        this.citaId = citaId;
    }
}
