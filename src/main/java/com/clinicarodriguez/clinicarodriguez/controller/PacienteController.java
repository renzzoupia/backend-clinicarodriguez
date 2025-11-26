package com.clinicarodriguez.clinicarodriguez.controller;

import com.clinicarodriguez.clinicarodriguez.dto.PacienteConPersonaDTO;
import com.clinicarodriguez.clinicarodriguez.dto.PacienteConPersonaDTO.ApoderadoSimpleDTO;
import com.clinicarodriguez.clinicarodriguez.dto.PacienteSimpleDTO;
import com.clinicarodriguez.clinicarodriguez.dto.RegistrarPacienteDTO;
import com.clinicarodriguez.clinicarodriguez.model.Paciente;
import com.clinicarodriguez.clinicarodriguez.model.Personas;
import com.clinicarodriguez.clinicarodriguez.service.PacienteService;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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

@CrossOrigin(origins = {"http://localhost"})
@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {
    
    @Autowired
    private PacienteService pacienteService;
    
    // Listar todos los pacientes
    @GetMapping()
    public ResponseEntity<?> findAll() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "Lista de Pacientes");
        result.put("data", pacienteService.findAll());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    // Obtener paciente por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        HashMap<String, Object> result = new HashMap<>();
        Paciente paciente = pacienteService.findById(id);

        if (paciente != null) {
            result.put("success", true);
            result.put("message", "Paciente encontrado");
            result.put("data", paciente);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            result.put("success", false);
            result.put("message", "No se encontró el paciente con id: " + id);
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }
    
    // Buscar paciente por DNI exacto
    @GetMapping("/dni/{dni}")
    public ResponseEntity<?> findByDni(@PathVariable String dni) {
        HashMap<String, Object> result = new HashMap<>();
        Optional<Paciente> paciente = pacienteService.findByDni(dni);

        if (paciente.isPresent()) {
            result.put("success", true);
            result.put("message", "Paciente encontrado");
            result.put("data", paciente.get());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            result.put("success", false);
            result.put("message", "No se encontró paciente con DNI: " + dni);
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }
    
    // Buscar pacientes por nombre (búsqueda parcial)
//    @GetMapping("/buscar")
//    public ResponseEntity<?> findByNombre(@RequestParam String nombre) {
//        HashMap<String, Object> result = new HashMap<>();
//        List<Paciente> pacientes = pacienteService.findByNombre(nombre);
//        
//        result.put("success", true);
//        result.put("message", "Resultados de búsqueda para: " + nombre);
//        result.put("data", pacientes);
//        result.put("total", pacientes.size());
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }
    
    // Listar pacientes por estado
    @GetMapping("/estado/{estado}")
    public ResponseEntity<?> findByEstado(@PathVariable Integer estado) {
        HashMap<String, Object> result = new HashMap<>();
        //List<Paciente> pacientes = pacienteService.findByEstado(estado);
        List<Paciente> pacientes = pacienteService.findByEstado(estado);
        
        if (!pacientes.isEmpty()) {
            result.put("success", true);
            result.put("message", "Pacientes encontrados");
            result.put("data", pacientes);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            result.put("success", false);
            result.put("message", "No se encontraron pacientes con ese estado");
            result.put("data", null);
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }
    
    // Contar pacientes activos
    @GetMapping("/activos/count")
    public ResponseEntity<?> countActivos() {
        HashMap<String, Object> result = new HashMap<>();
        long count = pacienteService.countPacientesActivos();
        
        result.put("success", true);
        result.put("message", "Total de pacientes activos");
        result.put("total", count);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    // Buscar pacientes por DNI (autocompletado) - SOLO SIN HISTORIA CLÍNICA
    @GetMapping("/buscar/dni")
    public ResponseEntity<?> buscarPorDni(@RequestParam(name = "dni", required = true) String dni) {
        HashMap<String, Object> result = new HashMap<>();
        
        try {
            // Validar que se proporcione al menos algo para buscar
            if (dni == null || dni.trim().isEmpty()) {
                result.put("success", false);
                result.put("message", "Debe proporcionar al menos un carácter para buscar");
                result.put("data", List.of());
                return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
            }
            
            // Buscar pacientes SIN historia clínica (máximo 10 resultados)
            List<Paciente> pacientes = pacienteService.buscarPorDniSinHistoria(dni, 10);
            
            // Convertir a DTO simplificado
            List<PacienteSimpleDTO> pacientesDTO = pacientes.stream()
                .map(p -> new PacienteSimpleDTO(
                    p.getPaciId(),
                    p.getPersona().getPersNombrecompleto(),
                    p.getPersona().getPersTipoDoc(),
                    p.getPersona().getPersNroDoc()
                ))
                .collect(Collectors.toList());
            
            result.put("success", true);
            result.put("message", "Pacientes sin historia clínica encontrados");
            result.put("data", pacientesDTO);
            result.put("total", pacientesDTO.size());
            return new ResponseEntity<>(result, HttpStatus.OK);
            
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "Error en la búsqueda: " + e.getMessage());
            result.put("data", List.of());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Registrar paciente con persona
    @PostMapping("/registrar")
    public ResponseEntity<?> registrarPaciente(@RequestBody RegistrarPacienteDTO dto) {
        HashMap<String, Object> result = new HashMap<>();
        
        try {
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
            persona.setPersFotoUrl(dto.getFotoUrl());
            persona.setPersEsActivo(true);
            
            // Crear paciente con persona y apoderado
            Paciente pacienteGuardado = pacienteService.crearPacienteConApoderado(
                persona, 
                dto.getApoderadoPersId()
            );
            
            // Convertir a DTO de respuesta
            PacienteConPersonaDTO responseDTO = convertirADTO(pacienteGuardado);
            
            result.put("success", true);
            result.put("message", "Paciente registrado exitosamente");
            result.put("data", responseDTO);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
            
        } catch (RuntimeException e) {
            result.put("success", false);
            result.put("message", e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "Error al registrar paciente: " + e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Crear paciente
    @PostMapping
    public ResponseEntity<?> save(@RequestBody Paciente paciente) {
        HashMap<String, Object> result = new HashMap<>();
        
        try {
            // Validaciones
//            if (paciente.getPaciNombrecompleto() == null || paciente.getPaciNombrecompleto().isEmpty()) {
//                result.put("success", false);
//                result.put("message", "El nombre completo es requerido");
//                return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
//            }
            
            // Verificar si el DNI ya existe (si se proporciona)
//            if (paciente.getPaciDni() != null && !paciente.getPaciDni().isEmpty()) {
//                if (pacienteService.existsByDni(paciente.getPaciDni())) {
//                    result.put("success", false);
//                    result.put("message", "Ya existe un paciente con el DNI: " + paciente.getPaciDni());
//                    return new ResponseEntity<>(result, HttpStatus.CONFLICT);
//                }
//            }
            
            // Verificar si el email ya existe (si se proporciona)
//            if (paciente.getPaciEmail() != null && !paciente.getPaciEmail().isEmpty()) {
//                if (pacienteService.existsByEmail(paciente.getPaciEmail())) {
//                    result.put("success", false);
//                    result.put("message", "Ya existe un paciente con el email: " + paciente.getPaciEmail());
//                    return new ResponseEntity<>(result, HttpStatus.CONFLICT);
//                }
//            }
            
            Paciente pacienteGuardado = pacienteService.save(paciente);
            result.put("success", true);
            result.put("message", "Paciente registrado exitosamente");
            result.put("data", pacienteGuardado);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
            
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "Error al registrar paciente: " + e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Actualizar paciente (solo estado)
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody Paciente paciente) {
        HashMap<String, Object> result = new HashMap<>();
        
        try {
            Paciente pacienteExistente = pacienteService.findById(id);

            if (pacienteExistente == null) {
                result.put("success", false);
                result.put("message", "No existe paciente con Id: " + id);
                return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
            }

            // Actualizar solo estado
            pacienteExistente.setPaciEstado(paciente.getPaciEstado());
            
            Paciente pacienteActualizado = pacienteService.save(pacienteExistente);

            result.put("success", true);
            result.put("message", "Paciente actualizado correctamente");
            result.put("data", pacienteActualizado);
            return new ResponseEntity<>(result, HttpStatus.OK);
            
        } catch (Exception ex) {
            result.put("success", false);
            result.put("message", "Error al actualizar: " + ex.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // Editar paciente completo (con datos de persona)
    @PutMapping("/editar/{id}")
    public ResponseEntity<?> editarPacienteCompleto(@PathVariable Integer id, @RequestBody RegistrarPacienteDTO dto) {
        HashMap<String, Object> result = new HashMap<>();
        
        try {
            // Buscar paciente existente
            Paciente pacienteExistente = pacienteService.findById(id);
            
            if (pacienteExistente == null) {
                result.put("success", false);
                result.put("message", "No existe paciente con Id: " + id);
                return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
            }
            
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
            
            // Actualizar datos de Persona
            Personas personaExistente = pacienteExistente.getPersona();
            personaExistente.setPersNombrecompleto(dto.getNombrecompleto());
            personaExistente.setPersTipoDoc(dto.getTipoDoc());
            personaExistente.setPersNroDoc(dto.getNroDoc());
            personaExistente.setPersSexo(dto.getSexo());
            personaExistente.setPersFecNacimiento(dto.getFecNacimiento());
            personaExistente.setPersEstadoCivil(dto.getEstadoCivil());
            personaExistente.setPersTelefono(dto.getTelefono());
            personaExistente.setPersEmail(dto.getEmail());
            personaExistente.setPersDireccion(dto.getDireccion());
            personaExistente.setPersFotoUrl(dto.getFotoUrl());
            
            // Actualizar apoderado si cambió
            if (dto.getApoderadoPersId() != null) {
                Personas apoderado = new Personas();
                apoderado.setPersId(dto.getApoderadoPersId());
                pacienteExistente.setApoderado(apoderado);
            } else {
                pacienteExistente.setApoderado(null);
            }
            
            // Guardar cambios
            Paciente pacienteActualizado = pacienteService.save(pacienteExistente);
            
            // Convertir a DTO de respuesta
            PacienteConPersonaDTO responseDTO = convertirADTO(pacienteActualizado);
            
            result.put("success", true);
            result.put("message", "Paciente actualizado exitosamente");
            result.put("data", responseDTO);
            return new ResponseEntity<>(result, HttpStatus.OK);
            
        } catch (RuntimeException e) {
            result.put("success", false);
            result.put("message", e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "Error al actualizar paciente: " + e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Eliminar paciente
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Integer id) {
        HashMap<String, Object> result = new HashMap<>();

        Paciente paciente = pacienteService.findById(id);

        if (paciente == null) {
            result.put("success", false);
            result.put("message", "No existe paciente con id: " + id);
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }

        try {
            pacienteService.deleteById(id);
            result.put("success", true);
            result.put("message", "Paciente eliminado correctamente");
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "Error al eliminar paciente: " + e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // Método auxiliar para convertir Paciente a DTO
    private PacienteConPersonaDTO convertirADTO(Paciente paciente) {
        Personas persona = paciente.getPersona();
        
        // Construir DTO del apoderado si existe
        ApoderadoSimpleDTO apoderadoDTO = null;
        if (paciente.getApoderado() != null) {
            Personas apoderado = paciente.getApoderado();
            apoderadoDTO = new ApoderadoSimpleDTO(
                apoderado.getPersId(),
                apoderado.getPersNombrecompleto(),
                apoderado.getPersTipoDoc(),
                apoderado.getPersNroDoc(),
                apoderado.getPersTelefono(),
                apoderado.getPersEmail()
            );
        }
        
        return new PacienteConPersonaDTO(
            paciente.getPaciId(),
            paciente.getPaciEstado(),
            persona.getPersId(),
            persona.getPersNombrecompleto(),
            persona.getPersTipoDoc(),
            persona.getPersNroDoc(),
            persona.getPersSexo(),
            persona.getPersFecNacimiento(),
            persona.getPersEstadoCivil(),
            persona.getPersTelefono(),
            persona.getPersEmail(),
            persona.getPersDireccion(),
            persona.getPersFotoUrl(),
            apoderadoDTO
        );
    }
}
