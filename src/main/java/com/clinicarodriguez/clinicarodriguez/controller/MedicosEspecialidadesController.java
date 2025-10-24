package com.clinicarodriguez.clinicarodriguez.controller;

import com.clinicarodriguez.clinicarodriguez.model.MedicosEspecialidades;
import com.clinicarodriguez.clinicarodriguez.repository.MedicosEspecialidadesRepository;
import com.clinicarodriguez.clinicarodriguez.service.MedicosEspecialidadesService;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/medicos-especialidades")
public class MedicosEspecialidadesController {
    
    @Autowired
    private MedicosEspecialidadesRepository medicosEspecialidadesRepository;
    
    @Autowired
    private MedicosEspecialidadesService medicosEspecialidadesService;
    
    @GetMapping()
    public ResponseEntity<?> findAll() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "Lista de relaciones Médicos-Especialidades");
        result.put("data", medicosEspecialidadesRepository.findAll());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<MedicosEspecialidades> medicosEspecialidades = medicosEspecialidadesRepository.findById(id);

        if (medicosEspecialidades.isPresent()) {
            HashMap<String, Object> result = new HashMap<>();
            result.put("success", true);
            result.put("message", "Relación encontrada");
            result.put("data", medicosEspecialidades.get());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            HashMap<String, Object> result = new HashMap<>();
            result.put("success", false);
            result.put("message", "No se encontró la relación con id: " + id);
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/medico/{medicoId}")
    public ResponseEntity<?> findByMedicoId(@PathVariable Long medicoId) {
        HashMap<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "Especialidades del médico");
        result.put("data", medicosEspecialidadesService.findByMedicoId(medicoId));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    @GetMapping("/especialidad/{especialidadId}")
    public ResponseEntity<?> findByEspecialidadId(@PathVariable Long especialidadId) {
        HashMap<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "Médicos con la especialidad");
        result.put("data", medicosEspecialidadesService.findByEspecialidadId(especialidadId));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        HashMap<String, Object> result = new HashMap<>();

        Optional<MedicosEspecialidades> data = medicosEspecialidadesRepository.findById(id);

        if (data.isEmpty()) {
            result.put("success", false);
            result.put("message", "No existe relación con id: " + id);
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        } else {
            medicosEspecialidadesRepository.deleteById(id);
            result.put("success", true);
            result.put("message", "Relación eliminada correctamente");
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody MedicosEspecialidades medicosEspecialidades) {
        HashMap<String, Object> result = new HashMap<>();
        
        try {
            MedicosEspecialidades saved = medicosEspecialidadesRepository.save(medicosEspecialidades);
            result.put("success", true);
            result.put("message", "Relación creada correctamente");
            result.put("data", saved);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            result.put("success", false);
            result.put("message", "Error al crear la relación: " + ex.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping("/asignar")
    public ResponseEntity<?> asignarEspecialidadAMedico(@RequestBody Map<String, Long> payload) {
        HashMap<String, Object> result = new HashMap<>();
        
        Long medicoId = payload.get("medicoId");
        Long especialidadId = payload.get("especialidadId");
        
        if (medicoId == null || especialidadId == null) {
            result.put("success", false);
            result.put("message", "Se requieren medicoId y especialidadId");
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        
        // Verificar si ya existe la relación
        if (medicosEspecialidadesService.existsByMedicoAndEspecialidad(medicoId, especialidadId)) {
            result.put("success", false);
            result.put("message", "La relación ya existe");
            return new ResponseEntity<>(result, HttpStatus.CONFLICT);
        }
        
        MedicosEspecialidades nuevaRelacion = medicosEspecialidadesService.asignarEspecialidadAMedico(medicoId, especialidadId);
        
        if (nuevaRelacion != null) {
            result.put("success", true);
            result.put("message", "Especialidad asignada al médico correctamente");
            result.put("data", nuevaRelacion);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            result.put("success", false);
            result.put("message", "No se pudo crear la relación. Verifique que el médico y la especialidad existan");
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }
}

