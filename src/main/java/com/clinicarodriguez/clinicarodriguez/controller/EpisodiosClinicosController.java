package com.clinicarodriguez.clinicarodriguez.controller;

import com.clinicarodriguez.clinicarodriguez.model.EpisodiosClinicos;
import com.clinicarodriguez.clinicarodriguez.service.EpisodiosClinicosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/episodios-clinicos")
public class EpisodiosClinicosController {

    @Autowired
    private EpisodiosClinicosService episodiosClinicosService;

    // Listar todos los episodios clínicos
    @GetMapping
    public ResponseEntity<Map<String, Object>> listarTodos() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<EpisodiosClinicos> episodios = episodiosClinicosService.listarTodos();
            response.put("success", true);
            response.put("message", "Episodios clínicos obtenidos correctamente");
            response.put("data", episodios);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener episodios clínicos: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Buscar episodio clínico por ID
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> buscarPorId(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<EpisodiosClinicos> episodio = episodiosClinicosService.buscarPorId(id);
            if (episodio.isPresent()) {
                response.put("success", true);
                response.put("message", "Episodio clínico encontrado");
                response.put("data", episodio.get());
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Episodio clínico no encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al buscar episodio clínico: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Crear nuevo episodio clínico
    @PostMapping
    public ResponseEntity<Map<String, Object>> crear(@RequestBody EpisodiosClinicos episodio) {
        Map<String, Object> response = new HashMap<>();
        try {
            EpisodiosClinicos nuevoEpisodio = episodiosClinicosService.guardar(episodio);
            response.put("success", true);
            response.put("message", "Episodio clínico creado correctamente");
            response.put("data", nuevoEpisodio);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al crear episodio clínico: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Actualizar episodio clínico
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> actualizar(@PathVariable Integer id, @RequestBody EpisodiosClinicos episodio) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<EpisodiosClinicos> episodioExistente = episodiosClinicosService.buscarPorId(id);
            if (episodioExistente.isPresent()) {
                episodio.setEpclId(id);
                EpisodiosClinicos episodioActualizado = episodiosClinicosService.guardar(episodio);
                response.put("success", true);
                response.put("message", "Episodio clínico actualizado correctamente");
                response.put("data", episodioActualizado);
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Episodio clínico no encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al actualizar episodio clínico: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Eliminar episodio clínico
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> eliminar(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<EpisodiosClinicos> episodio = episodiosClinicosService.buscarPorId(id);
            if (episodio.isPresent()) {
                episodiosClinicosService.eliminar(id);
                response.put("success", true);
                response.put("message", "Episodio clínico eliminado correctamente");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Episodio clínico no encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al eliminar episodio clínico: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Listar episodios por historia
    @GetMapping("/historia/{historiaId}")
    public ResponseEntity<Map<String, Object>> listarPorHistoria(@PathVariable Long historiaId) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<EpisodiosClinicos> episodios = episodiosClinicosService.listarPorHistoria(historiaId);
            response.put("success", true);
            response.put("message", "Episodios clínicos obtenidos correctamente");
            response.put("data", episodios);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener episodios clínicos: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Listar episodios activos por historia
    @GetMapping("/historia/{historiaId}/activos")
    public ResponseEntity<Map<String, Object>> listarActivosPorHistoria(@PathVariable Long historiaId) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<EpisodiosClinicos> episodios = episodiosClinicosService.listarActivosPorHistoria(historiaId);
            response.put("success", true);
            response.put("message", "Episodios clínicos activos obtenidos correctamente");
            response.put("data", episodios);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener episodios clínicos activos: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Obtener último episodio por historia
    @GetMapping("/historia/{historiaId}/ultimo")
    public ResponseEntity<Map<String, Object>> obtenerUltimoEpisodio(@PathVariable Long historiaId) {
        Map<String, Object> response = new HashMap<>();
        try {
            EpisodiosClinicos episodio = episodiosClinicosService.obtenerUltimoEpisodioPorHistoria(historiaId);
            if (episodio != null) {
                response.put("success", true);
                response.put("message", "Último episodio clínico obtenido correctamente");
                response.put("data", episodio);
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "No se encontró episodio clínico para esta historia");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener último episodio clínico: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Listar episodios por tipo
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<Map<String, Object>> listarPorTipo(@PathVariable String tipo) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<EpisodiosClinicos> episodios = episodiosClinicosService.listarPorTipo(tipo);
            response.put("success", true);
            response.put("message", "Episodios clínicos obtenidos correctamente");
            response.put("data", episodios);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener episodios clínicos: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Listar episodios por rango de fechas
    @GetMapping("/rango-fechas")
    public ResponseEntity<Map<String, Object>> listarPorRangoFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<EpisodiosClinicos> episodios = episodiosClinicosService.listarPorRangoFechas(fechaInicio, fechaFin);
            response.put("success", true);
            response.put("message", "Episodios clínicos obtenidos correctamente");
            response.put("data", episodios);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener episodios clínicos: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Listar episodios por estado
    @GetMapping("/estado/{estado}")
    public ResponseEntity<Map<String, Object>> listarPorEstado(@PathVariable Boolean estado) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<EpisodiosClinicos> episodios = episodiosClinicosService.listarPorEstado(estado);
            response.put("success", true);
            response.put("message", "Episodios clínicos obtenidos correctamente");
            response.put("data", episodios);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener episodios clínicos: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Buscar episodios por diagnóstico
    @GetMapping("/diagnostico")
    public ResponseEntity<Map<String, Object>> buscarPorDiagnostico(@RequestParam String diagnostico) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<EpisodiosClinicos> episodios = episodiosClinicosService.buscarPorDiagnostico(diagnostico);
            response.put("success", true);
            response.put("message", "Búsqueda completada");
            response.put("data", episodios);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al buscar episodios clínicos: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
