package com.clinicarodriguez.clinicarodriguez.service;

import com.clinicarodriguez.clinicarodriguez.dto.DisponibilidadEspecialidadDTO;
import com.clinicarodriguez.clinicarodriguez.dto.SlotsDisponiblesDTO;
import java.time.LocalDate;

public interface DisponibilidadService {
    
    /**
     * Obtiene la disponibilidad (horarios y médicos) de una especialidad
     * @param especialidadId ID de la especialidad
     * @return DTO con médicos y sus horarios disponibles
     */
    DisponibilidadEspecialidadDTO obtenerDisponibilidadPorEspecialidad(Integer especialidadId);
    
    /**
     * Obtiene los slots disponibles de un médico en una fecha específica
     * Muestra qué horarios están disponibles y cuáles están ocupados
     * @param medicoId ID del médico
     * @param fecha Fecha para consultar disponibilidad
     * @return DTO con todos los slots y su disponibilidad
     */
    SlotsDisponiblesDTO obtenerSlotsDisponibles(Integer medicoId, LocalDate fecha);
}
