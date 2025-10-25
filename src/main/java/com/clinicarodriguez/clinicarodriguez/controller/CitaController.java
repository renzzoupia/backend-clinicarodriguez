package com.clinicarodriguez.clinicarodriguez.controller;

import com.clinicarodriguez.clinicarodriguez.model.Cita;
import com.clinicarodriguez.clinicarodriguez.service.CitaService;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/citas")
public class CitaController {
    
    @Autowired
    private CitaService citaService;
    
    // Listar todas las citas
    @GetMapping()
    public ResponseEntity<?> findAll() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "Lista de Citas");
        result.put("data", citaService.findAll());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    // Obtener cita por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        HashMap<String, Object> result = new HashMap<>();
        Cita cita = citaService.findById(id);

        if (cita != null) {
            result.put("success", true);
            result.put("message", "Cita encontrada");
            result.put("data", cita);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            result.put("success", false);
            result.put("message", "No se encontró la cita con id: " + id);
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }
    
    // Buscar citas por paciente
    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<?> findByPacienteId(@PathVariable Long pacienteId) {
        HashMap<String, Object> result = new HashMap<>();
        List<Cita> citas = citaService.findByPacienteId(pacienteId);
        
        result.put("success", true);
        result.put("message", "Citas del paciente");
        result.put("data", citas);
        result.put("total", citas.size());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    // Buscar próximas citas de un paciente
    @GetMapping("/paciente/{pacienteId}/proximas")
    public ResponseEntity<?> findProximasCitasByPaciente(@PathVariable Long pacienteId) {
        HashMap<String, Object> result = new HashMap<>();
        List<Cita> citas = citaService.findProximasCitasByPaciente(pacienteId);
        
        result.put("success", true);
        result.put("message", "Próximas citas del paciente");
        result.put("data", citas);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    // Buscar citas por usuario (médico)
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<?> findByUsuarioId(@PathVariable Long usuarioId) {
        HashMap<String, Object> result = new HashMap<>();
        List<Cita> citas = citaService.findByUsuarioId(usuarioId);
        
        result.put("success", true);
        result.put("message", "Citas del usuario");
        result.put("data", citas);
        result.put("total", citas.size());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    // Buscar citas por fecha
    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<?> findByFecha(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        HashMap<String, Object> result = new HashMap<>();
        List<Cita> citas = citaService.findByCitaFecha(fecha);
        
        result.put("success", true);
        result.put("message", "Citas del día: " + fecha);
        result.put("data", citas);
        result.put("total", citas.size());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    // Buscar citas por usuario y fecha
    @GetMapping("/usuario/{usuarioId}/fecha/{fecha}")
    public ResponseEntity<?> findByUsuarioAndFecha(
            @PathVariable Long usuarioId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        HashMap<String, Object> result = new HashMap<>();
        List<Cita> citas = citaService.findByUsuarioIdAndFecha(usuarioId, fecha);
        long count = citaService.countByUsuarioAndFecha(usuarioId, fecha);
        
        result.put("success", true);
        result.put("message", "Citas del usuario en la fecha: " + fecha);
        result.put("data", citas);
        result.put("total", count);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    // Buscar citas por estado
    @GetMapping("/estado/{estado}")
    public ResponseEntity<?> findByEstado(@PathVariable String estado) {
        HashMap<String, Object> result = new HashMap<>();
        List<Cita> citas = citaService.findByCitaEstado(estado);
        
        result.put("success", true);
        result.put("message", "Citas con estado: " + estado);
        result.put("data", citas);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    // Buscar citas por rango de fechas
    @GetMapping("/rango")
    public ResponseEntity<?> findByRango(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        HashMap<String, Object> result = new HashMap<>();
        List<Cita> citas = citaService.findByFechaRange(fechaInicio, fechaFin);
        
        result.put("success", true);
        result.put("message", "Citas entre " + fechaInicio + " y " + fechaFin);
        result.put("data", citas);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // Crear cita
    @PostMapping
    public ResponseEntity<?> save(@RequestBody Cita cita) {
        HashMap<String, Object> result = new HashMap<>();
        
        try {
            // Validaciones básicas
            if (cita.getPaciente() == null || cita.getUsuario() == null) {
                result.put("success", false);
                result.put("message", "Paciente y Usuario son requeridos");
                return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
            }
            
            if (cita.getCitaFecha() == null) {
                result.put("success", false);
                result.put("message", "La fecha de la cita es requerida");
                return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
            }
            
            Cita citaGuardada = citaService.save(cita);
            result.put("success", true);
            result.put("message", "Cita registrada exitosamente");
            result.put("data", citaGuardada);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
            
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "Error al registrar cita: " + e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Actualizar cita
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Cita cita) {
        HashMap<String, Object> result = new HashMap<>();
        
        try {
            Cita citaExistente = citaService.findById(id);

            if (citaExistente == null) {
                result.put("success", false);
                result.put("message", "No existe cita con Id: " + id);
                return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
            }

            // Actualizar campos
            citaExistente.setUsuario(cita.getUsuario());
            citaExistente.setPaciente(cita.getPaciente());
            citaExistente.setCitaFecha(cita.getCitaFecha());
            citaExistente.setCitaHora(cita.getCitaHora());
            citaExistente.setCitaCupo(cita.getCitaCupo());
            citaExistente.setCitaFechaImpresion(cita.getCitaFechaImpresion());
            citaExistente.setCitaEstado(cita.getCitaEstado());
            
            Cita citaActualizada = citaService.save(citaExistente);

            result.put("success", true);
            result.put("message", "Cita actualizada correctamente");
            result.put("data", citaActualizada);
            return new ResponseEntity<>(result, HttpStatus.OK);
            
        } catch (Exception ex) {
            result.put("success", false);
            result.put("message", "Error al actualizar: " + ex.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Eliminar cita
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        HashMap<String, Object> result = new HashMap<>();

        Cita cita = citaService.findById(id);

        if (cita == null) {
            result.put("success", false);
            result.put("message", "No existe cita con id: " + id);
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }

        try {
            citaService.deleteById(id);
            result.put("success", true);
            result.put("message", "Cita eliminada correctamente");
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "Error al eliminar cita: " + e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
