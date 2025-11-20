package com.clinicarodriguez.clinicarodriguez.controller;

import com.clinicarodriguez.clinicarodriguez.model.Documentos;
import com.clinicarodriguez.clinicarodriguez.model.Historias;
import com.clinicarodriguez.clinicarodriguez.service.DocumentosService;
import com.clinicarodriguez.clinicarodriguez.service.FileStorageService;
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
    private HistoriasRepository historiasRepository;

    // GET: Listar todos los documentos
    @GetMapping
    public ResponseEntity<?> listarTodos() {
        HashMap<String, Object> result = new HashMap<>();
        List<Documentos> documentos = documentosService.listarTodos();
        
        result.put("success", true);
        result.put("message", "Lista de documentos");
        result.put("data", documentos);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // GET: Buscar documento por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        HashMap<String, Object> result = new HashMap<>();
        Optional<Documentos> documento = documentosService.buscarPorId(id);
        
        if (documento.isPresent()) {
            result.put("success", true);
            result.put("message", "Documento encontrado");
            result.put("data", documento.get());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            result.put("success", false);
            result.put("message", "Documento no encontrado");
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }

    // GET: Buscar documentos por historia
    @GetMapping("/historia/{histId}")
    public ResponseEntity<?> buscarPorHistoria(@PathVariable Integer histId) {
        HashMap<String, Object> result = new HashMap<>();
        List<Documentos> documentos = documentosService.buscarPorHistoria(histId);
        
        result.put("success", true);
        result.put("message", "Documentos de la historia");
        result.put("data", documentos);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // GET: Buscar documentos confidenciales
    @GetMapping("/confidenciales")
    public ResponseEntity<?> buscarConfidenciales(
            @RequestParam(defaultValue = "true") Boolean confidencial,
            @RequestParam(defaultValue = "true") Boolean estado) {
        HashMap<String, Object> result = new HashMap<>();
        List<Documentos> documentos = documentosService.buscarConfidenciales(confidencial, estado);
        
        result.put("success", true);
        result.put("message", "Documentos confidenciales");
        result.put("data", documentos);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // GET: Buscar documentos por tipo
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<?> buscarPorTipo(@PathVariable String tipo) {
        HashMap<String, Object> result = new HashMap<>();
        List<Documentos> documentos = documentosService.buscarPorTipo(tipo);
        
        result.put("success", true);
        result.put("message", "Documentos por tipo");
        result.put("data", documentos);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // GET: Buscar documentos por estado
    @GetMapping("/estado/{estado}")
    public ResponseEntity<?> buscarPorEstado(@PathVariable Boolean estado) {
        HashMap<String, Object> result = new HashMap<>();
        List<Documentos> documentos = documentosService.buscarPorEstado(estado);
        
        result.put("success", true);
        result.put("message", "Documentos por estado");
        result.put("data", documentos);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    /**
     * GET: Buscar documentos visibles para paciente por DNI
     * Solo retorna documentos donde:
     * - docuVisiblePaciente = true
     * - docuConfidencial = false
     * - docuEstado = true
     * 
     * Ejemplo: GET /api/documentos/paciente/dni/12345678
     */
    @GetMapping("/paciente/dni/{dni}")
    public ResponseEntity<?> buscarPorPacienteDni(@PathVariable String dni) {
        HashMap<String, Object> result = new HashMap<>();
        List<Documentos> documentos = documentosService.buscarPorPacienteDni(dni);
        
        if (documentos.isEmpty()) {
            result.put("success", true);
            result.put("message", "No se encontraron documentos para el paciente con DNI: " + dni);
            result.put("data", documentos);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        
        result.put("success", true);
        result.put("message", "Documentos del paciente con DNI: " + dni);
        result.put("data", documentos);
        result.put("total", documentos.size());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // POST: Crear nuevo documento
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Documentos documento) {
        HashMap<String, Object> result = new HashMap<>();
        Documentos nuevoDocumento = documentosService.guardar(documento);
        
        result.put("success", true);
        result.put("message", "Documento creado correctamente");
        result.put("data", nuevoDocumento);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    // PUT: Actualizar documento existente
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(
            @PathVariable Integer id,
            @RequestBody Documentos documento) {
        HashMap<String, Object> result = new HashMap<>();
        
        try {
            Documentos documentoActualizado = documentosService.actualizar(id, documento);
            result.put("success", true);
            result.put("message", "Documento actualizado correctamente");
            result.put("data", documentoActualizado);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (RuntimeException e) {
            result.put("success", false);
            result.put("message", "Documento no encontrado con id: " + id);
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }

    // DELETE: Eliminar documento
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        HashMap<String, Object> result = new HashMap<>();
        
        try {
            documentosService.eliminar(id);
            result.put("success", true);
            result.put("message", "Documento eliminado correctamente");
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (RuntimeException e) {
            result.put("success", false);
            result.put("message", "Documento no encontrado con id: " + id);
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }
    
    /**
     * Endpoint para subir un archivo (foto o PDF) y crear el registro de documento
     * @param file - Archivo (imagen o PDF)
     * @param histId - ID de la historia (obligatorio)
     * @param nombre - Nombre descriptivo del documento
     * @param tipo - Tipo de documento (ej: "Receta", "Resultado", "Radiografía")
     * @param visiblePaciente - Si el paciente puede ver el documento
     * @param confidencial - Si el documento es confidencial
     * @return ResponseEntity con el resultado
     */
    @PostMapping("/upload")
    public ResponseEntity<?> uploadDocumento(
            @RequestParam("file") MultipartFile file,
            @RequestParam("histId") Integer histId,
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
        
        // Validar que la historia existe
        Optional<Historias> historiaOpt = historiasRepository.findById(histId);
        if (historiaOpt.isEmpty()) {
            result.put("success", false);
            result.put("message", "No existe historia con id: " + histId);
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        Historias historia = historiaOpt.get();
        
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
