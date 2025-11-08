package com.clinicarodriguez.clinicarodriguez.controller;

import com.clinicarodriguez.clinicarodriguez.model.Triaje;
import com.clinicarodriguez.clinicarodriguez.service.TriajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/triaje")
public class TriajeController {

    @Autowired
    private TriajeService triajeService;

    // Listar todos los triajes
    @GetMapping
    public ResponseEntity<Map<String, Object>> listarTodos() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Triaje> triajes = triajeService.listarTodos();
            response.put("success", true);
            response.put("message", "Triajes obtenidos correctamente");
            response.put("data", triajes);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener triajes: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Buscar triaje por ID
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> buscarPorId(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<Triaje> triaje = triajeService.buscarPorId(id);
            if (triaje.isPresent()) {
                response.put("success", true);
                response.put("message", "Triaje encontrado");
                response.put("data", triaje.get());
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Triaje no encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al buscar triaje: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Crear nuevo triaje
    @PostMapping
    public ResponseEntity<Map<String, Object>> crear(@RequestBody Triaje triaje) {
        Map<String, Object> response = new HashMap<>();
        try {
            Triaje nuevoTriaje = triajeService.guardar(triaje);
            response.put("success", true);
            response.put("message", "Triaje creado correctamente");
            response.put("data", nuevoTriaje);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al crear triaje: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Actualizar triaje
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> actualizar(@PathVariable Long id, @RequestBody Triaje triaje) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<Triaje> triajeExistente = triajeService.buscarPorId(id);
            if (triajeExistente.isPresent()) {
                triaje.setTriaId(id);
                Triaje triajeActualizado = triajeService.guardar(triaje);
                response.put("success", true);
                response.put("message", "Triaje actualizado correctamente");
                response.put("data", triajeActualizado);
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Triaje no encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al actualizar triaje: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Eliminar triaje
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> eliminar(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<Triaje> triaje = triajeService.buscarPorId(id);
            if (triaje.isPresent()) {
                triajeService.eliminar(id);
                response.put("success", true);
                response.put("message", "Triaje eliminado correctamente");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Triaje no encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al eliminar triaje: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Listar triajes por historia
    @GetMapping("/historia/{historiaId}")
    public ResponseEntity<Map<String, Object>> listarPorHistoria(@PathVariable Long historiaId) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Triaje> triajes = triajeService.listarPorHistoria(historiaId);
            response.put("success", true);
            response.put("message", "Triajes obtenidos correctamente");
            response.put("data", triajes);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener triajes: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Listar triajes activos por historia
    @GetMapping("/historia/{historiaId}/activos")
    public ResponseEntity<Map<String, Object>> listarActivosPorHistoria(@PathVariable Long historiaId) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Triaje> triajes = triajeService.listarActivosPorHistoria(historiaId);
            response.put("success", true);
            response.put("message", "Triajes activos obtenidos correctamente");
            response.put("data", triajes);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener triajes activos: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Obtener último triaje por historia
    @GetMapping("/historia/{historiaId}/ultimo")
    public ResponseEntity<Map<String, Object>> obtenerUltimoTriaje(@PathVariable Long historiaId) {
        Map<String, Object> response = new HashMap<>();
        try {
            Triaje triaje = triajeService.obtenerUltimoTriajePorHistoria(historiaId);
            if (triaje != null) {
                response.put("success", true);
                response.put("message", "Último triaje obtenido correctamente");
                response.put("data", triaje);
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "No se encontró triaje para esta historia");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener último triaje: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Listar triajes por rango de fechas
    @GetMapping("/rango-fechas")
    public ResponseEntity<Map<String, Object>> listarPorRangoFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Triaje> triajes = triajeService.listarPorRangoFechas(fechaInicio, fechaFin);
            response.put("success", true);
            response.put("message", "Triajes obtenidos correctamente");
            response.put("data", triajes);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener triajes: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Listar triajes por estado
    @GetMapping("/estado/{estado}")
    public ResponseEntity<Map<String, Object>> listarPorEstado(@PathVariable Boolean estado) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Triaje> triajes = triajeService.listarPorEstado(estado);
            response.put("success", true);
            response.put("message", "Triajes obtenidos correctamente");
            response.put("data", triajes);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener triajes: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
