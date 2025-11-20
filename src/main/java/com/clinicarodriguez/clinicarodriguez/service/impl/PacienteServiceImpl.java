package com.clinicarodriguez.clinicarodriguez.service.impl;

import com.clinicarodriguez.clinicarodriguez.model.Paciente;
import com.clinicarodriguez.clinicarodriguez.model.Personas;
import com.clinicarodriguez.clinicarodriguez.repository.PacienteRepository;
import com.clinicarodriguez.clinicarodriguez.service.PacienteService;
import com.clinicarodriguez.clinicarodriguez.service.PersonasService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteServiceImpl implements PacienteService {
    
    @Autowired
    private PacienteRepository pacienteRepository;
    
    @Autowired
    private PersonasService personasService;

    @Override
    public List<Paciente> findAll() {
        return pacienteRepository.findAll();
    }

    @Override
    public Paciente findById(Integer id) {
        return pacienteRepository.findById(id).orElse(null);
    }

    @Override
    public Paciente save(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    @Override
    public void delete(Paciente paciente) {
        pacienteRepository.delete(paciente);
    }

    @Override
    public void deleteById(Integer id) {
        pacienteRepository.deleteById(id);
    }

    @Transactional
    @Override
    public Paciente crearPacienteCompleto(Personas persona) {
        // 1. Validar documento único
        System.out.println("DEBUG PACIENTE SIMPLE - Validando documento: Tipo=" + persona.getPersTipoDoc() + ", Nro=" + persona.getPersNroDoc());
        boolean existeDocumento = personasService.existePorTipoDocYNroDoc(persona.getPersTipoDoc(), persona.getPersNroDoc());
        System.out.println("DEBUG PACIENTE SIMPLE - Existe documento: " + existeDocumento);
        
        if (existeDocumento) {
            Optional<Personas> personaExistente = personasService.buscarPorTipoDocYNroDoc(persona.getPersTipoDoc(), persona.getPersNroDoc());
            if (personaExistente.isPresent()) {
                Personas p = personaExistente.get();
                System.out.println("DEBUG PACIENTE SIMPLE - Persona existente ID: " + p.getPersId() + ", Nombre: " + p.getPersNombrecompleto());
                throw new RuntimeException("Ya existe una persona con ese documento (" + 
                    persona.getPersTipoDoc() + "-" + persona.getPersNroDoc() + 
                    "). Persona ID: " + p.getPersId() + ", Nombre: " + p.getPersNombrecompleto());
            }
            throw new RuntimeException("Ya existe una persona con ese documento (" + 
                persona.getPersTipoDoc() + "-" + persona.getPersNroDoc() + ")");
        }
        
        // 3. Crear la persona primero
        Personas personaGuardada = personasService.guardar(persona);
        
        // 4. Crear el paciente vinculado a la persona
        Paciente paciente = new Paciente();
        paciente.setPersona(personaGuardada);
        paciente.setPaciEstado(true);
        
        return pacienteRepository.save(paciente);
    }

    @Transactional
    @Override
    public Paciente crearPacienteConApoderado(Personas persona, Integer apoderadoPersId) {
        // 1. Validar documento único
        System.out.println("DEBUG PACIENTE - Validando documento: Tipo=" + persona.getPersTipoDoc() + ", Nro=" + persona.getPersNroDoc());
        boolean existeDocumento = personasService.existePorTipoDocYNroDoc(persona.getPersTipoDoc(), persona.getPersNroDoc());
        System.out.println("DEBUG PACIENTE - Existe documento: " + existeDocumento);
        
        if (existeDocumento) {
            Optional<Personas> personaExistente = personasService.buscarPorTipoDocYNroDoc(persona.getPersTipoDoc(), persona.getPersNroDoc());
            if (personaExistente.isPresent()) {
                Personas p = personaExistente.get();
                System.out.println("DEBUG PACIENTE - Persona existente ID: " + p.getPersId() + ", Nombre: " + p.getPersNombrecompleto());
                throw new RuntimeException("Ya existe una persona con ese documento (" + 
                    persona.getPersTipoDoc() + "-" + persona.getPersNroDoc() + 
                    "). Persona ID: " + p.getPersId() + ", Nombre: " + p.getPersNombrecompleto());
            }
            throw new RuntimeException("Ya existe una persona con ese documento (" + 
                persona.getPersTipoDoc() + "-" + persona.getPersNroDoc() + ")");
        }
        
        // 2. Validar que el apoderado existe (si se proporciona)
        Personas apoderado = null;
        if (apoderadoPersId != null) {
            apoderado = personasService.findById(apoderadoPersId);
            if (apoderado == null) {
                throw new RuntimeException("El apoderado con id " + apoderadoPersId + " no existe");
            }
        }
        
        // 3. Crear la persona primero
        Personas personaGuardada = personasService.guardar(persona);
        
        // 4. Crear el paciente vinculado a la persona y el apoderado
        Paciente paciente = new Paciente();
        paciente.setPersona(personaGuardada);
        paciente.setApoderado(apoderado);
        paciente.setPaciEstado(true);
        
        return pacienteRepository.save(paciente);
    }

    @Transactional
    @Override
    public Paciente actualizarPacienteCompleto(Integer pacienteId, Personas persona, String nroColegiatura,
                                               String grupoSanguineo, String alergias) {
        // 1. Buscar paciente existente
        Paciente pacienteExistente = findById(pacienteId);
        if (pacienteExistente == null) {
            throw new RuntimeException("Paciente no encontrado");
        }
        
        // 2. Actualizar datos de la persona
        persona.setPersId(pacienteExistente.getPersona().getPersId());
        personasService.actualizar(persona.getPersId(), persona);
      
        
        return pacienteRepository.save(pacienteExistente);
    }

    @Override
    public Optional<Paciente> findByPersonaId(Integer personaId) {
        return pacienteRepository.findByPersonaId(personaId);
    }

    @Override
    public List<Paciente> findByEstado(Integer estado) {
        return pacienteRepository.findByEstado(estado);
    }

    @Override
    public List<Paciente> findAllActivosConPersona() {
        return pacienteRepository.findAllActivosConPersona();
    }

    @Override
    public List<Paciente> findAllConPersona() {
        return pacienteRepository.findAllConPersona();
    }
    
    @Override
    public List<Paciente> buscarPorDni(String dniBusqueda, int limite) {
        if (dniBusqueda == null || dniBusqueda.trim().isEmpty()) {
            return List.of();
        }
        
        List<Paciente> resultados = pacienteRepository.findByDniStartingWith(dniBusqueda.trim());
        
        // Limitar los resultados al límite especificado
        if (resultados.size() > limite) {
            return resultados.subList(0, limite);
        }
        
        return resultados;
    }
    
    @Override
    public Optional<Paciente> findByDni(String dni) {
        if (dni == null || dni.trim().isEmpty()) {
            return Optional.empty();
        }
        return pacienteRepository.findByDniExacto(dni.trim());
    }

    @Override
    public long countPacientesActivos() {
        return pacienteRepository.countPacientesActivos();
    }

    @Transactional
    @Override
    public Paciente activar(Integer id) {
        Paciente paciente = findById(id);
        if (paciente == null) {
            throw new RuntimeException("Paciente no encontrado");
        }
        paciente.setPaciEstado(true);
        return pacienteRepository.save(paciente);
    }

    @Transactional
    @Override
    public Paciente desactivar(Integer id) {
        Paciente paciente = findById(id);
        if (paciente == null) {
            throw new RuntimeException("Paciente no encontrado");
        }
        paciente.setPaciEstado(false);
        return pacienteRepository.save(paciente);
    }

    @Override
    public String generarNroColegiatura() {
        long count = pacienteRepository.count();
        return String.format("PAC-%06d", count + 1);
    }
}
