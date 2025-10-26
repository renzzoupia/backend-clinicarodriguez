package com.clinicarodriguez.clinicarodriguez.controller;

import com.clinicarodriguez.clinicarodriguez.model.Usuarios;
import com.clinicarodriguez.clinicarodriguez.repository.UsuariosRepository;
import com.clinicarodriguez.clinicarodriguez.service.FileStorageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@CrossOrigin(origins = {"http://localhost"})
@RestController
@RequestMapping("/api/usuarios")
public class UsuariosController {
    
    @Autowired
    private UsuariosRepository usuariosRepository;
    
    @Autowired
    private FileStorageService fileStorageService;
    
    @GetMapping()
    public ResponseEntity<?> findAll() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "Lista de Usuarios");
        result.put("data", usuariosRepository.findAll());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Usuarios> findById(@PathVariable Long id) {
        Optional<Usuarios> usuario = usuariosRepository.findById(id);

        if (usuario.isPresent()) {
            return ResponseEntity.ok(usuario.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        HashMap<String, Object> result = new HashMap<>();

        Optional<Usuarios> data = usuariosRepository.findById(id);

        if (data.isEmpty()) {
            result.put("success", false);
            result.put("message", "No existe usuario con id: " + id);
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        } else {
            usuariosRepository.deleteById(id);
            result.put("success", true);
            result.put("message", "Usuario eliminado correctamente");
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Usuarios usuario) {
        HashMap<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "Usuario registrado correctamente");
        result.put("data", usuariosRepository.save(usuario));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    /**
     * Endpoint para crear usuario con foto opcional
     * @param usuarioJson - Datos del usuario en formato JSON string
     * @param foto - Archivo de foto (opcional)
     * @return ResponseEntity con el resultado
     */
    @PostMapping("/with-photo")
    public ResponseEntity<?> saveWithPhoto(
            @RequestParam("usuario") String usuarioJson,
            @RequestParam(value = "foto", required = false) MultipartFile foto) {
        
        HashMap<String, Object> result = new HashMap<>();
        
        try {
            // Convertir JSON string a objeto Usuario
            ObjectMapper objectMapper = new ObjectMapper();
            Usuarios usuario = objectMapper.readValue(usuarioJson, Usuarios.class);
            
            // Si se envió una foto, procesarla
            if (foto != null && !foto.isEmpty()) {
                // Validar que sea una imagen
                String contentType = foto.getContentType();
                if (contentType == null || !contentType.startsWith("image/")) {
                    result.put("success", false);
                    result.put("message", "El archivo debe ser una imagen (JPG, PNG, etc.)");
                    return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
                }
                
                // Guardar la foto en la carpeta "usuarios"
                String fileName = fileStorageService.storeFile(foto, "usuarios");
                
                // Construir la URL completa del archivo
                String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/api/files/")
                        .path(fileName)
                        .toUriString();
                
                // Asignar la URL al usuario
                usuario.setUsuaFotoUrl(fileDownloadUri);
            }
            
            // Guardar el usuario en la base de datos
            Usuarios usuarioGuardado = usuariosRepository.save(usuario);
            
            result.put("success", true);
            result.put("message", "Usuario registrado correctamente");
            result.put("data", usuarioGuardado);
            
            return new ResponseEntity<>(result, HttpStatus.CREATED);
            
        } catch (Exception ex) {
            result.put("success", false);
            result.put("message", "Error al registrar usuario: " + ex.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody Usuarios usuario) {
        HashMap<String, Object> result = new HashMap<>();
        Optional<Usuarios> data = usuariosRepository.findById(id);

        if (data.isEmpty()) {
            result.put("success", false);
            result.put("message", "No existe registro con Id: " + id);
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }

        try {
            Usuarios existingUsuario = data.get();
            existingUsuario.setUsuaUsername(usuario.getUsuaUsername());
            existingUsuario.setUsuaNombrecompleto(usuario.getUsuaNombrecompleto());
            existingUsuario.setUsuaClave(usuario.getUsuaClave());
            existingUsuario.setUsuaDni(usuario.getUsuaDni());
            existingUsuario.setUsuaEmail(usuario.getUsuaEmail());
            existingUsuario.setUsuaTelefono(usuario.getUsuaTelefono());
            existingUsuario.setUsuaFotoUrl(usuario.getUsuaFotoUrl());
            //existingUsuario.setUsuaEstado(usuario.getUsuaEstado());
            existingUsuario.setUsuaEsActivo(usuario.getUsuaEsActivo());
            usuariosRepository.save(existingUsuario);

            result.put("success", true);
            result.put("message", "Datos actualizados correctamente.");
            result.put("data", existingUsuario);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            result.put("success", false);
            result.put("message", "Error al actualizar: " + ex.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

