package com.clinicarodriguez.clinicarodriguez.controller;

import com.clinicarodriguez.clinicarodriguez.model.DiaUsuario;
import com.clinicarodriguez.clinicarodriguez.service.DiaUsuarioService;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"http://localhost"})
@RestController
@RequestMapping("/api/dia-usuario")
public class DiaUsuarioController {
    
    @Autowired
    private DiaUsuarioService diaUsuarioService;
    
    @GetMapping()
    public ResponseEntity<?> findAll() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "Lista de Disponibilidad de Usuarios");
        result.put("data", diaUsuarioService.findAll());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        HashMap<String, Object> result = new HashMap<>();
        DiaUsuario diaUsuario = diaUsuarioService.findById(id);

        if (diaUsuario != null) {
            result.put("success", true);
            result.put("message", "Disponibilidad encontrada");
            result.put("data", diaUsuario);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            result.put("success", false);
            result.put("message", "No se encontró la disponibilidad con id: " + id);
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<?> findByUsuarioId(@PathVariable Long usuarioId) {
        HashMap<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "Disponibilidad del usuario");
        result.put("data", diaUsuarioService.findByUsuarioId(usuarioId));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    @GetMapping("/usuario/{usuarioId}/activos")
    public ResponseEntity<?> findByUsuarioIdActivos(@PathVariable Long usuarioId) {
        HashMap<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "Disponibilidad activa del usuario");
        result.put("data", diaUsuarioService.findByUsuarioIdAndEstadoActivo(usuarioId));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody DiaUsuario diaUsuario) {
        HashMap<String, Object> result = new HashMap<>();
        
        try {
            DiaUsuario saved = diaUsuarioService.save(diaUsuario);
            result.put("success", true);
            result.put("message", "Disponibilidad registrada correctamente");
            result.put("data", saved);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "Error al registrar disponibilidad: " + e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody DiaUsuario diaUsuario) {
        HashMap<String, Object> result = new HashMap<>();
        
        DiaUsuario existing = diaUsuarioService.findById(id);
        if (existing == null) {
            result.put("success", false);
            result.put("message", "No existe disponibilidad con Id: " + id);
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }

        existing.setUsuario(diaUsuario.getUsuario());
        existing.setDia(diaUsuario.getDia());
        existing.setDiusEstado(diaUsuario.getDiusEstado());
        existing.setDiusHoraInicio(diaUsuario.getDiusHoraInicio());
        existing.setDiusHoraFin(diaUsuario.getDiusHoraFin());
        existing.setDiusDuracion(diaUsuario.getDiusDuracion());
        
        DiaUsuario updated = diaUsuarioService.save(existing);
        result.put("success", true);
        result.put("message", "Disponibilidad actualizada correctamente");
        result.put("data", updated);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        HashMap<String, Object> result = new HashMap<>();
        DiaUsuario diaUsuario = diaUsuarioService.findById(id);

        if (diaUsuario == null) {
            result.put("success", false);
            result.put("message", "No existe disponibilidad con id: " + id);
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }

        diaUsuarioService.deleteById(id);
        result.put("success", true);
        result.put("message", "Disponibilidad eliminada correctamente");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
