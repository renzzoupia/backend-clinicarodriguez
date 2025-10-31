package com.clinicarodriguez.clinicarodriguez.controller;

import com.clinicarodriguez.clinicarodriguez.model.CategoriasActivo;
import com.clinicarodriguez.clinicarodriguez.service.CategoriasActivoService;
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

    @GetMapping
    public ResponseEntity<List<CategoriasActivo>> findAll() {
        List<CategoriasActivo> categorias = categoriasActivoService.findAll();
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriasActivo> findById(@PathVariable Integer id) {
        Optional<CategoriasActivo> categoria = categoriasActivoService.findById(id);
        return categoria.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CategoriasActivo categoriasActivo) {
        try {
            if (categoriasActivoService.existsByNombreCategoria(categoriasActivo.getCaacNombreCategoria())) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("Ya existe una categoría con ese nombre");
            }
            CategoriasActivo nuevaCategoria = categoriasActivoService.save(categoriasActivo);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaCategoria);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear la categoría: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody CategoriasActivo categoriasActivo) {
        try {
            CategoriasActivo categoriaActualizada = categoriasActivoService.update(id, categoriasActivo);
            return ResponseEntity.ok(categoriaActualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error al actualizar: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            Optional<CategoriasActivo> categoria = categoriasActivoService.findById(id);
            if (categoria.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            categoriasActivoService.deleteById(id);
            return ResponseEntity.ok("Categoría eliminada correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar la categoría: " + e.getMessage());
        }
    }

    @GetMapping("/nombre/{nombreCategoria}")
    public ResponseEntity<CategoriasActivo> findByNombreCategoria(@PathVariable String nombreCategoria) {
        Optional<CategoriasActivo> categoria = categoriasActivoService.findByNombreCategoria(nombreCategoria);
        return categoria.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<CategoriasActivo>> findByEstado(@PathVariable Integer estado) {
        List<CategoriasActivo> categorias = categoriasActivoService.findByEstado(estado);
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/exists/{nombreCategoria}")
    public ResponseEntity<Boolean> existsByNombreCategoria(@PathVariable String nombreCategoria) {
        boolean exists = categoriasActivoService.existsByNombreCategoria(nombreCategoria);
        return ResponseEntity.ok(exists);
    }
}
