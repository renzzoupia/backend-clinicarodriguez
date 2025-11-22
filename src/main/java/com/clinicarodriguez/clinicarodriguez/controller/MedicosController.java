/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.clinicarodriguez.clinicarodriguez.controller;

import com.clinicarodriguez.clinicarodriguez.dto.RegistrarMedicoDTO;
import com.clinicarodriguez.clinicarodriguez.model.Medicos;
import com.clinicarodriguez.clinicarodriguez.model.Personas;
import com.clinicarodriguez.clinicarodriguez.dto.MedicoConEspecialidadesDTO;
import com.clinicarodriguez.clinicarodriguez.dto.MedicoConEspecialidadesDTO.EspecialidadSimpleDTO;
import com.clinicarodriguez.clinicarodriguez.dto.MedicoConEspecialidadesDTO.UsuarioSimpleDTO;
import com.clinicarodriguez.clinicarodriguez.dto.VerMedicosDTO;
import com.clinicarodriguez.clinicarodriguez.dto.VerMedicosDTO.EspecialidadDTO;
import com.clinicarodriguez.clinicarodriguez.service.FileStorageService;
import com.clinicarodriguez.clinicarodriguez.service.MedicosService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.clinicarodriguez.clinicarodriguez.repository.MedicosRepository;
import java.util.ArrayList;
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
    private MedicosService medicosService;
    
    @Autowired
    private FileStorageService fileStorageService;
    
    @GetMapping()
    public ResponseEntity<?> findAll() {
        HashMap<String, Object> result = new HashMap<>();
        
        // Obtener todos los médicos
        List<Medicos> medicos = medicoRepository.findAll();
        
        // Convertir a DTO con especialidades
        List<MedicoConEspecialidadesDTO> medicosDTO = medicos.stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
        
        result.put("success", true);
        result.put("message", "Lista de Médicos");
        result.put("data", medicosDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    @GetMapping("/ver-medicos")
    public ResponseEntity<?> verMedicos() {
        HashMap<String, Object> result = new HashMap<>();
        
        // Obtener todos los médicos
        List<Medicos> medicos = medicoRepository.findAll();
        
        // Convertir a DTO simplificado (sin usuario ni persona)
        List<VerMedicosDTO> medicosDTO = medicos.stream()
            .map(this::convertirAVerMedicosDTO)
            .collect(Collectors.toList());
        
        result.put("success", true);
        result.put("message", "Lista de Médicos");
        result.put("data", medicosDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        HashMap<String, Object> result = new HashMap<>();
        Optional<Medicos> medico = medicoRepository.findById(id);

        if (medico.isPresent()) {
            MedicoConEspecialidadesDTO medicoDTO = convertirADTO(medico.get());
            result.put("success", true);
            result.put("message", "Médico encontrado");
            result.put("data", medicoDTO);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            result.put("success", false);
            result.put("message", "Médico no encontrado");
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/persona/{personaId}")
    public ResponseEntity<?> findByPersonaId(@PathVariable Integer personaId) {
        HashMap<String, Object> result = new HashMap<>();
        Optional<Medicos> medico = medicoRepository.findByPersonaId(personaId);

        if (medico.isPresent()) {
            MedicoConEspecialidadesDTO medicoDTO = convertirADTO(medico.get());
            result.put("success", true);
            result.put("message", "Médico encontrado por persona");
            result.put("data", medicoDTO);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            result.put("success", false);
            result.put("message", "No existe médico asociado a la persona con id: " + personaId);
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Integer id) {
        HashMap<String, Object> result = new HashMap<>();

        Optional<Medicos> data = medicoRepository.findById(id);

        if (data.isEmpty()) {
            result.put("success", false);
            result.put("message", "No existe médico con id: " + id);
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        } else {
            //medicoRepository.deleteById(id);
            medicoRepository.delete(data.get()); 
            result.put("success", true);
            result.put("message", "Médico y usuario eliminados correctamente");
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

    /**
     * Endpoint para registrar médico completo con Persona, Usuario y Médico
     * Soporta multipart/form-data con foto opcional
     * @param medicoJson - Datos del médico en formato JSON string
     * @param foto - Archivo de foto (opcional)
     * @return ResponseEntity con el resultado
     */
    @PostMapping("/registrar")
    public ResponseEntity<?> registrarMedico(
            @RequestParam("medico") String medicoJson,
            @RequestParam(value = "foto", required = false) MultipartFile foto) {
        
        HashMap<String, Object> result = new HashMap<>();
        
        try {
            // Convertir JSON string a DTO
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            RegistrarMedicoDTO dto = objectMapper.readValue(medicoJson, RegistrarMedicoDTO.class);
            
            // Validaciones
            if (dto.getNombrecompleto() == null || dto.getNombrecompleto().isEmpty()) {
                result.put("success", false);
                result.put("message", "El nombre completo es requerido");
                return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
            }
            
            if (dto.getTipoDoc() == null) {
                result.put("success", false);
                result.put("message", "El tipo de documento es requerido");
                return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
            }
            
            if (dto.getNroDoc() == null || dto.getNroDoc().isEmpty()) {
                result.put("success", false);
                result.put("message", "El número de documento es requerido");
                return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
            }
            
            if (dto.getUsername() == null || dto.getUsername().isEmpty()) {
                result.put("success", false);
                result.put("message", "El nombre de usuario es requerido");
                return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
            }
            
            if (dto.getPassword() == null || dto.getPassword().isEmpty()) {
                result.put("success", false);
                result.put("message", "La contraseña es requerida");
                return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
            }
            
            if (dto.getNroColegiatura() == null || dto.getNroColegiatura().isEmpty()) {
                result.put("success", false);
                result.put("message", "El número de colegiatura es requerido");
                return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
            }
            
            // Procesar foto si se envió
            String fotoUrl = null;
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
                fotoUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/api/files/")
                        .path(fileName)
                        .toUriString();
            }
            
            // Construir entidad Personas
            Personas persona = new Personas();
            persona.setPersNombrecompleto(dto.getNombrecompleto());
            persona.setPersTipoDoc(dto.getTipoDoc());
            persona.setPersNroDoc(dto.getNroDoc());
            persona.setPersSexo(dto.getSexo());
            persona.setPersFecNacimiento(dto.getFecNacimiento());
            persona.setPersEstadoCivil(dto.getEstadoCivil());
            persona.setPersTelefono(dto.getTelefono());
            persona.setPersEmail(dto.getEmail());
            persona.setPersDireccion(dto.getDireccion());
            persona.setPersFotoUrl(fotoUrl);
            persona.setPersEsActivo(true);
            
            // Crear médico con persona y usuario
            Medicos medicoGuardado = medicosService.crearMedicoConUsuario(
                persona,
                dto.getUsername(),
                dto.getPassword(),
                dto.getNroColegiatura()
            );
            
            // Convertir a DTO de respuesta
            MedicoConEspecialidadesDTO responseDTO = convertirADTO(medicoGuardado);
            
            result.put("success", true);
            result.put("message", "Médico registrado exitosamente");
            result.put("data", responseDTO);
            if (fotoUrl != null) {
                result.put("fotoUrl", fotoUrl);
            }
            
            return new ResponseEntity<>(result, HttpStatus.CREATED);
            
        } catch (RuntimeException e) {
            result.put("success", false);
            result.put("message", e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            result.put("success", false);
            result.put("message", "Error al registrar médico: " + ex.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
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
                //medico.setMediFotoUrl(fileDownloadUri);
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
    public ResponseEntity<?> update(@PathVariable("id") Integer id, @RequestBody Medicos medico) {
        HashMap<String, Object> result = new HashMap<>();
        Optional<Medicos> data = medicoRepository.findById(id);

        if (data.isEmpty()) {
            result.put("success", false);
            result.put("message", "No existe registro con Id: " + id);
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }

        try {
            Medicos existingMedico = data.get();
            //existingMedico.setMediNombre(medico.getMediNombre());
            //existingMedico.setMediApellido(medico.getMediApellido());
            //existingMedico.setMediFotoUrl(medico.getMediFotoUrl());
            existingMedico.setMediEstado(medico.getMediEstado());
            //existingMedico.setUsuario(medico.getUsuario());
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
    public ResponseEntity<?> uploadFoto(@PathVariable Integer id, @RequestParam("file") MultipartFile file) {
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
            //if (medico.getMediFotoUrl() != null && !medico.getMediFotoUrl().isEmpty()) {
               // try {
                    // Extraer el nombre del archivo de la URL
                    //String oldFileName = medico.getMediFotoUrl().substring(medico.getMediFotoUrl().lastIndexOf("/") + 1);
                  //  fileStorageService.deleteFile("medicos/" + oldFileName);
               // } catch (Exception e) {
                    // Ignorar errores al eliminar el archivo anterior
                //}
            //}
            
            //medico.setMediFotoUrl(fileDownloadUri);
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
    
    /**
     * Método auxiliar para convertir un Medico a DTO con especialidades
     */
    private MedicoConEspecialidadesDTO convertirADTO(Medicos medico) {
        List<EspecialidadSimpleDTO> especialidades = medico.getMedicosEspecialidades()
            .stream()
            .map(me -> new EspecialidadSimpleDTO(
                me.getEspecialidad().getEspeId(),
                me.getEspecialidad().getEspeNombre(),
                me.getEspecialidad().getEspeDescripcion()
            ))
            .collect(Collectors.toList());
        
        
        
        // Los datos personales ahora vienen de la tabla Personas
        return new MedicoConEspecialidadesDTO(
            medico.getMediId(),
            medico.getPersona().getPersNombrecompleto(),
            medico.getPersona().getPersFotoUrl(),
            medico.getMediEstado() ? "ACTIVO" : "INACTIVO",
            medico.getUsuarios(), // Ya no hay relación directa con Usuario
            especialidades
            
        );
    }
    
    /**
     * Método auxiliar para convertir un Medico a DTO simplificado
     * Solo muestra: ID, nombre, foto y especialidades
     */
    private VerMedicosDTO convertirAVerMedicosDTO(Medicos medico) {
        List<EspecialidadDTO> especialidades = medico.getMedicosEspecialidades()
            .stream()
            .map(me -> new EspecialidadDTO(
                me.getEspecialidad().getEspeId(),
                me.getEspecialidad().getEspeNombre(),
                me.getEspecialidad().getEspeDescripcion()
            ))
            .collect(Collectors.toList());
        
        return new VerMedicosDTO(
            medico.getMediId(),
            medico.getPersona().getPersNombrecompleto(),
            medico.getPersona().getPersFotoUrl(),
            especialidades
        );
    }
}
