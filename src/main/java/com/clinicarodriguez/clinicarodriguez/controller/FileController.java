package com.clinicarodriguez.clinicarodriguez.controller;

import com.clinicarodriguez.clinicarodriguez.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("/api/files")
@CrossOrigin(origins = {"http://localhost"})
public class FileController {

    @Autowired
    private FileStorageService fileStorageService;

    /**
     * Endpoint para descargar/acceder a un archivo
     * @param fileName - Nombre del archivo (puede incluir subdirectorio, ej: "medicos/abc123.jpg")
     * @param request - HttpServletRequest para determinar el tipo de contenido
     * @return ResponseEntity con el archivo
     */
    @GetMapping("/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Cargar el archivo como Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Intentar determinar el tipo de contenido del archivo
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            // No se pudo determinar el tipo de contenido
        }

        // Si no se pudo determinar, usar tipo genérico
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
    
    /**
     * Endpoint alternativo con subdirectorio en la ruta
     * Útil para acceder a archivos con estructura de carpetas
     */
    @GetMapping("/{subdirectory}/{fileName:.+}")
    public ResponseEntity<Resource> downloadFileWithSubdirectory(
            @PathVariable String subdirectory,
            @PathVariable String fileName,
            HttpServletRequest request) {
        
        String fullPath = subdirectory + "/" + fileName;
        return downloadFile(fullPath, request);
    }
}
