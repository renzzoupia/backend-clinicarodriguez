package com.clinicarodriguez.clinicarodriguez.controller;

import com.clinicarodriguez.clinicarodriguez.model.Personas;
import com.clinicarodriguez.clinicarodriguez.model.Personas.TipoDocumento;
import com.clinicarodriguez.clinicarodriguez.service.PersonasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/personas")
@CrossOrigin(origins = {"http://localhost"})
public class PersonasController {

    @Autowired
    private PersonasService personasService;

    // Listar todas las personas
    @GetMapping
    public ResponseEntity<Map<String, Object>> listarTodas() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Personas> personas = personasService.listarTodas();
            response.put("success", true);
            response.put("message", "Personas obtenidas correctamente");
            response.put("data", personas);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener personas: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Buscar persona por ID
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> buscarPorId(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<Personas> persona = personasService.buscarPorId(id);
            if (persona.isPresent()) {
                response.put("success", true);
                response.put("message", "Persona encontrada");
                response.put("data", persona.get());
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Persona no encontrada");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al buscar persona: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Crear nueva persona
    @PostMapping
    public ResponseEntity<Map<String, Object>> crear(@RequestBody Personas persona) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Validar que no exista documento duplicado
            if (personasService.existePorTipoDocYNroDoc(persona.getPersTipoDoc(), persona.getPersNroDoc())) {
                response.put("success", false);
                response.put("message", "Ya existe una persona con ese tipo y número de documento");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            // Validar email único si se proporciona
            if (persona.getPersEmail() != null && !persona.getPersEmail().isEmpty()) {
                if (personasService.existePorEmail(persona.getPersEmail())) {
                    response.put("success", false);
                    response.put("message", "Ya existe una persona con ese email");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                }
            }

            Personas nuevaPersona = personasService.guardar(persona);
            response.put("success", true);
            response.put("message", "Persona creada correctamente");
            response.put("data", nuevaPersona);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al crear persona: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Actualizar persona
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> actualizar(@PathVariable Integer id, @RequestBody Personas persona) {
        Map<String, Object> response = new HashMap<>();
        try {
            Personas personaActualizada = personasService.actualizar(id, persona);
            response.put("success", true);
            response.put("message", "Persona actualizada correctamente");
            response.put("data", personaActualizada);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al actualizar persona: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Eliminar persona
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> eliminar(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<Personas> persona = personasService.buscarPorId(id);
            if (persona.isPresent()) {
                personasService.eliminar(id);
                response.put("success", true);
                response.put("message", "Persona eliminada correctamente");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Persona no encontrada");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al eliminar persona: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Buscar por tipo y número de documento
    @GetMapping("/documento/{tipoDoc}/{nroDoc}")
    public ResponseEntity<Map<String, Object>> buscarPorDocumento(
            @PathVariable String tipoDoc,
            @PathVariable String nroDoc) {
        Map<String, Object> response = new HashMap<>();
        try {
            TipoDocumento tipo = TipoDocumento.valueOf(tipoDoc.toUpperCase());
            Optional<Personas> persona = personasService.buscarPorTipoDocYNroDoc(tipo, nroDoc);
            if (persona.isPresent()) {
                response.put("success", true);
                response.put("message", "Persona encontrada");
                response.put("data", persona.get());
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Persona no encontrada");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("message", "Tipo de documento inválido. Use: DNI, CE o PAS");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al buscar persona: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Buscar por email
    @GetMapping("/email/{email}")
    public ResponseEntity<Map<String, Object>> buscarPorEmail(@PathVariable String email) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<Personas> persona = personasService.buscarPorEmail(email);
            if (persona.isPresent()) {
                response.put("success", true);
                response.put("message", "Persona encontrada");
                response.put("data", persona.get());
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Persona no encontrada");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al buscar persona: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Buscar por nombre
    @GetMapping("/buscar")
    public ResponseEntity<Map<String, Object>> buscarPorNombre(@RequestParam String nombre) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Personas> personas = personasService.buscarPorNombre(nombre);
            response.put("success", true);
            response.put("message", "Búsqueda completada");
            response.put("data", personas);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al buscar personas: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Listar personas activas
    @GetMapping("/activos")
    public ResponseEntity<Map<String, Object>> listarActivos() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Personas> personas = personasService.listarActivos();
            response.put("success", true);
            response.put("message", "Personas activas obtenidas correctamente");
            response.put("data", personas);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener personas activas: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Listar personas por estado
    @GetMapping("/estado/{estado}")
    public ResponseEntity<Map<String, Object>> listarPorEstado(@PathVariable Boolean estado) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Personas> personas = personasService.listarPorEstado(estado);
            response.put("success", true);
            response.put("message", "Personas obtenidas correctamente");
            response.put("data", personas);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener personas: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Listar personas sin usuario
    @GetMapping("/sin-usuario")
    public ResponseEntity<Map<String, Object>> listarSinUsuario() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Personas> personas = personasService.listarPersonasSinUsuario();
            response.put("success", true);
            response.put("message", "Personas sin usuario obtenidas correctamente");
            response.put("data", personas);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener personas: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Listar personas sin paciente
    @GetMapping("/sin-paciente")
    public ResponseEntity<Map<String, Object>> listarSinPaciente() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Personas> personas = personasService.listarPersonasSinPaciente();
            response.put("success", true);
            response.put("message", "Personas sin paciente obtenidas correctamente");
            response.put("data", personas);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener personas: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Listar personas sin médico
    @GetMapping("/sin-medico")
    public ResponseEntity<Map<String, Object>> listarSinMedico() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Personas> personas = personasService.listarPersonasSinMedico();
            response.put("success", true);
            response.put("message", "Personas sin médico obtenidas correctamente");
            response.put("data", personas);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener personas: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Activar persona
    @PatchMapping("/{id}/activar")
    public ResponseEntity<Map<String, Object>> activar(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Personas persona = personasService.activar(id);
            response.put("success", true);
            response.put("message", "Persona activada correctamente");
            response.put("data", persona);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al activar persona: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Desactivar persona
    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<Map<String, Object>> desactivar(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Personas persona = personasService.desactivar(id);
            response.put("success", true);
            response.put("message", "Persona desactivada correctamente");
            response.put("data", persona);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al desactivar persona: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Contar personas activas
    @GetMapping("/count/activos")
    public ResponseEntity<Map<String, Object>> contarActivos() {
        Map<String, Object> response = new HashMap<>();
        try {
            long count = personasService.contarActivos();
            response.put("success", true);
            response.put("message", "Conteo completado");
            response.put("data", Map.of("total", count));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al contar personas: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
