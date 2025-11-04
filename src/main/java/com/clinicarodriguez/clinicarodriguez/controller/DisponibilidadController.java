package com.clinicarodriguez.clinicarodriguez.controller;

import com.clinicarodriguez.clinicarodriguez.dto.DisponibilidadEspecialidadDTO;
import com.clinicarodriguez.clinicarodriguez.dto.SlotsDisponiblesDTO;
import com.clinicarodriguez.clinicarodriguez.service.DisponibilidadService;
import java.time.LocalDate;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    
    /**
     * Obtener slots de horarios disponibles para un médico en una fecha específica
     * 
     * @param medicoId ID del médico
     * @param fecha Fecha para consultar (formato: yyyy-MM-dd)
     * @return ResponseEntity con los slots disponibles y ocupados
     * 
     * Ejemplo de uso:
     * GET /api/disponibilidad/slots?medicoId=1&fecha=2025-11-15
     * 
     * Respuesta:
     * {
     *   "success": true,
     *   "message": "Slots encontrados",
     *   "data": {
     *     "medicoId": 1,
     *     "medicoNombre": "Dr. Juan Pérez",
     *     "fecha": "2025-11-15",
     *     "configuracion": {
     *       "horaInicio": "08:00",
     *       "horaFin": "17:00",
     *       "duracionSlot": 30
     *     },
     *     "slots": [
     *       {"horaInicio": "08:00", "horaFin": "08:30", "disponible": true, "citaId": null},
     *       {"horaInicio": "08:30", "horaFin": "09:00", "disponible": false, "citaId": 45}
     *     ]
     *   },
     *   "totalSlots": 18,
     *   "slotsDisponibles": 12,
     *   "slotsOcupados": 6
     * }
     */
    @GetMapping("/slots")
    public ResponseEntity<?> obtenerSlotsDisponibles(
            @RequestParam Long medicoId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        
        HashMap<String, Object> result = new HashMap<>();
        
        try {
            SlotsDisponiblesDTO slots = disponibilidadService.obtenerSlotsDisponibles(medicoId, fecha);
            
            // Calcular estadísticas
            long slotsDisponibles = slots.getSlots().stream()
                    .filter(slot -> slot.isDisponible())
                    .count();
            long slotsOcupados = slots.getSlots().size() - slotsDisponibles;
            
            result.put("success", true);
            result.put("message", "Slots encontrados");
            result.put("data", slots);
            result.put("totalSlots", slots.getSlots().size());
            result.put("slotsDisponibles", slotsDisponibles);
            result.put("slotsOcupados", slotsOcupados);
            
            return new ResponseEntity<>(result, HttpStatus.OK);
            
        } catch (RuntimeException e) {
            result.put("success", false);
            result.put("message", e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
            
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "Error al obtener slots: " + e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
