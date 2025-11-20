package com.clinicarodriguez.clinicarodriguez.controller;

import com.clinicarodriguez.clinicarodriguez.model.RecetaDetalle;
import com.clinicarodriguez.clinicarodriguez.service.RecetaDetalleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/receta-detalles")
@CrossOrigin(origins = {"http://localhost"})
public class RecetaDetalleController {

    @Autowired
    private RecetaDetalleService recetaDetalleService;

    // GET: Listar todos los detalles
    @GetMapping
    public ResponseEntity<Map<String, Object>> listarTodos() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<RecetaDetalle> detalles = recetaDetalleService.findAll();
            response.put("success", true);
            response.put("message", "Lista de detalles de recetas");
            response.put("data", detalles);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener detalles: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // GET: Buscar detalle por ID
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> buscarPorId(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<RecetaDetalle> detalle = recetaDetalleService.findById(id);
            if (detalle.isPresent()) {
                response.put("success", true);
                response.put("message", "Detalle encontrado");
                response.put("data", detalle.get());
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Detalle no encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al buscar detalle: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // GET: Buscar detalles por receta
    @GetMapping("/receta/{recetaId}")
    public ResponseEntity<Map<String, Object>> buscarPorReceta(@PathVariable Integer recetaId) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<RecetaDetalle> detalles = recetaDetalleService.findByRecetaId(recetaId);
            response.put("success", true);
            response.put("message", "Detalles de la receta");
            response.put("data", detalles);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al buscar detalles: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // GET: Buscar detalles por medicamento
    @GetMapping("/medicamento")
    public ResponseEntity<Map<String, Object>> buscarPorMedicamento(@RequestParam String nombre) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<RecetaDetalle> detalles = recetaDetalleService.findByMedicamento(nombre);
            response.put("success", true);
            response.put("message", "Detalles por medicamento");
            response.put("data", detalles);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al buscar detalles: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // POST: Crear nuevo detalle
    @PostMapping
    public ResponseEntity<Map<String, Object>> crear(@RequestBody RecetaDetalle detalle) {
        Map<String, Object> response = new HashMap<>();
        try {
            RecetaDetalle nuevoDetalle = recetaDetalleService.save(detalle);
            response.put("success", true);
            response.put("message", "Detalle creado correctamente");
            response.put("data", nuevoDetalle);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al crear detalle: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // PUT: Actualizar detalle
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> actualizar(@PathVariable Integer id, @RequestBody RecetaDetalle detalle) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<RecetaDetalle> detalleExistente = recetaDetalleService.findById(id);
            if (detalleExistente.isPresent()) {
                detalle.setRedeId(id);
                RecetaDetalle detalleActualizado = recetaDetalleService.save(detalle);
                response.put("success", true);
                response.put("message", "Detalle actualizado correctamente");
                response.put("data", detalleActualizado);
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Detalle no encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al actualizar detalle: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // DELETE: Eliminar detalle
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> eliminar(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<RecetaDetalle> detalle = recetaDetalleService.findById(id);
            if (detalle.isPresent()) {
                recetaDetalleService.deleteById(id);
                response.put("success", true);
                response.put("message", "Detalle eliminado correctamente");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Detalle no encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al eliminar detalle: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
