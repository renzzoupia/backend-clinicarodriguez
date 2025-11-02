package com.clinicarodriguez.clinicarodriguez.controller;

import com.clinicarodriguez.clinicarodriguez.dto.DisponibilidadEspecialidadDTO;
import com.clinicarodriguez.clinicarodriguez.service.DisponibilidadService;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"http://localhost"})
@RestController
@RequestMapping("/api/disponibilidad")
public class DisponibilidadController {
    
    @Autowired
    private DisponibilidadService disponibilidadService;
    
    /**
     * Obtener horarios disponibles y médicos por especialidad
     * 
     * @param especialidadId ID de la especialidad
     * @return ResponseEntity con los médicos y sus horarios disponibles
     * 
     * Ejemplo de uso:
     * GET /api/disponibilidad/especialidad/1
     * 
     */
    @GetMapping("/especialidad/{especialidadId}")
    public ResponseEntity<?> obtenerDisponibilidadPorEspecialidad(@PathVariable Long especialidadId) {
        HashMap<String, Object> result = new HashMap<>();
        
        try {
            DisponibilidadEspecialidadDTO disponibilidad = 
                    disponibilidadService.obtenerDisponibilidadPorEspecialidad(especialidadId);
            
            if (disponibilidad.getMedicosDisponibles().isEmpty()) {
                result.put("success", false);
                result.put("message", "No hay médicos disponibles para esta especialidad");
                result.put("data", disponibilidad);
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
            
            result.put("success", true);
            result.put("message", "Disponibilidad encontrada");
            result.put("data", disponibilidad);
            result.put("totalMedicos", disponibilidad.getMedicosDisponibles().size());
            
            return new ResponseEntity<>(result, HttpStatus.OK);
            
        } catch (RuntimeException e) {
            result.put("success", false);
            result.put("message", e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
            
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "Error al obtener disponibilidad: " + e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
