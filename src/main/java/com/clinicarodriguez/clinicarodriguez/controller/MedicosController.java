/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clinicarodriguez.clinicarodriguez.controller;

import com.clinicarodriguez.clinicarodriguez.model.Medicos;
import com.clinicarodriguez.clinicarodriguez.service.FileStorageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.clinicarodriguez.clinicarodriguez.repository.MedicosRepository;
import java.util.Optional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@CrossOrigin(origins = {"http://localhost"})
@RestController
@RequestMapping("/api/medicos")
//@Api(value = "Microservicios de gestion de pacientes", description ="Microservicio de pacientes")
public class MedicosController {
    
    @Autowired
    private MedicosRepository medicoRepository;
    
    @Autowired
    private FileStorageService fileStorageService;
    
    @GetMapping()
    public ResponseEntity<?> findAll() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "Lista de Pacientes");
        result.put("data", medicoRepository.findAll());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Medicos> findById(@PathVariable Long id) {
        Optional<Medicos> medico = medicoRepository.findById(id);

        if (medico.isPresent()) {
            return ResponseEntity.ok(medico.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        HashMap<String, Object> result = new HashMap<>();

        Optional<Medicos> data = medicoRepository.findById(id);

        if (data.isEmpty()) {
            result.put("success", false);
            result.put("message", "No existe médico con id: " + id);
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        } else {
            medicoRepository.deleteById(id);
            result.put("success", true);
            result.put("message", "Médico eliminado correctamente");
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Medicos medico) {
        HashMap<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "Carrera registrado correctamente");
        result.put("data", medicoRepository.save(medico));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    /**
     * Endpoint para crear médico con foto opcional
     * @param medicoJson - Datos del médico en formato JSON string
     * @param foto - Archivo de foto (opcional)
     * @return ResponseEntity con el resultado
     */
    @PostMapping("/with-photo")
    public ResponseEntity<?> saveWithPhoto(
            @RequestParam("medico") String medicoJson,
            @RequestParam(value = "foto", required = false) MultipartFile foto) {
        
        HashMap<String, Object> result = new HashMap<>();
        
        try {
            // Convertir JSON string a objeto Medico
            ObjectMapper objectMapper = new ObjectMapper();
            Medicos medico = objectMapper.readValue(medicoJson, Medicos.class);
            
            // Si se envió una foto, procesarla
            if (foto != null && !foto.isEmpty()) {
                // Validar que sea una imagen
                String contentType = foto.getContentType();
                if (contentType == null || !contentType.startsWith("image/")) {
                    result.put("success", false);
                    result.put("message", "El archivo debe ser una imagen (JPG, PNG, etc.)");
                    return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
                }
                
                // Guardar la foto en la carpeta "medicos"
                String fileName = fileStorageService.storeFile(foto, "medicos");
                
                // Construir la URL completa del archivo
                String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/api/files/")
                        .path(fileName)
                        .toUriString();
                
                // Asignar la URL al médico
                medico.setMediFotoUrl(fileDownloadUri);
            }
            
            // Guardar el médico en la base de datos
            Medicos medicoGuardado = medicoRepository.save(medico);
            
            result.put("success", true);
            result.put("message", "Médico registrado correctamente");
            result.put("data", medicoGuardado);
            
            return new ResponseEntity<>(result, HttpStatus.CREATED);
            
        } catch (Exception ex) {
            result.put("success", false);
            result.put("message", "Error al registrar médico: " + ex.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody Medicos medico) {
        HashMap<String, Object> result = new HashMap<>();
        Optional<Medicos> data = medicoRepository.findById(id);

        if (data.isEmpty()) {
            result.put("success", false);
            result.put("message", "No existe registro con Id: " + id);
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }

        try {
            Medicos existingMedico = data.get();
            existingMedico.setMediNombre(medico.getMediNombre());
            existingMedico.setMediApellido(medico.getMediApellido());
            existingMedico.setMediDni(medico.getMediDni());
            existingMedico.setMediEmail(medico.getMediEmail());
            existingMedico.setMediTelefono(medico.getMediTelefono());
            existingMedico.setMediFotoUrl(medico.getMediFotoUrl());
            existingMedico.setMediEstado(medico.getMediEstado());
            medicoRepository.save(existingMedico);

            result.put("success", true);
            result.put("message", "Datos actualizados correctamente.");
            result.put("data", existingMedico);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            result.put("success", false);
            result.put("message", "Error al actualizar: " + ex.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Endpoint para subir la foto de un médico
     * @param id - ID del médico
     * @param file - Archivo de imagen
     * @return ResponseEntity con el resultado
     */
    @PostMapping("/{id}/foto")
    public ResponseEntity<?> uploadFoto(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        HashMap<String, Object> result = new HashMap<>();
        
        // Validar que el médico existe
        Optional<Medicos> data = medicoRepository.findById(id);
        if (data.isEmpty()) {
            result.put("success", false);
            result.put("message", "No existe médico con id: " + id);
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        
        // Validar que se envío un archivo
        if (file.isEmpty()) {
            result.put("success", false);
            result.put("message", "El archivo está vacío");
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        
        try {
            // Guardar el archivo en la carpeta "medicos"
            String fileName = fileStorageService.storeFile(file, "medicos");
            
            // Construir la URL completa del archivo
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/files/")
                    .path(fileName)
                    .toUriString();
            
            // Actualizar el campo mediFotoUrl del médico
            Medicos medico = data.get();
            
            // Eliminar la foto anterior si existía
            if (medico.getMediFotoUrl() != null && !medico.getMediFotoUrl().isEmpty()) {
                try {
                    // Extraer el nombre del archivo de la URL
                    String oldFileName = medico.getMediFotoUrl().substring(medico.getMediFotoUrl().lastIndexOf("/") + 1);
                    fileStorageService.deleteFile("medicos/" + oldFileName);
                } catch (Exception e) {
                    // Ignorar errores al eliminar el archivo anterior
                }
            }
            
            medico.setMediFotoUrl(fileDownloadUri);
            medicoRepository.save(medico);
            
            result.put("success", true);
            result.put("message", "Foto subida correctamente");
            result.put("fileName", fileName);
            result.put("fileDownloadUri", fileDownloadUri);
            result.put("fileType", file.getContentType());
            result.put("size", file.getSize());
            
            return new ResponseEntity<>(result, HttpStatus.OK);
            
        } catch (Exception ex) {
            result.put("success", false);
            result.put("message", "Error al subir el archivo: " + ex.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
