package com.clinicarodriguez.clinicarodriguez.controller;

import com.clinicarodriguez.clinicarodriguez.model.ActivosTecnologicos;
import com.clinicarodriguez.clinicarodriguez.service.ActivosTecnologicosService;
import java.util.HashMap;
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

    // Listar todos los activos tecnológicos
    @GetMapping
    public ResponseEntity<?> findAll() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "Lista de Activos Tecnológicos");
        result.put("data", activosTecnologicosService.findAll());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // Obtener activo por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        HashMap<String, Object> result = new HashMap<>();
        Optional<ActivosTecnologicos> activo = activosTecnologicosService.findById(id);

        if (activo.isPresent()) {
            result.put("success", true);
            result.put("message", "Activo tecnológico encontrado");
            result.put("data", activo.get());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            result.put("success", false);
            result.put("message", "No se encontró el activo tecnológico con id: " + id);
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }

    // Buscar activo por código de activo
    @GetMapping("/codigo/{codigoActivo}")
    public ResponseEntity<?> findByCodigoActivo(@PathVariable String codigoActivo) {
        HashMap<String, Object> result = new HashMap<>();
        Optional<ActivosTecnologicos> activo = activosTecnologicosService.findByCodigoActivo(codigoActivo);

        if (activo.isPresent()) {
            result.put("success", true);
            result.put("message", "Activo tecnológico encontrado");
            result.put("data", activo.get());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            result.put("success", false);
            result.put("message", "No se encontró activo tecnológico con código: " + codigoActivo);
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }

    // Buscar activos por categoría
    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<?> findByCategoria(@PathVariable Integer categoriaId) {
        HashMap<String, Object> result = new HashMap<>();
        try {
            List<ActivosTecnologicos> activos = activosTecnologicosService.findByCategoria(categoriaId);
            result.put("success", true);
            result.put("message", "Activos tecnológicos de la categoría: " + categoriaId);
            result.put("data", activos);
            result.put("total", activos.size());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (RuntimeException e) {
            result.put("success", false);
            result.put("message", "Error al buscar por categoría: " + e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }

    // Buscar activos por estado
    @GetMapping("/estado/{estado}")
    public ResponseEntity<?> findByEstado(@PathVariable String estado) {
        HashMap<String, Object> result = new HashMap<>();
        List<ActivosTecnologicos> activos = activosTecnologicosService.findByEstado(estado);
        
        result.put("success", true);
        result.put("message", "Activos tecnológicos con estado: " + estado);
        result.put("data", activos);
        result.put("total", activos.size());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // Buscar activos por ubicación
    @GetMapping("/ubicacion/{ubicacion}")
    public ResponseEntity<?> findByUbicacion(@PathVariable String ubicacion) {
        HashMap<String, Object> result = new HashMap<>();
        List<ActivosTecnologicos> activos = activosTecnologicosService.findByUbicacion(ubicacion);
        
        result.put("success", true);
        result.put("message", "Activos tecnológicos en ubicación: " + ubicacion);
        result.put("data", activos);
        result.put("total", activos.size());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // Buscar activos por usuario
    @GetMapping("/usuario/{usuaId}")
    public ResponseEntity<?> findByUsuarioId(@PathVariable Integer usuaId) {
        HashMap<String, Object> result = new HashMap<>();
        List<ActivosTecnologicos> activos = activosTecnologicosService.findByUsuarioId(usuaId);
        
        result.put("success", true);
        result.put("message", "Activos tecnológicos del usuario: " + usuaId);
        result.put("data", activos);
        result.put("total", activos.size());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // Verificar si existe código de activo
    @GetMapping("/exists/codigo/{codigoActivo}")
    public ResponseEntity<?> existsByCodigoActivo(@PathVariable String codigoActivo) {
        HashMap<String, Object> result = new HashMap<>();
        boolean exists = activosTecnologicosService.existsByCodigoActivo(codigoActivo);
        
        result.put("success", true);
        result.put("message", "Verificación de código de activo");
        result.put("exists", exists);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // Verificar si existe número de serie
    @GetMapping("/exists/serie/{numeroSerie}")
    public ResponseEntity<?> existsByNumeroSerie(@PathVariable String numeroSerie) {
        HashMap<String, Object> result = new HashMap<>();
        boolean exists = activosTecnologicosService.existsByNumeroSerie(numeroSerie);
        
        result.put("success", true);
        result.put("message", "Verificación de número de serie");
        result.put("exists", exists);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // Crear activo tecnológico
    @PostMapping
    public ResponseEntity<?> create(@RequestBody ActivosTecnologicos activosTecnologicos) {
        HashMap<String, Object> result = new HashMap<>();
        
        try {
            // Validaciones básicas
            if (activosTecnologicos.getActeCodigoActivo() == null || activosTecnologicos.getActeCodigoActivo().isEmpty()) {
                result.put("success", false);
                result.put("message", "El código de activo es requerido");
                return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
            }
            
            // Verificar si el código de activo ya existe
            if (activosTecnologicosService.existsByCodigoActivo(activosTecnologicos.getActeCodigoActivo())) {
                result.put("success", false);
                result.put("message", "Ya existe un activo con el código: " + activosTecnologicos.getActeCodigoActivo());
                return new ResponseEntity<>(result, HttpStatus.CONFLICT);
            }
            
            // Verificar si el número de serie ya existe (si se proporciona)
            if (activosTecnologicos.getActeNumeroSerie() != null && !activosTecnologicos.getActeNumeroSerie().isEmpty()) {
                if (activosTecnologicosService.existsByNumeroSerie(activosTecnologicos.getActeNumeroSerie())) {
                    result.put("success", false);
                    result.put("message", "Ya existe un activo con el número de serie: " + activosTecnologicos.getActeNumeroSerie());
                    return new ResponseEntity<>(result, HttpStatus.CONFLICT);
                }
            }
            
            ActivosTecnologicos nuevoActivo = activosTecnologicosService.save(activosTecnologicos);
            result.put("success", true);
            result.put("message", "Activo tecnológico creado exitosamente");
            result.put("data", nuevoActivo);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
            
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "Error al crear el activo tecnológico: " + e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Actualizar activo tecnológico
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody ActivosTecnologicos activosTecnologicos) {
        HashMap<String, Object> result = new HashMap<>();
        
        try {
            Optional<ActivosTecnologicos> activoExistente = activosTecnologicosService.findById(id);

            if (activoExistente.isEmpty()) {
                result.put("success", false);
                result.put("message", "No existe activo tecnológico con Id: " + id);
                return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
            }

            ActivosTecnologicos activoActualizado = activosTecnologicosService.update(id, activosTecnologicos);
            
            result.put("success", true);
            result.put("message", "Activo tecnológico actualizado correctamente");
            result.put("data", activoActualizado);
            return new ResponseEntity<>(result, HttpStatus.OK);
            
        } catch (RuntimeException e) {
            result.put("success", false);
            result.put("message", "Error al actualizar: " + e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            result.put("success", false);
            result.put("message", "Error interno al actualizar: " + ex.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Eliminar activo tecnológico
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        HashMap<String, Object> result = new HashMap<>();

        Optional<ActivosTecnologicos> activo = activosTecnologicosService.findById(id);

        if (activo.isEmpty()) {
            result.put("success", false);
            result.put("message", "No existe activo tecnológico con id: " + id);
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }

        try {
            activosTecnologicosService.deleteById(id);
            result.put("success", true);
            result.put("message", "Activo tecnológico eliminado correctamente");
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "Error al eliminar el activo tecnológico: " + e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
