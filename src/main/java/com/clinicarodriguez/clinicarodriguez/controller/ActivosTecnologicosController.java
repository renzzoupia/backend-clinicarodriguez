package com.clinicarodriguez.clinicarodriguez.controller;

import com.clinicarodriguez.clinicarodriguez.model.ActivosTecnologicos;
import com.clinicarodriguez.clinicarodriguez.service.ActivosTecnologicosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/activos-tecnologicos")
public class ActivosTecnologicosController {

    @Autowired
    private ActivosTecnologicosService activosTecnologicosService;

    @GetMapping
    public ResponseEntity<List<ActivosTecnologicos>> findAll() {
        List<ActivosTecnologicos> activos = activosTecnologicosService.findAll();
        return ResponseEntity.ok(activos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActivosTecnologicos> findById(@PathVariable Integer id) {
        Optional<ActivosTecnologicos> activo = activosTecnologicosService.findById(id);
        return activo.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ActivosTecnologicos activosTecnologicos) {
        try {
            if (activosTecnologicosService.existsByCodigoActivo(activosTecnologicos.getActeCodigoActivo())) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("Ya existe un activo con ese código");
            }
            if (activosTecnologicos.getActeNumeroSerie() != null && 
                activosTecnologicosService.existsByNumeroSerie(activosTecnologicos.getActeNumeroSerie())) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("Ya existe un activo con ese número de serie");
            }
            ActivosTecnologicos nuevoActivo = activosTecnologicosService.save(activosTecnologicos);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoActivo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear el activo: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody ActivosTecnologicos activosTecnologicos) {
        try {
            ActivosTecnologicos activoActualizado = activosTecnologicosService.update(id, activosTecnologicos);
            return ResponseEntity.ok(activoActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error al actualizar: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            Optional<ActivosTecnologicos> activo = activosTecnologicosService.findById(id);
            if (activo.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            activosTecnologicosService.deleteById(id);
            return ResponseEntity.ok("Activo eliminado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar el activo: " + e.getMessage());
        }
    }

    @GetMapping("/codigo/{codigoActivo}")
    public ResponseEntity<ActivosTecnologicos> findByCodigoActivo(@PathVariable String codigoActivo) {
        Optional<ActivosTecnologicos> activo = activosTecnologicosService.findByCodigoActivo(codigoActivo);
        return activo.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<?> findByCategoria(@PathVariable Integer categoriaId) {
        try {
            List<ActivosTecnologicos> activos = activosTecnologicosService.findByCategoria(categoriaId);
            return ResponseEntity.ok(activos);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<ActivosTecnologicos>> findByEstado(@PathVariable String estado) {
        List<ActivosTecnologicos> activos = activosTecnologicosService.findByEstado(estado);
        return ResponseEntity.ok(activos);
    }

    @GetMapping("/ubicacion/{ubicacion}")
    public ResponseEntity<List<ActivosTecnologicos>> findByUbicacion(@PathVariable String ubicacion) {
        List<ActivosTecnologicos> activos = activosTecnologicosService.findByUbicacion(ubicacion);
        return ResponseEntity.ok(activos);
    }

    @GetMapping("/usuario/{usuaId}")
    public ResponseEntity<List<ActivosTecnologicos>> findByUsuarioId(@PathVariable Integer usuaId) {
        List<ActivosTecnologicos> activos = activosTecnologicosService.findByUsuarioId(usuaId);
        return ResponseEntity.ok(activos);
    }

    @GetMapping("/exists/codigo/{codigoActivo}")
    public ResponseEntity<Boolean> existsByCodigoActivo(@PathVariable String codigoActivo) {
        boolean exists = activosTecnologicosService.existsByCodigoActivo(codigoActivo);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/exists/serie/{numeroSerie}")
    public ResponseEntity<Boolean> existsByNumeroSerie(@PathVariable String numeroSerie) {
        boolean exists = activosTecnologicosService.existsByNumeroSerie(numeroSerie);
        return ResponseEntity.ok(exists);
    }
}
