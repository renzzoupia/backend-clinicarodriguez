package com.clinicarodriguez.clinicarodriguez.controller;

import com.clinicarodriguez.clinicarodriguez.model.CategoriasActivo;
import com.clinicarodriguez.clinicarodriguez.service.CategoriasActivoService;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categorias-activo")
public class CategoriasActivoController {

    @Autowired
    private CategoriasActivoService categoriasActivoService;

    // Listar todas las categorías
    @GetMapping
    public ResponseEntity<?> findAll() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "Lista de Categorías de Activo");
        result.put("data", categoriasActivoService.findAll());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // Obtener categoría por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        HashMap<String, Object> result = new HashMap<>();
        Optional<CategoriasActivo> categoria = categoriasActivoService.findById(id);

        if (categoria.isPresent()) {
            result.put("success", true);
            result.put("message", "Categoría encontrada");
            result.put("data", categoria.get());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            result.put("success", false);
            result.put("message", "No se encontró la categoría con id: " + id);
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }

    // Buscar categoría por nombre
    @GetMapping("/nombre/{nombreCategoria}")
    public ResponseEntity<?> findByNombreCategoria(@PathVariable String nombreCategoria) {
        HashMap<String, Object> result = new HashMap<>();
        Optional<CategoriasActivo> categoria = categoriasActivoService.findByNombreCategoria(nombreCategoria);

        if (categoria.isPresent()) {
            result.put("success", true);
            result.put("message", "Categoría encontrada");
            result.put("data", categoria.get());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            result.put("success", false);
            result.put("message", "No se encontró categoría con nombre: " + nombreCategoria);
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }

    // Buscar categorías por estado
    @GetMapping("/estado/{estado}")
    public ResponseEntity<?> findByEstado(@PathVariable Integer estado) {
        HashMap<String, Object> result = new HashMap<>();
        List<CategoriasActivo> categorias = categoriasActivoService.findByEstado(estado);
        
        result.put("success", true);
        result.put("message", "Categorías con estado: " + estado);
        result.put("data", categorias);
        result.put("total", categorias.size());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // Verificar si existe categoría por nombre
    @GetMapping("/exists/{nombreCategoria}")
    public ResponseEntity<?> existsByNombreCategoria(@PathVariable String nombreCategoria) {
        HashMap<String, Object> result = new HashMap<>();
        boolean exists = categoriasActivoService.existsByNombreCategoria(nombreCategoria);
        
        result.put("success", true);
        result.put("message", "Verificación de nombre de categoría");
        result.put("exists", exists);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // Crear categoría
    @PostMapping
    public ResponseEntity<?> create(@RequestBody CategoriasActivo categoriasActivo) {
        HashMap<String, Object> result = new HashMap<>();
        
        try {
            // Validaciones básicas
            if (categoriasActivo.getCaacNombreCategoria() == null || categoriasActivo.getCaacNombreCategoria().isEmpty()) {
                result.put("success", false);
                result.put("message", "El nombre de la categoría es requerido");
                return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
            }
            
            // Verificar si el nombre de categoría ya existe
            if (categoriasActivoService.existsByNombreCategoria(categoriasActivo.getCaacNombreCategoria())) {
                result.put("success", false);
                result.put("message", "Ya existe una categoría con el nombre: " + categoriasActivo.getCaacNombreCategoria());
                return new ResponseEntity<>(result, HttpStatus.CONFLICT);
            }
            
            CategoriasActivo nuevaCategoria = categoriasActivoService.save(categoriasActivo);
            result.put("success", true);
            result.put("message", "Categoría creada exitosamente");
            result.put("data", nuevaCategoria);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
            
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "Error al crear la categoría: " + e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Actualizar categoría
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody CategoriasActivo categoriasActivo) {
        HashMap<String, Object> result = new HashMap<>();
        
        try {
            Optional<CategoriasActivo> categoriaExistente = categoriasActivoService.findById(id);

            if (categoriaExistente.isEmpty()) {
                result.put("success", false);
                result.put("message", "No existe categoría con Id: " + id);
                return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
            }

            // Verificar si el nuevo nombre ya existe (excluyendo la categoría actual)
            CategoriasActivo categoriaActual = categoriaExistente.get();
            if (!categoriaActual.getCaacNombreCategoria().equals(categoriasActivo.getCaacNombreCategoria()) &&
                categoriasActivoService.existsByNombreCategoria(categoriasActivo.getCaacNombreCategoria())) {
                result.put("success", false);
                result.put("message", "Ya existe una categoría con el nombre: " + categoriasActivo.getCaacNombreCategoria());
                return new ResponseEntity<>(result, HttpStatus.CONFLICT);
            }

            CategoriasActivo categoriaActualizada = categoriasActivoService.update(id, categoriasActivo);
            
            result.put("success", true);
            result.put("message", "Categoría actualizada correctamente");
            result.put("data", categoriaActualizada);
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

    // Eliminar categoría
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        HashMap<String, Object> result = new HashMap<>();

        Optional<CategoriasActivo> categoria = categoriasActivoService.findById(id);

        if (categoria.isEmpty()) {
            result.put("success", false);
            result.put("message", "No existe categoría con id: " + id);
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }

        try {
            // Aquí podrías agregar validaciones adicionales
            // Por ejemplo, verificar si la categoría tiene activos asociados
            // antes de permitir la eliminación
            
            categoriasActivoService.deleteById(id);
            result.put("success", true);
            result.put("message", "Categoría eliminada correctamente");
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "Error al eliminar la categoría: " + e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // Endpoint adicional: Listar categorías activas
    @GetMapping("/activas")
    public ResponseEntity<?> findActivas() {
        HashMap<String, Object> result = new HashMap<>();
        List<CategoriasActivo> categoriasActivas = categoriasActivoService.findByEstado(1); // Asumiendo que 1 = activo
        
        result.put("success", true);
        result.put("message", "Categorías activas");
        result.put("data", categoriasActivas);
        result.put("total", categoriasActivas.size());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
