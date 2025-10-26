package com.clinicarodriguez.clinicarodriguez.controller;

import com.clinicarodriguez.clinicarodriguez.model.Documentos;
import com.clinicarodriguez.clinicarodriguez.model.Paciente;
import com.clinicarodriguez.clinicarodriguez.model.Historias;
import com.clinicarodriguez.clinicarodriguez.service.DocumentosService;
import com.clinicarodriguez.clinicarodriguez.service.FileStorageService;
import com.clinicarodriguez.clinicarodriguez.repository.PacienteRepository;
import com.clinicarodriguez.clinicarodriguez.repository.HistoriasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/documentos")
@CrossOrigin(origins = {"http://localhost"})
public class DocumentosController {

    @Autowired
    private DocumentosService documentosService;
    
    @Autowired
    private FileStorageService fileStorageService;
    
    @Autowired
    private PacienteRepository pacienteRepository;
    
    @Autowired
    private HistoriasRepository historiasRepository;

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
    
    /**
     * Endpoint para subir un archivo (foto o PDF) y crear el registro de documento
     * @param file - Archivo (imagen o PDF)
     * @param paciId - ID del paciente (obligatorio)
     * @param histId - ID de la historia (opcional)
     * @param nombre - Nombre descriptivo del documento
     * @param tipo - Tipo de documento (ej: "Receta", "Resultado", "Radiografía")
     * @param visiblePaciente - Si el paciente puede ver el documento
     * @param confidencial - Si el documento es confidencial
     * @return ResponseEntity con el resultado
     */
    @PostMapping("/upload")
    public ResponseEntity<?> uploadDocumento(
            @RequestParam("file") MultipartFile file,
            @RequestParam("paciId") Long paciId,
            @RequestParam(value = "histId", required = false) Long histId,
            @RequestParam("nombre") String nombre,
            @RequestParam("tipo") String tipo,
            @RequestParam(value = "visiblePaciente", defaultValue = "true") Boolean visiblePaciente,
            @RequestParam(value = "confidencial", defaultValue = "false") Boolean confidencial) {
        
        HashMap<String, Object> result = new HashMap<>();
        
        // Validar que el archivo no esté vacío
        if (file.isEmpty()) {
            result.put("success", false);
            result.put("message", "El archivo está vacío");
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        
        // Validar tipo de archivo (solo imágenes y PDFs)
        String contentType = file.getContentType();
        if (contentType == null || 
            (!contentType.startsWith("image/") && !contentType.equals("application/pdf"))) {
            result.put("success", false);
            result.put("message", "Solo se permiten imágenes (JPG, PNG, etc.) o archivos PDF");
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        
        // Validar que el paciente existe
        Optional<Paciente> paciente = pacienteRepository.findById(paciId);
        if (paciente.isEmpty()) {
            result.put("success", false);
            result.put("message", "No existe paciente con id: " + paciId);
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        
        // Validar historia si se proporciona
        Historias historia = null;
        if (histId != null) {
            Optional<Historias> historiaOpt = historiasRepository.findById(histId);
            if (historiaOpt.isEmpty()) {
                result.put("success", false);
                result.put("message", "No existe historia con id: " + histId);
                return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
            }
            historia = historiaOpt.get();
        }
        
        try {
            // Guardar el archivo en la carpeta "documentos"
            String fileName = fileStorageService.storeFile(file, "documentos");
            
            // Construir la URL completa del archivo
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/files/")
                    .path(fileName)
                    .toUriString();
            
            // Crear el registro del documento
            Documentos documento = new Documentos();
            documento.setPaciente(paciente.get());
            documento.setHistoria(historia);
            documento.setDocuNombre(nombre);
            documento.setDocuTipo(tipo);
            documento.setDocuUrl(fileDownloadUri);
            documento.setDocuFechaSubida(LocalDateTime.now());
            documento.setDocuVisiblePaciente(visiblePaciente);
            documento.setDocuConfidencial(confidencial);
            documento.setDocuEstado(true);
            
            // Guardar en base de datos
            Documentos documentoGuardado = documentosService.guardar(documento);
            
            result.put("success", true);
            result.put("message", "Documento subido correctamente");
            result.put("data", documentoGuardado);
            result.put("fileName", fileName);
            result.put("fileDownloadUri", fileDownloadUri);
            result.put("fileType", file.getContentType());
            result.put("size", file.getSize());
            
            return new ResponseEntity<>(result, HttpStatus.CREATED);
            
        } catch (Exception ex) {
            result.put("success", false);
            result.put("message", "Error al subir el archivo: " + ex.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
