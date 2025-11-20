package com.clinicarodriguez.clinicarodriguez.controller;

import com.clinicarodriguez.clinicarodriguez.model.Receta;
import com.clinicarodriguez.clinicarodriguez.service.RecetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/recetas")
@CrossOrigin(origins = {"http://localhost"})
public class RecetaController {

    @Autowired
    private RecetaService recetaService;

    // GET: Listar todas las recetas
    @GetMapping
    public ResponseEntity<Map<String, Object>> listarTodas() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Receta> recetas = recetaService.findAll();
            response.put("success", true);
            response.put("message", "Lista de recetas");
            response.put("data", recetas);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener recetas: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // GET: Buscar receta por ID
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> buscarPorId(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<Receta> receta = recetaService.findById(id);
            if (receta.isPresent()) {
                response.put("success", true);
                response.put("message", "Receta encontrada");
                response.put("data", receta.get());
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Receta no encontrada");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al buscar receta: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // GET: Buscar recetas por episodio clínico
    @GetMapping("/episodio/{episodioId}")
    public ResponseEntity<Map<String, Object>> buscarPorEpisodio(@PathVariable Integer episodioId) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Receta> recetas = recetaService.findByEpisodioId(episodioId);
            response.put("success", true);
            response.put("message", "Recetas del episodio clínico");
            response.put("data", recetas);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al buscar recetas: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // GET: Buscar recetas por estado
    @GetMapping("/estado/{estado}")
    public ResponseEntity<Map<String, Object>> buscarPorEstado(@PathVariable Boolean estado) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Receta> recetas = recetaService.findByEstado(estado);
            response.put("success", true);
            response.put("message", "Recetas por estado");
            response.put("data", recetas);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al buscar recetas: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // POST: Crear nueva receta
    @PostMapping
    public ResponseEntity<Map<String, Object>> crear(@RequestBody Receta receta) {
        Map<String, Object> response = new HashMap<>();
        try {
            Receta nuevaReceta = recetaService.save(receta);
            response.put("success", true);
            response.put("message", "Receta creada correctamente");
            response.put("data", nuevaReceta);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al crear receta: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // PUT: Actualizar receta
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> actualizar(@PathVariable Integer id, @RequestBody Receta receta) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<Receta> recetaExistente = recetaService.findById(id);
            if (recetaExistente.isPresent()) {
                receta.setReceId(id);
                Receta recetaActualizada = recetaService.save(receta);
                response.put("success", true);
                response.put("message", "Receta actualizada correctamente");
                response.put("data", recetaActualizada);
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Receta no encontrada");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al actualizar receta: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // DELETE: Eliminar receta
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> eliminar(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<Receta> receta = recetaService.findById(id);
            if (receta.isPresent()) {
                recetaService.deleteById(id);
                response.put("success", true);
                response.put("message", "Receta eliminada correctamente");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Receta no encontrada");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al eliminar receta: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
