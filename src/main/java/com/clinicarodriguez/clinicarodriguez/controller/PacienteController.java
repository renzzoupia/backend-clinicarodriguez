package com.clinicarodriguez.clinicarodriguez.controller;

import com.clinicarodriguez.clinicarodriguez.model.Paciente;
import com.clinicarodriguez.clinicarodriguez.service.PacienteService;
import java.util.HashMap;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"http://localhost"})
@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {
    
    @Autowired
    private PacienteService pacienteService;
    
    // Listar todos los pacientes
    @GetMapping()
    public ResponseEntity<?> findAll() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "Lista de Pacientes");
        result.put("data", pacienteService.findAll());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    // Obtener paciente por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        HashMap<String, Object> result = new HashMap<>();
        Paciente paciente = pacienteService.findById(id);

        if (paciente != null) {
            result.put("success", true);
            result.put("message", "Paciente encontrado");
            result.put("data", paciente);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            result.put("success", false);
            result.put("message", "No se encontró el paciente con id: " + id);
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }
    
    // Buscar paciente por DNI
    @GetMapping("/dni/{dni}")
    public ResponseEntity<?> findByDni(@PathVariable String dni) {
        HashMap<String, Object> result = new HashMap<>();
        Optional<Paciente> paciente = pacienteService.findByDni(dni);

        if (paciente.isPresent()) {
            result.put("success", true);
            result.put("message", "Paciente encontrado");
            result.put("data", paciente.get());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            result.put("success", false);
            result.put("message", "No se encontró paciente con DNI: " + dni);
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }
    
    // Buscar pacientes por nombre (búsqueda parcial)
    @GetMapping("/buscar")
    public ResponseEntity<?> findByNombre(@RequestParam String nombre) {
        HashMap<String, Object> result = new HashMap<>();
        List<Paciente> pacientes = pacienteService.findByNombre(nombre);
        
        result.put("success", true);
        result.put("message", "Resultados de búsqueda para: " + nombre);
        result.put("data", pacientes);
        result.put("total", pacientes.size());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    // Listar pacientes por estado
    @GetMapping("/estado/{estado}")
    public ResponseEntity<?> findByEstado(@PathVariable Integer estado) {
        HashMap<String, Object> result = new HashMap<>();
        List<Paciente> pacientes = pacienteService.findByEstado(estado);
        
        result.put("success", true);
        result.put("message", "Pacientes con estado: " + estado);
        result.put("data", pacientes);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    // Contar pacientes activos
    @GetMapping("/activos/count")
    public ResponseEntity<?> countActivos() {
        HashMap<String, Object> result = new HashMap<>();
        long count = pacienteService.countPacientesActivos();
        
        result.put("success", true);
        result.put("message", "Total de pacientes activos");
        result.put("total", count);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // Crear paciente
    @PostMapping
    public ResponseEntity<?> save(@RequestBody Paciente paciente) {
        HashMap<String, Object> result = new HashMap<>();
        
        try {
            // Validaciones
            if (paciente.getPaciNombrecompleto() == null || paciente.getPaciNombrecompleto().isEmpty()) {
                result.put("success", false);
                result.put("message", "El nombre completo es requerido");
                return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
            }
            
            // Verificar si el DNI ya existe (si se proporciona)
            if (paciente.getPaciDni() != null && !paciente.getPaciDni().isEmpty()) {
                if (pacienteService.existsByDni(paciente.getPaciDni())) {
                    result.put("success", false);
                    result.put("message", "Ya existe un paciente con el DNI: " + paciente.getPaciDni());
                    return new ResponseEntity<>(result, HttpStatus.CONFLICT);
                }
            }
            
            // Verificar si el email ya existe (si se proporciona)
            if (paciente.getPaciEmail() != null && !paciente.getPaciEmail().isEmpty()) {
                if (pacienteService.existsByEmail(paciente.getPaciEmail())) {
                    result.put("success", false);
                    result.put("message", "Ya existe un paciente con el email: " + paciente.getPaciEmail());
                    return new ResponseEntity<>(result, HttpStatus.CONFLICT);
                }
            }
            
            Paciente pacienteGuardado = pacienteService.save(paciente);
            result.put("success", true);
            result.put("message", "Paciente registrado exitosamente");
            result.put("data", pacienteGuardado);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
            
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "Error al registrar paciente: " + e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Actualizar paciente
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Paciente paciente) {
        HashMap<String, Object> result = new HashMap<>();
        
        try {
            Paciente pacienteExistente = pacienteService.findById(id);

            if (pacienteExistente == null) {
                result.put("success", false);
                result.put("message", "No existe paciente con Id: " + id);
                return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
            }

            // Actualizar campos
            pacienteExistente.setPaciNombrecompleto(paciente.getPaciNombrecompleto());
            pacienteExistente.setPaciSexo(paciente.getPaciSexo());
            pacienteExistente.setPaciFecNacimiento(paciente.getPaciFecNacimiento());
            pacienteExistente.setPaciDni(paciente.getPaciDni());
            pacienteExistente.setPaciEstadoCivil(paciente.getPaciEstadoCivil());
            pacienteExistente.setPaciDireccion(paciente.getPaciDireccion());
            pacienteExistente.setPaciTelefono(paciente.getPaciTelefono());
            pacienteExistente.setPaciEmail(paciente.getPaciEmail());
            pacienteExistente.setPaciApoderado(paciente.getPaciApoderado());
            pacienteExistente.setPaciEstado(paciente.getPaciEstado());
            
            Paciente pacienteActualizado = pacienteService.save(pacienteExistente);

            result.put("success", true);
            result.put("message", "Paciente actualizado correctamente");
            result.put("data", pacienteActualizado);
            return new ResponseEntity<>(result, HttpStatus.OK);
            
        } catch (Exception ex) {
            result.put("success", false);
            result.put("message", "Error al actualizar: " + ex.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Eliminar paciente
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        HashMap<String, Object> result = new HashMap<>();

        Paciente paciente = pacienteService.findById(id);

        if (paciente == null) {
            result.put("success", false);
            result.put("message", "No existe paciente con id: " + id);
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }

        try {
            pacienteService.deleteById(id);
            result.put("success", true);
            result.put("message", "Paciente eliminado correctamente");
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "Error al eliminar paciente: " + e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
