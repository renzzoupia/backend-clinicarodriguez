package com.clinicarodriguez.clinicarodriguez.controller;

import com.clinicarodriguez.clinicarodriguez.dto.AreaSimpleDTO;
import com.clinicarodriguez.clinicarodriguez.model.Areas;
import com.clinicarodriguez.clinicarodriguez.service.AreasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/areas")
public class AreasController {

    @Autowired
    private AreasService areasService;

    // Listar todas las áreas (jerárquico - para tablas)
    @GetMapping
    public ResponseEntity<Map<String, Object>> listarTodas() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Areas> todasLasAreas = areasService.listarTodas();

            // ✨ SOLUCIÓN: Filtrar solo áreas raíz
            List<Areas> areasRaiz = todasLasAreas.stream()
                .filter(area -> area.getAreaPadre() == null)
                .collect(Collectors.toList());

            response.put("success", true);
            response.put("message", "Áreas obtenidas correctamente");
            response.put("data", areasRaiz); // Las sub-áreas vienen automáticamente por @JsonManagedReference
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener áreas: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    // Listar todas las áreas de forma plana (sin jerarquía - para formularios <select>)
    @GetMapping("/normal")
    public ResponseEntity<Map<String, Object>> listarNormal() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Areas> todasLasAreas = areasService.listarTodas();
            
            // Convertir a DTO simple sin relaciones jerárquicas
            List<AreaSimpleDTO> areasSimples = todasLasAreas.stream()
                .map(area -> new AreaSimpleDTO(
                    area.getAreaId(),
                    area.getAreaNombre(),
                    area.getAreaDescripcion()
                ))
                .collect(Collectors.toList());
            
            response.put("success", true);
            response.put("message", "Lista de áreas para formulario");
            response.put("data", areasSimples);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener áreas: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Buscar área por ID
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> buscarPorId(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<Areas> area = areasService.buscarPorId(id);
            if (area.isPresent()) {
                response.put("success", true);
                response.put("message", "Área encontrada");
                response.put("data", area.get());
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Área no encontrada");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al buscar área: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Crear nueva área
    @PostMapping
    public ResponseEntity<Map<String, Object>> crear(@RequestBody Areas area) {
        Map<String, Object> response = new HashMap<>();
        try {
            Areas nuevaArea = areasService.guardar(area);
            response.put("success", true);
            response.put("message", "Área creada correctamente");
            response.put("data", nuevaArea);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al crear área: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Actualizar área
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> actualizar(@PathVariable Integer id, @RequestBody Areas area) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<Areas> areaExistente = areasService.buscarPorId(id);
            if (areaExistente.isPresent()) {
                area.setAreaId(id);
                Areas areaActualizada = areasService.guardar(area);
                response.put("success", true);
                response.put("message", "Área actualizada correctamente");
                response.put("data", areaActualizada);
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Área no encontrada");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al actualizar área: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Eliminar área
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> eliminar(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<Areas> area = areasService.buscarPorId(id);
            if (area.isPresent()) {
                // Verificar si tiene subáreas antes de eliminar
                if (areasService.tieneSubAreas(id)) {
                    response.put("success", false);
                    response.put("message", "No se puede eliminar el área porque tiene subáreas asociadas");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                }
                areasService.eliminar(id);
                response.put("success", true);
                response.put("message", "Área eliminada correctamente");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Área no encontrada");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al eliminar área: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Listar áreas raíz (sin padre)
    @GetMapping("/raiz")
    public ResponseEntity<Map<String, Object>> listarAreasRaiz() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Areas> areasRaiz = areasService.listarAreasRaiz();
            response.put("success", true);
            response.put("message", "Áreas raíz obtenidas correctamente");
            response.put("data", areasRaiz);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener áreas raíz: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Listar subáreas de un área padre
    @GetMapping("/padre/{areaPadreId}")
    public ResponseEntity<Map<String, Object>> listarSubAreas(@PathVariable Integer areaPadreId) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Areas> subAreas = areasService.listarSubAreasPorPadre(areaPadreId);
            response.put("success", true);
            response.put("message", "Subáreas obtenidas correctamente");
            response.put("data", subAreas);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener subáreas: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Buscar áreas por nombre
    @GetMapping("/buscar")
    public ResponseEntity<Map<String, Object>> buscarPorNombre(@RequestParam String nombre) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Areas> areas = areasService.buscarPorNombre(nombre);
            response.put("success", true);
            response.put("message", "Búsqueda completada");
            response.put("data", areas);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al buscar áreas: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Verificar si un área tiene subáreas
    @GetMapping("/{id}/tiene-subareas")
    public ResponseEntity<Map<String, Object>> tieneSubAreas(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        try {
            boolean tieneSubAreas = areasService.tieneSubAreas(id);
            response.put("success", true);
            response.put("message", "Verificación completada");
            response.put("data", Map.of("tieneSubAreas", tieneSubAreas));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al verificar subáreas: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
