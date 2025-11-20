package com.clinicarodriguez.clinicarodriguez.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * DTO para representar la respuesta completa de slots disponibles
 * para un médico en una fecha específica
 */
public class SlotsDisponiblesDTO {
    
    private Integer medicoId;
    private String medicoNombre;
    private LocalDate fecha;
    private ConfiguracionHorarioDTO configuracion;
    private List<SlotHorarioDTO> slots;
    
    // Clase interna para la configuración del horario
    public static class ConfiguracionHorarioDTO {
        private LocalTime horaInicio;
        private LocalTime horaFin;
        private Integer duracionSlot; // en minutos
        
        public ConfiguracionHorarioDTO() {
        }
        
        public ConfiguracionHorarioDTO(LocalTime horaInicio, LocalTime horaFin, Integer duracionSlot) {
            this.horaInicio = horaInicio;
            this.horaFin = horaFin;
            this.duracionSlot = duracionSlot;
        }

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

        public Integer getDuracionSlot() {
            return duracionSlot;
        }

        public void setDuracionSlot(Integer duracionSlot) {
            this.duracionSlot = duracionSlot;
        }
    }
    
    // Constructores
    public SlotsDisponiblesDTO() {
    }
    
    public SlotsDisponiblesDTO(Integer medicoId, String medicoNombre, LocalDate fecha, 
                               ConfiguracionHorarioDTO configuracion, List<SlotHorarioDTO> slots) {
        this.medicoId = medicoId;
        this.medicoNombre = medicoNombre;
        this.fecha = fecha;
        this.configuracion = configuracion;
        this.slots = slots;
    }

    // Getters y Setters
    public Integer getMedicoId() {
        return medicoId;
    }

    public void setMedicoId(Integer medicoId) {
        this.medicoId = medicoId;
    }

    public String getMedicoNombre() {
        return medicoNombre;
    }

    public void setMedicoNombre(String medicoNombre) {
        this.medicoNombre = medicoNombre;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public ConfiguracionHorarioDTO getConfiguracion() {
        return configuracion;
    }

    public void setConfiguracion(ConfiguracionHorarioDTO configuracion) {
        this.configuracion = configuracion;
    }

    public List<SlotHorarioDTO> getSlots() {
        return slots;
    }

    public void setSlots(List<SlotHorarioDTO> slots) {
        this.slots = slots;
    }
}
