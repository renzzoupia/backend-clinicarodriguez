package com.clinicarodriguez.clinicarodriguez.controller;

import com.clinicarodriguez.clinicarodriguez.model.Documentos;
import com.clinicarodriguez.clinicarodriguez.service.DocumentosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/documentos")
public class DocumentosController {

    @Autowired
    private DocumentosService documentosService;

    // GET: Listar todos los documentos
    @GetMapping
    public ResponseEntity<List<Documentos>> listarTodos() {
        List<Documentos> documentos = documentosService.listarTodos();
        return ResponseEntity.ok(documentos);
    }

    // GET: Buscar documento por ID
    @GetMapping("/{id}")
    public ResponseEntity<Documentos> buscarPorId(@PathVariable Long id) {
        Optional<Documentos> documento = documentosService.buscarPorId(id);
        return documento.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET: Buscar documentos por paciente
    @GetMapping("/paciente/{paciId}")
    public ResponseEntity<List<Documentos>> buscarPorPaciente(@PathVariable Long paciId) {
        List<Documentos> documentos = documentosService.buscarPorPaciente(paciId);
        return ResponseEntity.ok(documentos);
    }

    // GET: Buscar documentos por historia
    @GetMapping("/historia/{histId}")
    public ResponseEntity<List<Documentos>> buscarPorHistoria(@PathVariable Long histId) {
        List<Documentos> documentos = documentosService.buscarPorHistoria(histId);
        return ResponseEntity.ok(documentos);
    }

    // GET: Buscar documentos por paciente y estado
    @GetMapping("/paciente/{paciId}/estado/{estado}")
    public ResponseEntity<List<Documentos>> buscarPorPacienteYEstado(
            @PathVariable Long paciId,
            @PathVariable Boolean estado) {
        List<Documentos> documentos = documentosService.buscarPorPacienteYEstado(paciId, estado);
        return ResponseEntity.ok(documentos);
    }

    // GET: Buscar documentos visibles para paciente
    @GetMapping("/paciente/{paciId}/visibles")
    public ResponseEntity<List<Documentos>> buscarVisiblesParaPaciente(
            @PathVariable Long paciId,
            @RequestParam(defaultValue = "true") Boolean visiblePaciente,
            @RequestParam(defaultValue = "true") Boolean estado) {
        List<Documentos> documentos = documentosService.buscarVisiblesParaPaciente(paciId, visiblePaciente, estado);
        return ResponseEntity.ok(documentos);
    }

    // GET: Buscar documentos confidenciales
    @GetMapping("/confidenciales")
    public ResponseEntity<List<Documentos>> buscarConfidenciales(
            @RequestParam(defaultValue = "true") Boolean confidencial,
            @RequestParam(defaultValue = "true") Boolean estado) {
        List<Documentos> documentos = documentosService.buscarConfidenciales(confidencial, estado);
        return ResponseEntity.ok(documentos);
    }

    // GET: Buscar documentos por tipo
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Documentos>> buscarPorTipo(@PathVariable String tipo) {
        List<Documentos> documentos = documentosService.buscarPorTipo(tipo);
        return ResponseEntity.ok(documentos);
    }

    // GET: Buscar documentos por tipo y paciente
    @GetMapping("/tipo/{tipo}/paciente/{paciId}")
    public ResponseEntity<List<Documentos>> buscarPorTipoYPaciente(
            @PathVariable String tipo,
            @PathVariable Long paciId) {
        List<Documentos> documentos = documentosService.buscarPorTipoYPaciente(tipo, paciId);
        return ResponseEntity.ok(documentos);
    }

    // GET: Buscar documentos por estado
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Documentos>> buscarPorEstado(@PathVariable Boolean estado) {
        List<Documentos> documentos = documentosService.buscarPorEstado(estado);
        return ResponseEntity.ok(documentos);
    }

    // POST: Crear nuevo documento
    @PostMapping
    public ResponseEntity<Documentos> crear(@RequestBody Documentos documento) {
        Documentos nuevoDocumento = documentosService.guardar(documento);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoDocumento);
    }

    // PUT: Actualizar documento existente
    @PutMapping("/{id}")
    public ResponseEntity<Documentos> actualizar(
            @PathVariable Long id,
            @RequestBody Documentos documento) {
        try {
            Documentos documentoActualizado = documentosService.actualizar(id, documento);
            return ResponseEntity.ok(documentoActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE: Eliminar documento
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            documentosService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
