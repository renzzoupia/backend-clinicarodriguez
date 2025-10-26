package com.clinicarodriguez.clinicarodriguez.controller;

import com.clinicarodriguez.clinicarodriguez.model.Especialidades;
import com.clinicarodriguez.clinicarodriguez.repository.EspecialidadesRepository;
import java.util.HashMap;
import java.util.Optional;
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
@RequestMapping("/api/especialidades")
public class EspecialidadesController {
    
    @Autowired
    private EspecialidadesRepository especialidadesRepository;
    
    @GetMapping()
    public ResponseEntity<?> findAll() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "Lista de Especialidades");
        result.put("data", especialidadesRepository.findAll());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Especialidades> findById(@PathVariable Long id) {
        Optional<Especialidades> especialidad = especialidadesRepository.findById(id);

        if (especialidad.isPresent()) {
            return ResponseEntity.ok(especialidad.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        HashMap<String, Object> result = new HashMap<>();

        Optional<Especialidades> data = especialidadesRepository.findById(id);

        if (data.isEmpty()) {
            result.put("success", false);
            result.put("message", "No existe especialidad con id: " + id);
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        } else {
            especialidadesRepository.deleteById(id);
            result.put("success", true);
            result.put("message", "Especialidad eliminada correctamente");
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Especialidades especialidad) {
        HashMap<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "Especialidad registrada correctamente");
        result.put("data", especialidadesRepository.save(especialidad));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody Especialidades especialidad) {
        HashMap<String, Object> result = new HashMap<>();
        Optional<Especialidades> data = especialidadesRepository.findById(id);

        if (data.isEmpty()) {
            result.put("success", false);
            result.put("message", "No existe registro con Id: " + id);
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }

        try {
            Especialidades existingEspecialidad = data.get();
            existingEspecialidad.setEspeNombre(especialidad.getEspeNombre());
            existingEspecialidad.setEspeDescripcion(especialidad.getEspeDescripcion());
            especialidadesRepository.save(existingEspecialidad);

            result.put("success", true);
            result.put("message", "Datos actualizados correctamente.");
            result.put("data", existingEspecialidad);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            result.put("success", false);
            result.put("message", "Error al actualizar: " + ex.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

