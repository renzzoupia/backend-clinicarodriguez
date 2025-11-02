package com.clinicarodriguez.clinicarodriguez.service;

import com.clinicarodriguez.clinicarodriguez.dto.DisponibilidadEspecialidadDTO;

public interface DisponibilidadService {
    
    /**
     * Obtiene la disponibilidad (horarios y médicos) de una especialidad
     * @param especialidadId ID de la especialidad
     * @return DTO con médicos y sus horarios disponibles
     */
    DisponibilidadEspecialidadDTO obtenerDisponibilidadPorEspecialidad(Long especialidadId);
}
