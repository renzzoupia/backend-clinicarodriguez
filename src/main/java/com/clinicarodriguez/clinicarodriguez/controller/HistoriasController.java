package com.clinicarodriguez.clinicarodriguez.controller;

import com.clinicarodriguez.clinicarodriguez.model.Historias;
import com.clinicarodriguez.clinicarodriguez.service.HistoriasService;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

@CrossOrigin(origins = {"http://localhost"})
@RestController
@RequestMapping("/api/historias")
public class HistoriasController {
    
    @Autowired
    private HistoriasService historiasService;
    
    @GetMapping()
    public ResponseEntity<?> findAll() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "Lista de Historias Clínicas");
        result.put("data", historiasService.findAll());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        HashMap<String, Object> result = new HashMap<>();
        Historias historia = historiasService.findById(id);

        if (historia != null) {
            result.put("success", true);
            result.put("message", "Historia encontrada");
            result.put("data", historia);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            result.put("success", false);
            result.put("message", "No se encontró la historia con id: " + id);
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<?> findByPacienteId(@PathVariable Long pacienteId) {
        HashMap<String, Object> result = new HashMap<>();
        List<Historias> historias = historiasService.findByPacienteId(pacienteId);
        long count = historiasService.countByPacienteId(pacienteId);
        
        result.put("success", true);
        result.put("message", "Historias del paciente");
        result.put("data", historias);
        result.put("total", count);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<?> findByUsuarioId(@PathVariable Long usuarioId) {
        HashMap<String, Object> result = new HashMap<>();
        List<Historias> historias = historiasService.findByUsuarioId(usuarioId);
        
        result.put("success", true);
        result.put("message", "Historias creadas por el usuario");
        result.put("data", historias);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<?> findByFecha(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        HashMap<String, Object> result = new HashMap<>();
        List<Historias> historias = historiasService.findByHistFecha(fecha);
        
        result.put("success", true);
        result.put("message", "Historias del día: " + fecha);
        result.put("data", historias);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    @GetMapping("/rango")
    public ResponseEntity<?> findByRango(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        HashMap<String, Object> result = new HashMap<>();
        List<Historias> historias = historiasService.findByFechaRange(fechaInicio, fechaFin);
        
        result.put("success", true);
        result.put("message", "Historias entre " + fechaInicio + " y " + fechaFin);
        result.put("data", historias);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Historias historia) {
        HashMap<String, Object> result = new HashMap<>();
        
        try {
            if (historia.getPaciente() == null || historia.getUsuario() == null) {
                result.put("success", false);
                result.put("message", "Paciente y Usuario son requeridos");
                return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
            }
            
            Historias saved = historiasService.save(historia);
            result.put("success", true);
            result.put("message", "Historia clínica registrada exitosamente");
            result.put("data", saved);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
            
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "Error al registrar historia: " + e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Historias historia) {
        HashMap<String, Object> result = new HashMap<>();
        
        try {
            Historias existing = historiasService.findById(id);

            if (existing == null) {
                result.put("success", false);
                result.put("message", "No existe historia con Id: " + id);
                return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
            }

            existing.setUsuario(historia.getUsuario());
            existing.setPaciente(historia.getPaciente());
            existing.setHistNumHistoria(historia.getHistNumHistoria());
            existing.setHistFecha(historia.getHistFecha());
            existing.setHistTalle(historia.getHistTalle());
            existing.setHistPeso(historia.getHistPeso());
            existing.setHistTemperaturaC(historia.getHistTemperaturaC());
            existing.setHistFrecCardiaca(historia.getHistFrecCardiaca());
            existing.setHistEstado(historia.getHistEstado());
            
            Historias updated = historiasService.save(existing);

            result.put("success", true);
            result.put("message", "Historia actualizada correctamente");
            result.put("data", updated);
            return new ResponseEntity<>(result, HttpStatus.OK);
            
        } catch (Exception ex) {
            result.put("success", false);
            result.put("message", "Error al actualizar: " + ex.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        HashMap<String, Object> result = new HashMap<>();
        Historias historia = historiasService.findById(id);

        if (historia == null) {
            result.put("success", false);
            result.put("message", "No existe historia con id: " + id);
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }

        try {
            historiasService.deleteById(id);
            result.put("success", true);
            result.put("message", "Historia eliminada correctamente");
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "Error al eliminar historia: " + e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
